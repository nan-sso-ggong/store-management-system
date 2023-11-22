import React, {useState, useEffect} from "react";
import { Link } from "react-router-dom";
import axios from 'axios'
import Modal from "react-modal/lib/components/Modal";
import ModuleStyle from "../../ModuleStyle.module.css"
import Frame from "../Frame";

function Detail(){

    const [name , setname] = useState("users")
    const [item, setitem] = useState([])
    const [loading, setLoading] = useState(false);
    const [changeModalOpen, setChangeModal] = useState(false)
    const [deleteModalOpen, setDeleteModal] = useState(false)

    const boxstyle = {
        width:"1500px",
        height:"900px",
        border:"1px solid #F0F1F3",
        backgroundColor:"white",
        display : "flex",
        textAlign:"center",
        marginLeft:"30px",
        marginTop:"30px"
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
            width: '560px',
            height: '930px',
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

    const smallmodalstyle = {
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
            width: '450px',
            height: '400px',
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

    const bigImgbox = {
        width : "500px",
        height : "500px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        display: "table",
        marginLeft : "500px" 
    }

    const imgbox = {
        width : "200px",
        height : "200px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        margin : "auto"
    }

    useEffect(() => {
        axios.get("http://127.0.0.1:8000/quiz//api/v1/headquarters/stock-management/stocks/cokacola")
        .then((response) => {
            setitem(response.data)
            console.log(response.data)
            setLoading(true)
        })
        .catch(function (error){
            console.log(error)
        })
    }, []);

    return <div className={ModuleStyle.pagestyle}> 

        {(changeModalOpen) && <Modal style={modalstyle} isOpen={changeModalOpen}>
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>상품 추가</p>   
            <div style={{display:"flex"}}>
                <div style={imgbox}><img src={item.data.item_image_uuid} style={{height:"200px"}}></img></div>
            </div>
            <div style={{display:"flex"}}>
                <div style={{marginLeft:"auto", color:"red"}}>*필수사항</div> 
            </div>
            <div style={{height:"580px"}}>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>상품명</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder={item.data.item_name}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"62px", fontSize:"25px"}}>카테고리</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder={item.data.category}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"80px", fontSize:"25px"}}>공급처</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder={item.data.supplier}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"80px", fontSize:"25px"}}>공급가</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder={item.data.supply_price}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>판매가</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder={item.data.selling_price}></input>
                </div>
            </div>
            <div style={{display:"flex", marginTop:"auto"}}>
                <button style={{marginLeft:"290px", marginRight:"10px"}} className={ModuleStyle.whiteButton} onClick={() => setChangeModal(false)}>취소</button>
                <button className={ModuleStyle.blueButton}>상품 추가</button>
            </div>
        </Modal>}

        {(deleteModalOpen) && <Modal style={smallmodalstyle} isOpen={deleteModalOpen}>
            
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>상품 삭제</p>
            <p style={{marginTop:"75px", marginLeft:"0px", fontSize:"25px"}}>{item.data.item_name} 상품을 삭제하시겠습니까?</p>

            <div style={{display:"flex"}}>
                <button style={{marginLeft:"80px", marginRight:"10px", marginTop:"180px"}} className={ModuleStyle.whiteButton} onClick={() => setDeleteModal(false)}>취소</button>
                <button style={{marginTop:"180px"}} className={ModuleStyle.blueButton}>상품 삭제</button>
            </div>
        </Modal>}

        <Frame item={
            <div style={boxstyle}>
                {(loading) && <div>
                    <div style={{display:"flex", height:"150px"}}>
                    <p style={{fontSize:"30px", marginLeft:"30px", marginRight:"25px"}}>{item.data.item_name}</p>
                        <button style={{marginLeft:"1080px", marginTop:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {setDeleteModal(true)}}>삭제</button>
                        <button style={{marginLeft:"10px", marginTop:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {setChangeModal(true)}}>수정</button>
                    </div>
                    <div style={{display:"flex"}}>
                        <div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px"}}>상품 정보</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"150px"}}>상품명</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.item_name}</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"130px"}}>카테고리</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.category}</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"150px"}}> 공급가</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.supply_price}</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"150px"}}> 판매가</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.selling_price}</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"150px"}}> 입고일</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.stock_date}</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"120px"}}>보유 수량</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.stock_quantity}</p>
                            </div>
                            <div style={{display:"flex", height:"60px"}}>
                                <p style={{fontSize:"25px", marginLeft:"40px", marginRight:"130px"}}>공급처명</p>
                                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"25px"}}>{item.data.supplier}</p>
                            </div>
                        </div>
                        <div style={{display:"flex"}}>
                            <div style={bigImgbox}> <img src={item.data.item_image_uuid} style={{height:"500px"}}/></div>
                        </div>
                    </div>
                </div>}
            </div>
        }/>
    </div>
}

export default Detail