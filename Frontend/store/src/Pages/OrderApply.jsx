import { useLocation } from 'react-router-dom';
import React, { useState, useEffect} from "react";
import ModuleStyle from "../ModuleStyle.module.css";
import Modal from "react-modal/lib/components/Modal";
import { useNavigate } from 'react-router-dom';
import {CiSquarePlus} from "react-icons/ci";
import {CiSquareMinus} from "react-icons/ci";
import api from '../Axios';
import {Modalstyle} from "../component/LoginStyle";

const titleboxstyle = {
    width:"1500px",
    height:"795px",
    backgroundColor:"white",
    //display : d,
    textAlign:"center",
    //overflow:"auto",
    marginTop: "100px",
    margin : "auto",
    //fontSize: f,
}
const modalstyle = {
    overlay: {
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(255, 255, 255, 0.75)',
        zIndex:15
    },
    content: {
        position: 'absolute',
        width: '330px',
        height: '170px',
        margin: 'auto',
        border: '1px solid #ccc',
        background: '#fff',
        WebkitOverflowScrolling: 'touch',
        borderRadius: '1%',
        outline: 'none',
        padding: '2%',
        zIndex:20,
        overflow:"auto"
    }
}



function OrderApply(){
    const [page, setPage] = useState(0);
    const [storeId, setStoreId] = useState(1);
    const [name, setname] = useState([]);
    const [loading, setLoading] = useState(false);
    const [item, setitem] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedItems, setSelectedItems] = useState({});
    const navigate = useNavigate();
    const location = useLocation();
    const selectedData = location.state?.selectedData || [];
    const [newItemStock, setNewItemStock] = useState(() => Array(parseInt(selectedData.length, 10)).fill(0));
    const [newPrice, setNewPrice] = useState(() => Array(parseInt(selectedData.length, 10)).fill(0));
    const [showConfirmationModal, setShowConfirmationModal] = useState(false);
    const [deleteModalOpen, setDeleteModal] = useState(false)
    const [changeModalOpen, setChangeModal] = useState(false)
    const [firstItem, setFirstItem] = useState(null);
    const [selectedIndexSize, setSelectedIndexSize] = useState(0);
    const [orderSuccess, setOrderSuccess] = useState(false);

    const handleOrderApplyClick = async () => {
        // Filter out items with newItemStock equal to 0
        const validItems = selectedData.filter((data, index) => newItemStock[index] > 0);

        // Set the item_cu_id of the first index
        setFirstItem(validItems.length > 0 ? validItems[0].item_cu_fielditem_name : null);

        // Set the size of the index array
        setSelectedIndexSize(validItems.length - 1);

        setShowConfirmationModal(true);
        setChangeModal(true);
    };
    const handleCancelOrderApply = () => {
        setShowConfirmationModal(false);
        setChangeModal(false);
    };

    const handleOrderSuccessClose = () => {
        // Reset state and close the modal
        setOrderSuccess(false);
        setShowConfirmationModal(false);
        setChangeModal(false);
    };

    const handleConfirmOrderApply = async () => {
        // Filter out items with newItemStock equal to 0
        const validItems = selectedData.filter((data, index) => newItemStock[index] > 0);

        // Prepare items for axios.post
        const items = validItems.map((data, index) => ({
            count: newItemStock[index],
            item_cu_id: data.item_cu_id,
        }));

        console.log(items);

        setOrderSuccess(true);
        try {
            const response = await api.post(
                '/managers/store/1/item_orders',
                items
            );

            console.log(response.data);

            // If the axios.post is successful, set orderSuccess to true
        } catch (error) {
            console.error(error);
        }
    };


    const handleMinusClick = (index) => {
        const updatedData = [...location.state.selectedData];
        setNewItemStock(prevArray => {
            const newArray = [...prevArray]; // 이전 배열을 복제
            newArray[index] = newItemStock[index]-1; // 원하는 인덱스에 새로운 값 할당
            return newArray; // 변경된 배열을 반환
        });

        setNewPrice(prevArray => {
            const newArray = [...prevArray]; // 이전 배열을 복제
            newArray[index] = (newItemStock[index]-1) * parseInt(updatedData[index].item_hq_price,10); // 원하는 인덱스에 새로운 값 할당
            return newArray; // 변경된 배열을 반환
        });
    };
    const handlePlusClick = (index) => {
        const updatedData = [...location.state.selectedData];
        setNewItemStock(prevArray => {
            const newArray = [...prevArray]; // 이전 배열을 복제
            newArray[index] = newItemStock[index]+1; // 원하는 인덱스에 새로운 값 할당
            return newArray; // 변경된 배열을 반환
        });

        setNewPrice(prevArray => {
            const newArray = [...prevArray]; // 이전 배열을 복제
            newArray[index] = (newItemStock[index]+1) * parseInt(updatedData[index].item_hq_price,10); // 원하는 인덱스에 새로운 값 할당
            return newArray; // 변경된 배열을 반환
        });
    };

    const result = selectedData.map((data, index) => (
        <div key={index} style={{ margin: "10px", borderBottom: "1px solid #D0D3D9", height: "50px", display: "flex", width: "96.5%"}}>
            <div style={{ display: "flex", margin: "20px" }}>
                <div style={{ display: "flex" }}>
                    <div style={{ marginLeft: "100px", width: "350px", textAlign: "left" }}>{data.item_cu_fielditem_name}</div>
                    <div style={{ width: "291px", textAlign: "left" }}>{data.item_hq_category}</div>
                    <div onClick={() => handleMinusClick(index)}><CiSquareMinus size={22}/></div>
                    <div style={{textAlign: "left", marginLeft:"5px", marginRight: "5px" }}>
                        {newItemStock[index]}
                    </div>
                    <div onClick={() => handlePlusClick(index)} style={{ width: "180px", textAlign: "left" }}><CiSquarePlus size={22}/></div>
                    <div style={{ width: "200px", textAlign: "left" }}>{data.item_hq_price}</div>
                    <div style={{ width: "200px", textAlign: "left" }}>{newPrice[index]}</div>
                </div>
            </div>
        </div>
    ));


    return (
        <div style={titleboxstyle}>
            <div style={{ height: "750px" }}>
                <div style={{ display: "flex" }}>
                    <p style={{ marginTop: "25px", marginLeft: "25px", marginRight: "1100px", fontSize: "30px" }}>발주 신청 목록</p>
                    <button
                        style={{ marginTop: "20px", marginRight: "20px" }}
                        className={ModuleStyle.blueSmallButton}
                        onClick={handleOrderApplyClick} // Attach the click handler to the button
                    >
                        발주 신청
                    </button>
                    {showConfirmationModal && (
                        <Modal style={modalstyle} isOpen={changeModalOpen} className={ModuleStyle.confirmationModal}>
                            {orderSuccess ? (
                                <div style={{marginTop: "-10px"}}>
                                    <p style={{fontSize: "20px", textAlign: "left"}}>
                                        발주 신청
                                    </p>
                                    <p>
                                        발주 신청이 완료되었습니다.
                                        <br/>
                                    </p>
                                    <div style={{textAlign: "center", marginTop: "10px"}}>
                                    <button onClick={handleOrderSuccessClose} className={ModuleStyle.blueSmallButton}>확인</button>
                                    </div>
                                </div>
                            ) : (
                                <div style={{marginTop: "-10px"}}>
                                    <div>
                                        <p style={{fontSize: "20px", textAlign: "left"}}>
                                            발주 신청
                                        </p>
                                    <p>
                                        {selectedIndexSize === 0
                                            ? `${firstItem}을(를) 발주 신청하시겠습니까?`
                                            : `${firstItem}외 ${selectedIndexSize}개를 발주 신청하시겠습니까?`}
                                        <br />
                                    </p>
                                    </div>
                                    <div style={{textAlign: "center", marginTop: "10px"}}>
                                    <button style={{margin: "10px"}} onClick={handleConfirmOrderApply} className={ModuleStyle.blueSmallButton}>발주 신청</button>
                                    <button style={{margin: "10px"}} onClick={handleCancelOrderApply} className={ModuleStyle.whiteSmallButton}>취소</button>
                                    </div>
                                </div>
                            )}
                        </Modal>
                    )}
                </div>
                <div style={{ display: "flex" }}>
                    <div style={{ width: "130px" }} />
                    <div style={{ width: "350px", textAlign: "left" }}>상품명</div>
                    <div style={{ width: "300px", textAlign: "left" }}>카테고리</div>
                    <div style={{ width: "200px", textAlign: "left" }}>수량</div>
                    <div style={{ width: "200px", textAlign: "left" }}>공급가</div>
                    <div style={{ width: "200px", textAlign: "left" }}>금액</div>
                </div>
                {result}
            </div>
            <div style={{ display: "flex" }}>
                <button style={{ marginRight: "550px", marginLeft: "30px" }} className={ModuleStyle.whiteSmallButton} onClick={() => { (page > 0) && setPage(page - 1) }}>Previous</button>
                <div>Page {page + 1} / {(item.data && item.data.pageInfo) ? item.data.pageInfo.totalPages : 1}</div>
                <button style={{ marginLeft: "550px", marginRight: "30px" }} className={ModuleStyle.whiteSmallButton} onClick={() => { (page < (item.data && item.data.pageInfo && item.data.pageInfo.totalPages - 1)) && setPage(page + 1) }}>Next</button>
            </div>
        </div>
    );
}

export default OrderApply;