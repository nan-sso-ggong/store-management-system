import React, { useState, useEffect } from "react";
import ModuleStyle from "../ModuleStyle.module.css";
import { Link } from "react-router-dom";
import axios from 'axios'

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
    marginTop: "50px",
    margin : "auto",
    //fontSize: f,
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
    const [category, setcategory] = useState("카테고리를 선택하세요");
    const [open, setopen] = useState(false);
    const [page, setPage] = useState(0);
    const [storeId, setStoreId] = useState(1);
    const [name, setname] = useState([]);
    const [item, setitem] = useState([]);
    const [loading, setLoading] = useState(false);

    console.log("asdf");
    const savename = (e) => {
        setname(e.target.value); // Use e.target instead of event.target
    };

    useEffect(() => {
        axios.get(`/managers/store/${storeId}/item_orders?keyword=${name}&catagorie=${category}&page=${page}`)
            .then((response) => {
                setitem(response.data)
                console.log(response.data)
                setLoading(true)
            })
            .catch(function (error){
                console.log(error)
            })
    }, [])

    const result = (loading) && item.data.data_list.slice(page * 10, (page + 1) * 10).map((data, index) => {
        return (
            <div style={{ margin: "10px", borderBottom: "1px solid #D0D3D9", height: "50px", display: "flex", width: "96.5%"}}>
                <Link to="/admin/detail" style={{ textDecoration: "none" }}>
                    <div style={{ display: "flex", margin: "20px" }}>
                        <div style={{ display: "flex" }}>
                            <div className="checkbox" style={{ width: "100px" }}>
                                <input type="checkbox" />
                            </div>
                            <div style={{ width: "350px", textAlign: "left" }}>{data.item_cu_fielditem_name}</div>
                            <div style={{ width: "300px", textAlign: "left" }}>{data.item_hq_category}</div>
                            <div style={{ width: "200px", textAlign: "left" }}>{data.item_cu_stock}</div>
                            <div style={{ width: "200px", textAlign: "left" }}>{data.item_hq_price}</div>
                            <div style={{ width: "200px", textAlign: "left" }}>{data.item_state}</div>
                        </div>
                    </div>
                </Link>
            </div>
        );
    })

    return (
        <div>
        <div style={boxstyle}>
            {open && <div style={out} onClick={() => setopen(false)}></div>}
            <div style={{ display: "flex" }}>
                <p style={{ marginTop: "15px", marginLeft: "25px", fontSize: "30px" }}>발주 관리</p>
            </div>
            <div style={{ display: "flex" }}>
                <div style={{ marginLeft: "20px", marginRight: "25px", marginTop: "-25px", display: "flex" }}>
                    <p style={{ fontSize: "25px", marginRight: "25px" }}>카테고리</p>
                    <div>
                        <div style={{ marginTop: "20px" }} className={ModuleStyle.inputBox} onClick={() => setopen(true)}>
                            {category}
                        </div>
                        {open && (
                            <div style={{ height: "250px", overflow: "auto" }}>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category1"); }}>
                                    category1
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category2"); }}>
                                    category2
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category3"); }}>
                                    category3
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category4"); }}>
                                    category4
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category5"); }}>
                                    category5
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category6"); }}>
                                    category6
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category7"); }}>
                                    category7
                                </div>
                                <div style={listStyle} onClick={() => { setopen(false); setcategory("category8"); }}>
                                    category8
                                </div>
                            </div>
                        )}
                    </div>
                    <p style={{ fontSize: "25px", marginLeft: "25px", marginRight: "25px" }}>상품검색</p>
                    <input type="text" className={ModuleStyle.inputBox} placeholder="상품명을 입력하세요" onChange={savename}></input>
                </div>
                <button className={ModuleStyle.whiteSmallButton} onClick={() => { /* Implement button click logic here */ }}>
                    검색
                </button>
            </div>
        </div>

            <div style={titleboxstyle}>
                <div style={{ height: "750px" }}>
                    <div style={{ display: "flex" }}>
                        <p style={{ marginTop: "25px", marginLeft: "25px", marginRight: "900px", fontSize: "30px" }}>상품 조회</p>
                        <button style={{ marginTop: "20px", marginRight: "20px" }} className={ModuleStyle.blueSmallButton}>선택 신청</button>
                        <button style={{ marginTop: "20px", marginRight: "20px" }} className={ModuleStyle.whiteSmallButton}>전체 선택</button>
                        <button style={{ marginTop: "20px", marginRight: "20px" }} className={ModuleStyle.whiteVerySmallButton}>삭제</button>

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
