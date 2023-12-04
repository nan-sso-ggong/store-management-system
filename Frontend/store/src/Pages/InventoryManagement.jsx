import { useLocation } from 'react-router-dom';
import React, { useState, useEffect} from "react";
import ModuleStyle from "../ModuleStyle.module.css";
import Modal from "react-modal/lib/components/Modal";
import { useNavigate } from 'react-router-dom';
import {CiSquarePlus} from "react-icons/ci";
import {CiSquareMinus} from "react-icons/ci";
import {Modalstyle} from "../component/LoginStyle";
import api from "../Axios";
import {useSelector} from "react-redux";


const listStyle = {
    zIndex: 10,
    width: "200px",
    height: "40px",
    border: "1px solid #F0F1F3",
    backgroundColor: "white",
    textAlign: "center",
    lineHeight: "40px",
    position: "relative",
};

const out = {
    zIndex: 3,
    display: "block",
    position: "fixed",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
};

const boxstyle = {
    width: "1500px",
    height: "130px",
    backgroundColor: "white",
    textAlign: "center",
    marginLeft: "27.5px",
    marginTop: "20px",
    marginBottom: "5px",
    zIndex: 4,
};

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



function InventoryManagement(){
    const [category, setCategory] = useState("");
    const [index, setIndex] = useState("");
    const [order, setOrder] = useState("");
    const [sort, setSort] = useState("");
    const [size, setSize] = useState(10);
    const [open, setOpen] = useState(false);
    const [page, setPage] = useState(0);
    const [storeId, setStoreId] = useState(1);
    const [name, setName] = useState([]);
    const [loading, setLoading] = useState(false);
    const [item, setitem] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedItems, setSelectedItems] = useState({});
    const navigate = useNavigate();
    const location = useLocation();
    const [newItemStock, setNewItemStock] = useState(() => Array(10).fill(0));
    const [showConfirmationModal, setShowConfirmationModal] = useState(false);
    const [deleteModalOpen, setDeleteModal] = useState(false)
    const [changeModalOpen, setChangeModal] = useState(false)
    const [firstItem, setFirstItem] = useState(null);
    const [selectedIndexSize, setSelectedIndexSize] = useState(0);
    const [orderSuccess, setOrderSuccess] = useState(false);
    const { users } = useSelector((state) => state);
    const [store, setstore]= useState(users.store_id);


    //모달창 확인 눌렀을때
    const handleOrderApplyClick = async () => {
        setShowConfirmationModal(true);
        setChangeModal(true);
    };
    //모달창 취소 눌렀을때
    const handleCancelOrderApply = () => {
        setShowConfirmationModal(false);
        setChangeModal(false);
    };
    const handleUpdateModalShow = () => {
        // Extract items with newItemStock not equal to 0
        const updatedItems = item.data && item.data.datalist.filter((data, index) => newItemStock[index] !== 0);

        // Check if there are items to update
        if (updatedItems && updatedItems.length > 0) {
            // If there are items, set the first item and its index size
            setFirstItem(updatedItems[0].id !== undefined ? updatedItems[0].name : updatedItems[0].item_cu_fielditem_name);
            setSelectedIndexSize(updatedItems.length-1);

            // Open the confirmation modal
            setShowConfirmationModal(true);
            setChangeModal(true);
        } else {
            // If there are no items to update, show a message or handle as needed
            console.log('No items to update.');
            // Add your logic here if needed
        }
    };

    //모달창 데이터 전송 성공 멘트 확인 눌렀을 때
    const handleOrderSuccessClose = () => {
        // Reset state and close the modal
        setOrderSuccess(false);
        setShowConfirmationModal(false);
        setChangeModal(false);
    };

    //확인 눌렀을 때 전송하는부분
    const handleConfirmOrderApply = async () => {
        // Filter out items with newItemStock equal to 0

        setOrderSuccess(true);
        try {
            const response = await api.patch(
                `/managers/store/${store}/item_orders`,

            );

            console.log(response.data);

            // If the axios.post is successful, set orderSuccess to true
        } catch (error) {
            console.error(error);
        }
    };

    //검색어 입력한거 기억하기
    const saveName = (e) => {
        setName(e.target.value);
    };

    //체크박스 확성화
    const handleCheckboxChange = (id) => (e) => {
        setSelectedItems({ ...selectedItems, [id]: e.target.checked });
    };

    //전체선택&헤제
    const handleSelectAll = () => {
        const updatedSelectedItems = {};
        item.data && item.data.datalist.forEach((data, index) => {
            updatedSelectedItems[index] = !selectAll;
        });
        setSelectAll(!selectAll);
        setSelectedItems(updatedSelectedItems);
    };

    //마이너스 버튼
    const handleMinusClick = (index) => {
        setNewItemStock(prevArray => {
            const newArray = [...prevArray]; // 이전 배열을 복제
            newArray[index] = newItemStock[index]-1; // 원하는 인덱스에 새로운 값 할당
            return newArray; // 변경된 배열을 반환
        });
    };

    //플러스 버튼
    const handlePlusClick = (index) => {
        setNewItemStock(prevArray => {
            const newArray = [...prevArray]; // 이전 배열을 복제
            newArray[index] = newItemStock[index]+1; // 원하는 인덱스에 새로운 값 할당
            return newArray; // 변경된 배열을 반환
        });
    };

    // 불러오기
    const handleUpdateClick = async () => {
        console.log("불러오기");
        try {
            const response = await api.get(`/managers/store/${store}/item_orders?keyword={}&category={}&page={}`,{});  // Replace 'YOUR_API_ENDPOINT' with the actual API endpoint
            console.log(response);
            setitem(response.data);

        } catch (error) {
            console.error('Error updating data:', error);
        }
    };

    const fetchData = async () => {
        try {
            console.error(store);
            const response = await api.get(`/managers/store/${store}/stock?order=${order}&sort=${sort}&index=${index}&size=${size}&category=${category}`);
            console.log(response);
            setitem(response.data);
            setLoading(true);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    useEffect(() => {
        fetchData();
    }, [category, name, page]);

    const result = item.data && item.data.datalist?.map((data, index) => {
        console.log(newItemStock[index]);

        return (
            <div key={index} style={{ margin: "10px", borderBottom: "1px solid #D0D3D9", height: "50px", display: "flex", width: "96.5%" }}>
                <div style={{ display: "flex", margin: "20px" }}>
                    <div style={{ display: "flex" }}>
                        <div className="checkbox" style={{ width: "100px" }}>
                            <input
                                type="checkbox"
                                checked={selectedItems[index] || false}
                                onChange={handleCheckboxChange(index)}
                            />
                        </div>
                        <div style={{ width: "350px", textAlign: "left" }}>
                            {data.id !== undefined ? data.name : data.item_cu_fielditem_name}
                        </div>
                        <div style={{ width: "300px", textAlign: "left" }}>
                            {data.id !== undefined ? data.category : data.item_hq_category}
                        </div>
                        <div style={{ width: "200px", textAlign: "left" }}>
                            {data.id !== undefined ? data.amount : data.item_cu_stock}
                        </div>
                        <div style={{ width: "200px", textAlign: "left" }}>
                            {data.id !== undefined ? data.price : data.item_hq_price}
                        </div>

                        <div onClick={() => handleMinusClick(index)}><CiSquareMinus size={22}/></div>
                        <div style={{textAlign: "left", marginLeft:"5px", marginRight: "5px" }}>
                            {newItemStock[index]}
                        </div>
                        <div onClick={() => handlePlusClick(index)} style={{ width: "180px", textAlign: "left" }}><CiSquarePlus size={22}/></div>
                    </div>
                </div>
            </div>
        );
    });




    return (
        <div>
            <div style={boxstyle}>
                {open && <div style={out} onClick={() => setOpen(false)}></div>}
                <div style={{ display: "flex" }}>
                    <p style={{ marginTop: "15px", marginLeft: "25px", fontSize: "30px" }}>재고 관리</p>
                </div>
                <div style={{ display: "flex" }}>
                    <div style={{ marginLeft: "20px", marginRight: "25px", marginTop: "-25px", display: "flex" }}>
                        <p style={{ fontSize: "25px", marginRight: "25px" }}>카테고리</p>
                        <div>
                            <div style={{ marginTop: "20px" }} className={ModuleStyle.inputBox} onClick={() => setOpen(true)}>
                                {category}
                            </div>
                            {open && (
                                <div style={{ height: "250px", overflow: "auto" }}>
                                    <div style={listStyle} onClick={() => { setOpen(false); setCategory("아이스크림"); }}>
                                        아이스크림
                                    </div>
                                    <div style={listStyle} onClick={() => { setOpen(false); setCategory("과자"); }}>
                                        과자
                                    </div>
                                    <div style={listStyle} onClick={() => { setOpen(false); setCategory("라면"); }}>
                                        라면
                                    </div>
                                    <div style={listStyle} onClick={() => { setOpen(false); setCategory("음료"); }}>
                                        음료
                                    </div>
                                </div>
                            )}
                        </div>
                        <p style={{ fontSize: "25px", marginLeft: "25px", marginRight: "25px" }}>상품검색</p>
                        <input type="text" className={ModuleStyle.inputBox} placeholder="상품명을 입력하세요" onChange={saveName}></input>
                    </div>
                    <button className={ModuleStyle.whiteSmallButton} onClick={() => setLoading(true)}>
                        검색
                    </button>
                </div>
            </div>

        <div style={titleboxstyle}>
            <div style={{ height: "750px" }}>
                <div style={{ display: "flex" }}>
                    <p style={{ marginTop: "25px", marginLeft: "25px", marginRight: "800px", fontSize: "30px" }}>상품 조회</p>
                    <button
                        style={{ marginTop: "20px", marginRight: "10px", width: "170px"}}
                        className={ModuleStyle.whiteSmallButton}
                        onClick={handleUpdateClick}
                    >
                        발주목록 불러오기
                    </button>
                    <button
                        onClick={handleSelectAll}
                        style={{ marginTop: "20px", marginRight: "10px" }}
                        className={ModuleStyle.whiteSmallButton}
                    >
                        <span>{selectAll ? '전체 해제' : '전체 선택'}</span>
                    </button>
                    <button
                        style={{ marginTop: "20px", marginRight: "10px"}}
                        className={ModuleStyle.blueSmallButton}
                        onClick={handleUpdateModalShow}
                    >
                        업데이트
                    </button>
                    {showConfirmationModal && (
                        <Modal style={modalstyle} isOpen={changeModalOpen} className={ModuleStyle.confirmationModal}>
                            {orderSuccess ? (
                                <div style={{marginTop: "-10px"}}>
                                    <p style={{fontSize: "20px", textAlign: "left"}}>
                                        재고 관리
                                    </p>
                                    <p>
                                        재고 수량 업데이트가 완료되었습니다.
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
                                            재고 관리
                                        </p>
                                        <p>
                                            {selectedIndexSize === 0
                                                ? `${firstItem}을(를) 업데이트 하시겠습니까?`
                                                : `${firstItem}외 ${selectedIndexSize}개를 업데이트 하시겠습니까?`}
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
                    <div style={{ width: "200px", textAlign: "left" }}>단가</div>
                    <div style={{ width: "200px", textAlign: "left" }}>수량 변경</div>
                </div>
                {result}
            </div>
            <div style={{ display: "flex" }}>
                <button style={{ marginRight: "550px", marginLeft: "30px" }} className={ModuleStyle.whiteSmallButton} onClick={() => { (page > 0) && setPage(page - 1) }}>Previous</button>
                <div>Page {page + 1} / {(item.data && item.data.pageInfo) ? item.data.pageInfo.totalPages : 1}</div>
                <button style={{ marginLeft: "550px", marginRight: "30px" }} className={ModuleStyle.whiteSmallButton} onClick={() => { (page < (item.data && item.data.pageInfo && item.data.pageInfo.totalPages - 1)) && setPage(page + 1) }}>Next</button>
            </div>
        </div>
        </div>
    );
}

export default InventoryManagement;