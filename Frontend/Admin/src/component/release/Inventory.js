import React, { useState, useEffect } from "react";
import Modal from "react-modal/lib/components/Modal";
import { Link } from "react-router-dom";

import ModuleStyle from "../../ModuleStyle.module.css"

import axios from 'axios'

function Inventory(){

    const [item, setitem] = useState([])
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState(0);
    const [modalopen, setmodal] = useState(false)

    const [isHovering, setIsHovering] = useState(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);

    const boxstyle = {
        width:"1500px",
        height:"730px",
        border:"1px solid #F0F1F3",
        backgroundColor:"white",
        //display : d,
        textAlign:"center",
        //overflow:"auto",
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
            zIndex:5
        },
        content: {
            position: 'absolute',
            width: '1300px',
            height: '1000px',
            margin: 'auto',
            border: '1px solid #ccc',
            background: '#fff',
            overflow: 'auto',
            WebkitOverflowScrolling: 'touch',
            borderRadius: '2%',
            outline: 'none',
            padding: '2%',
            flexDirection:"row",
            zIndex:10
        }
    }

    const imgbox = {
        width : "200px",
        height : "200px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        margin : "auto"
    }

    useEffect(() => {
        axios.get("http://127.0.0.1:8000/quiz//api/v1/headquarters/stock-management/stocks")
        .then((response) => {
            setitem(response.data)
            console.log(response.data)
            setLoading(true)
        })
        .catch(function (error){
            console.log(error)
        })
    }, [])

    const result = (loading) && item.data.data_list.slice(page*10, (page+1)*10).map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"45px", display:"flex", width:"96.5%", backgroundColor:isHovering[index]}}>
            <div style={{display:"flex", margin:"20px"}}>
                <div style={{display:"flex"}} onMouseOver={() => {
                    const temp = ["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"];
                    temp[index] = "#2B7175";
                    setIsHovering(temp);
                }} 
                onMouseOut={() => {
                    setIsHovering(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);
                }}>
                    <div style={{width:"45px"}}/>
                    <div style={{width:"250px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"190px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"200px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"220px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"190px", textAlign:"left"}}>{data.warehousing_date}</div>
                    <div style={{width:"80px", textAlign:"left"}}>상태</div>
                
                </div>
            </div>
        </div> 
    })


    return <div style={boxstyle}>

        {(modalopen) && <Modal style={modalstyle} isOpen={modalopen}>
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>상품 추가</p>   
            <div style={{display:"flex"}}>
                <div style={imgbox}></div>
            </div>
            <div style={{display:"flex"}}>
                <div style={{marginLeft:"auto", color:"red"}}>*필수사항</div> 
            </div>
            <div style={{height:"580px"}}>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>상품명</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   상품명을 입력하세요"></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"62px", fontSize:"25px"}}>카테고리</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   상품명을 입력하세요"></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"80px", fontSize:"25px"}}>공급처</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   상품명을 입력하세요"></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"80px", fontSize:"25px"}}>공급가</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   상품명을 입력하세요"></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>판매가</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   상품명을 입력하세요"></input>
                </div>
            </div>
            <div style={{display:"flex", marginTop:"auto"}}>
                <button style={{marginLeft:"290px", marginRight:"10px"}} className={ModuleStyle.whiteButton} onClick={() => setmodal(false)}>취소</button>
                <button className={ModuleStyle.blueButton}>상품 추가</button>
            </div>
        </Modal>}

        
        {(loading) &&
        <div>
            <div style={{height:"680px"}}>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"1000px", fontSize:"30px"}}>발주목록</p>
                    <button style={{marginTop:"20px", marginRight:"10px"}} className={ModuleStyle.blueSmallButton} onClick={() => {setmodal(true)}}>상품 추가</button>
                    <button style={{marginTop:"20px", marginRight:"10px"}} className={ModuleStyle.whiteSmallButton} >점포 검색</button>
                    <button style={{marginTop:"20px"}} className={ModuleStyle.whiteSmallButton} >입고 기록</button>
                </div>
                <div style={{display:"flex"}}>
                    <div style={{width:"80px"}}/>
                    <div style={{width:"250px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"200px", textAlign:"left"}}>공급가(원)</div>
                    <div style={{width:"200px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"200px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"200px", textAlign:"left"}}>보유 수량</div>
                    <div style={{width:"200px", textAlign:"left"}}>발주 신청 날짜</div>
                    <div style={{width:"200px", textAlign:"left"}}>상태</div>
                </div>
                {result}
            </div>
            <div style={{display:"flex"}}>
                <button style={{marginRight:"550px", marginLeft:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {(page > 0) && setPage(page - 1)}}>Previous</button> 
                <div>Page {page+1} / {item.data.pageInfo.totalPages} </div>
                <button style={{marginLeft:"550px", marginRight:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {(page < item.data.pageInfo.totalPages - 1) && setPage(page + 1)}}>next</button>
            </div>
        </div>}
    </div>

}

export default Inventory