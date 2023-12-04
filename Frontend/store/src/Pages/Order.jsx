import React, { useState, useEffect } from "react";
import ModuleStyle from "../ModuleStyle.module.css";
import { useNavigate } from 'react-router-dom';
import api from '../Axios';
import {useSelector} from "react-redux";

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
    width: "1500px",
    height: "795px",
    backgroundColor: "white",
    textAlign: "center",
    marginTop: "50px",
    margin: "auto",
}

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

function Order() {
    const [category, setCategory] = useState("");
    const [open, setOpen] = useState(false);
    const [page, setPage] = useState(0);
    const [name, setName] = useState("");
    const [loading, setLoading] = useState(0);
    const [item, setItem] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const { users } = useSelector((state) => state);
    const [store]= useState(users.store_id);
    const [accessToken]= useState(users.access_token);
    const [selectedItems, setSelectedItems] = useState({});
    const navigate = useNavigate();

    const saveName = (e) => {
        setName(e.target.value);
    };

    const fetchData = async () => {
        console.log(store);
        console.log(localStorage.getItem(accessToken));

        try {
            const response = await api.get(`/managers/store/${store}/item_orders?category=${category}`, {

            })
            console.log(response);
            setItem(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
            fetchData();
    }, []);

    useEffect(() => {
            fetchData();
    }, [page, category]);

    const handleSelectionRequest = () => {
        const isAnyCheckboxSelected = Object.values(selectedItems).some((isChecked) => isChecked);

        if (isAnyCheckboxSelected) {
            const selectedData = item.data && item.data.datalist.filter((data, index) => selectedItems[index]);
            console.log(selectedData);
            // 다른 페이지로 선택된 데이터 전달 또는 필요한 작업 수행
            navigate('/store/order/apply', { state: { selectedData: selectedData } });
        } else {
            // No checkbox selected, handle accordingly (e.g., show a message)
            console.log("Please select at least one item.");
        }
    };

    const handleCheckboxChange = (id) => (e) => {
        setSelectedItems({ ...selectedItems, [id]: e.target.checked });
    };

    const handleSelectAll = () => {
        const updatedSelectedItems = {};
        item.data && item.data.datalist.forEach((data, index) => {
            updatedSelectedItems[index] = !selectAll;
        });
        setSelectAll(!selectAll);
        setSelectedItems(updatedSelectedItems);
    };

    const result = item.data && item.data.datalist?.map((data, index) => {
        console.log(index);
        const stockValue = data.item_cu_stock;
        const stockDisplay = stockValue === 0 ? <span style={{ color: "red" }}>품절</span> : stockValue;

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
                        <div style={{ width: "350px", textAlign: "left" }}>{data.item_cu_fielditem_name}</div>
                        <div style={{ width: "300px", textAlign: "left" }}>{data.item_hq_category}</div>
                        <div style={{ width: "200px", textAlign: "left" }}>{stockDisplay}</div>
                        <div style={{ width: "200px", textAlign: "left" }}>{data.item_hq_price}</div>
                        <div style={{ width: "200px", textAlign: "left" }}>
                            {data.item_state ? '신청완료' : '미신청'}
                        </div>
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
                    <p style={{ marginTop: "15px", marginLeft: "25px", fontSize: "30px" }}>발주 목록</p>
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
                                    <div style={listStyle} onClick={() => { setOpen(false); setCategory(""); }}>
                                        선택 해제
                                    </div>
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
                        {/*<p style={{ fontSize: "25px", marginLeft: "25px", marginRight: "25px" }}>상품검색</p>*/}
                        {/*<input type="text" className={ModuleStyle.inputBox} placeholder="상품명을 입력하세요" onChange={saveName}></input>*/}
                    </div>
                    {/*<button className={ModuleStyle.whiteSmallButton} onClick={() => setLoading(loading+1)}>*/}
                    {/*    검색*/}
                    {/*</button>*/}
                </div>
            </div>

            <div style={titleboxstyle}>
                <div style={{ height: "750px" }}>
                    <div style={{ display: "flex" }}>
                        <p style={{ marginTop: "25px", marginLeft: "25px", marginRight: "1000px", fontSize: "30px" }}>상품 조회</p>
                        <button style={{ marginTop: "20px", marginRight: "20px" }}
                                className={ModuleStyle.blueSmallButton}
                                onClick={handleSelectionRequest}
                        >
                            선택 신청
                        </button>
                        <button
                            onClick={handleSelectAll}
                            style={{ marginTop: "20px", marginRight: "20px" }}
                            className={ModuleStyle.whiteSmallButton}
                        >
                            <span>{selectAll ? '전체 해제' : '전체 선택'}</span>
                        </button>
                        {/*<button*/}
                        {/*    style={{ marginTop: "20px", marginRight: "20px" }}*/}
                        {/*    className={ModuleStyle.whiteVerySmallButton}*/}
                        {/*>*/}
                        {/*    삭제*/}
                        {/*</button>*/}
                    </div>
                    <div style={{ display: "flex" }}>
                        <div style={{ width: "130px" }} />
                        <div style={{ width: "350px", textAlign: "left" }}>상품명</div>
                        <div style={{ width: "300px", textAlign: "left" }}>카테고리</div>
                        <div style={{ width: "200px", textAlign: "left" }}>수량</div>
                        <div style={{ width: "200px", textAlign: "left" }}>공급가</div>
                        <div style={{ width: "200px", textAlign: "left" }}>상태</div>
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

export default Order;
