import React, { useState, useEffect } from "react";
import Modal from "react-modal/lib/components/Modal";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"

import axios from 'axios'

function Inventory(){

    const { search } = useSelector((state) => state)

    const [item, setitem] = useState([])
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState(0);

    const [modalopen, setmodal] = useState([])
    const [rejectModalOpen, setRejectModal] = useState(false)
    const [acceptModalOpen, setAcceptModal] = useState(false)

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

    const smallModalstyle = {
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
            width: '456px',
            height: '300px',
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
            width: '700px',
            height: '1000px',
            margin: 'auto',
            border: '1px solid #ccc',
            background: '#fff',
            WebkitOverflowScrolling: 'touch',
            borderRadius: '1%',
            outline: 'none',
            padding: '2%',
            zIndex:10,
            overflow:"auto"
        }
    }

    const imgbox = {
        width : "600px",
        height : "300px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        margin : "auto"
    }

    
    {/* 초기 default값 검색 */}
    useEffect(() => {
        const templist = []
        for (var i=0; i<100; i++){
            templist.push(false);
        }
        setmodal(templist);
        
        const link = search.url + "/api/v1/headquarters/stock-management/stocks"

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

    const dispatch = useDispatch();

    {/* 상단 search에서 검색하기 */}
    if (search.employSearch){
        // link 수정 필요
        const link = "/api/v1/headquarters/release-management/stocks?store_name=" + search.wareName + "&address=" + search.wareAddr
        //axios.get("server_url"+ link)
        //console.log(link)
        axios.get("http://127.0.0.1:8000/quiz//api/v1/headquarters/stock-management/stocks")
        .then((response) => {
            setitem(response.data)
            console.log(response.data)
            setLoading(true)
        })
        .catch(function (error){
            console.log(error)
        })

        dispatch({ type: 'employeeEnd' });
    }

    {/* 기본 발주 목록 (상품검색, 카테고리, 공급처) */}
    const result = (loading) && item.data.data_list.slice(page*10, (page+1)*10).map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"45px", display:"flex", width:"96.5%", backgroundColor:isHovering[index]}}>
            <div style={{display:"flex", margin:"20px"}}>
            {(modalopen[index]) && <Modal style={modalstyle} isOpen={modalopen[index]}>
                        <p style={{marginTop:"25px", marginLeft:"25px", fontSize:"35px", width:"250px"}}>회원가입 승인</p>
                        <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"0px", fontSize:"30px"}}>본사 관리자</p>
                        <div style={{display:"flex"}}>
                            <p style={{marginTop:"25px", marginLeft:"25px", width:"100px", fontSize:"25px"}}>이름</p>
                            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"0px", fontSize:"25px"}}>신예빈</p>
                        </div>
                        <div style={{display:"flex"}}>
                            <p style={{marginTop:"25px", marginLeft:"25px", width:"100px", fontSize:"25px"}}>아이디</p>
                            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"0px", fontSize:"25px"}}>{index}</p>
                        </div>
                        <div style={{display:"flex"}}>
                            <p style={{marginTop:"25px", marginLeft:"25px", width:"100px", fontSize:"25px"}}>연락처</p>
                            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"0px", fontSize:"25px"}}>010 1234 5678</p>
                        </div>
                        <div style={{display:"flex"}}>
                            <p style={{marginTop:"25px", marginLeft:"25px", width:"100px", fontSize:"25px"}}>이메일</p>
                            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"0px", fontSize:"25px"}}>cs25@gmail.com</p>
                        </div>

                        <div style={{display:"flex", height:"50px"}}>
                            <button style={{marginLeft:"130px", marginTop:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {
                                const templist = [...modalopen];
                                templist[index] = false;
                                setmodal(templist);
                            }}>취소</button>
                            <button style={{marginLeft:"10px", marginTop:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {setRejectModal(true);}}>거절</button>
                            <button style={{marginLeft:"10px", marginTop:"30px"}} className={ModuleStyle.blueSmallButton} onClick={() => {setAcceptModal(true);}}>승인</button>
                        </div>
                    </Modal>}
                <div style={{display:"flex"}} onMouseOver={() => {
                    const temp = ["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"];
                    temp[index] = "#2B7175";
                    setIsHovering(temp);
                }} 
                onMouseOut={() => {
                    setIsHovering(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);
                }}
                onClick={(e) => {
                    const templist = [...modalopen];
                    templist[index] = true;
                    setmodal(templist);
                }}
                >
                    <div style={{width:"10px"}}/>
                    <div style={{width:"240px", textAlign:"left"}}>이름</div>
                    <div style={{width:"345px", textAlign:"left"}}>연락처</div>
                    <div style={{width:"485px", textAlign:"left"}}>주소</div>
                    <div style={{width:"195px", textAlign:"left"}}>구분</div>
                    <div style={{width:"200px", textAlign:"left"}}>신청일</div>
                </div>
            </div>
        </div> 
    })



    return <div style={boxstyle}>
        {/* 승인 모달 */}
        {(acceptModalOpen) && <Modal style={smallModalstyle} isOpen={acceptModalOpen}>
        <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>회원가입 승인</p>
            <p style={{marginTop:"40px", marginLeft:"10px", fontSize:"25px"}}>회원가입이 승인되었습니다.</p>    
            <button style={{marginLeft:"180px", marginTop:"120px", }} className={ModuleStyle.whiteSmallButton} onClick={() => {
                setAcceptModal(false);
                const templist = [];
                for (var i=0; i<100; i++){ templist[i] = false; }                
                setmodal(templist);
                }}>닫기</button>
        </Modal>}

        {/* 거절 모달 */}
        {(rejectModalOpen) && <Modal style={smallModalstyle} isOpen={rejectModalOpen}>
        <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>회원가입 거절</p>
            <p style={{marginTop:"40px", marginLeft:"10px", fontSize:"25px"}}>회원가입 승인이 거절되었습니다.</p>    
            <button style={{marginLeft:"180px", marginTop:"120px", }} className={ModuleStyle.whiteSmallButton} onClick={() => {
                setRejectModal(false);
                const templist = [];
                for (var i=0; i<100; i++){ templist[i] = false; }                
                setmodal(templist);
                }}>닫기</button>
        </Modal>}

        <div style={{display:"flex"}}>
            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"1100px", fontSize:"30px"}}>신청 목록</p>
        </div>

        {(loading) && <div>
            <div style={{height:"600px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"40px"}}/>
                    <div style={{width:"250px", textAlign:"left"}}>이름</div>
                    <div style={{width:"350px", textAlign:"left"}}>연락처</div>
                    <div style={{width:"500px", textAlign:"left"}}>주소</div>
                    <div style={{width:"200px", textAlign:"left"}}>구분</div>
                    <div style={{width:"200px", textAlign:"left"}}>신청일</div>
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