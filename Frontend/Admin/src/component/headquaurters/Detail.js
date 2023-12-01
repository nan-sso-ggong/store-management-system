import React, {useState, useEffect} from "react";
import { Link, useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import axios from 'axios'
import Modal from "react-modal/lib/components/Modal";
import ModuleStyle from "../../ModuleStyle.module.css"
import Frame from "../Frame";

function Detail(){

    const { search } = useSelector((state) => state)

    const params = useParams()
    //const [name , setname] = useState("users")
    const [item, setitem] = useState([])
    const [loading, setLoading] = useState(false);
    const [changeModalOpen, setChangeModal] = useState(false)
    const [deleteModalOpen, setDeleteModal] = useState(false)
    const [smallEditModalOpen, setSmallEditModal] = useState(false)
    const [smallDeleteModalOpen, setSmallDeleteModal] = useState(false)

    const [imageSrc, setImageSrc] = useState(null);
    const [editName, setEditName] = useState("")
    const [editCategory, setEditCategory] = useState("")
    const [editSupplier, setEditSupplier] = useState("")
    const [editPrice, setEditPrice] = useState("")

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

        const link = search.url + "/api/v1/headquarters/stock-management/stocks/" + params.id
        axios.get(link)
        .then((response) => {
            setitem(response.data)

            setImageSrc(item.data.item_image_uuid)
            setEditName(item.data.item_name)
            setEditCategory(item.data.category)
            setEditSupplier(item.data.supplier)
            setEditPrice(item.data.supply_price)
            setLoading(true)
        })
        .catch(function (error){
            console.log(error)
        })
    }, []);

    {/* url 수정 필요 */}
    const editItem = () => {
        const link = search.url + "/api/v1/headquarters/stock-management/stocks" + params.id
        
        const formData = new FormData()
        formData.append('imageFile',imageSrc)
        
        const data = {
            "item_name": editName,
            "category": editCategory,
            "supplier": editSupplier,
            "supply_price": editPrice,
        };

        formData.append(
            "data",
            new Blob([JSON.stringify(data)], { type: "application/json" })
          );
        const config = {"Content-Type": 'multipart/form-data'};

        axios.patch(link, formData, config)
        .then(res => {
            // 성공 처리
        }).catch(err => {
            // 에러 처리
        });
        setSmallEditModal(true)
    }
    const EditNameChange = event => {
        setEditName(event.target.value);
    };
    const EditCategoryChange = event => {
        setEditCategory(event.target.value);
    };
    const EditSupplierChange = event => {
        setEditSupplier(event.target.value);
    };
    const EditPriceChange = event => {
        setEditPrice(event.target.value);
    };
    
    const deleteItem = () => {
        const link = search.url + "/api/v1/headquarters/stock-management/stocks" + params.id
        axios.delete(link)
        .then(res => {
            // 성공 처리
        }).catch(err => {
            // 에러 처리
        });
    }

    return <div className={ModuleStyle.pagestyle}> 

        {/* 상품 정보 수정 완료 모달 */}
        {(smallEditModalOpen) && <Modal style={smallmodalstyle} isOpen={smallEditModalOpen}>
            <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>상품 수정</p>
            <p style={{marginTop:"70px", marginLeft:"10px", fontSize:"25px"}}>{editName} 상품이 수정되었습니다.</p>    
            <button style={{marginLeft:"180px", marginTop:"200px", }} className={ModuleStyle.whiteSmallButton} onClick={() => {setSmallEditModal(false); setChangeModal(false);}}>닫기</button>
        </Modal>}

        {/* 상품 정보 수정 모달*/}
        {(changeModalOpen) && <Modal style={modalstyle} isOpen={changeModalOpen}>
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>상품 수정</p>   
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
                    <p style={{marginTop:"35px", marginRight:"5px", fontSize:"25px"}}>공급가</p>
                    <p style={{marginTop:"35px", marginRight:"65px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder={item.data.supply_price}></input>
                </div>
            </div>
            <div style={{display:"flex", marginTop:"auto"}}>
                <button style={{marginLeft:"290px", marginRight:"10px"}} className={ModuleStyle.whiteButton} onClick={() => setChangeModal(false)}>취소</button>
                <button className={ModuleStyle.blueButton} onClick={editItem}>상품 수정</button>
            </div>
        </Modal>}

        {/* 상품 삭제 완료 모달 */}
        {(smallDeleteModalOpen) && <Modal style={smallmodalstyle} isOpen={smallDeleteModalOpen}>
            <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>상품 삭제</p>
            <p style={{marginTop:"70px", marginLeft:"10px", fontSize:"25px"}}>{editName} 상품이 삭제되었습니다.</p>    
            <button style={{marginLeft:"180px", marginTop:"200px", }} className={ModuleStyle.whiteSmallButton} onClick={() => {setSmallDeleteModal(false); setDeleteModal(false);}}>닫기</button>
        </Modal>}

        {/* 상품 삭제 모달 */}
        {(deleteModalOpen) && <Modal style={smallmodalstyle} isOpen={deleteModalOpen}>
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>상품 삭제</p>
            <p style={{marginTop:"75px", marginLeft:"0px", fontSize:"25px"}}>{item.data.item_name} 상품을 삭제하시겠습니까?</p>
            <div style={{display:"flex"}}>
                <button style={{marginLeft:"80px", marginRight:"10px", marginTop:"180px"}} className={ModuleStyle.whiteButton} onClick={() => setDeleteModal(false)}>취소</button>
                <button style={{marginTop:"180px"}} className={ModuleStyle.blueButton} onClick={() => {deleteItem(); setSmallDeleteModal(true);}}>상품 삭제</button>
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