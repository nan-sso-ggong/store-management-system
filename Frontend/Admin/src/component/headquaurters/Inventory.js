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
    const [modalopen, setmodal] = useState(false)
    const [smallModalOpen, setSmallModal] = useState(false)

    const [isHovering, setIsHovering] = useState(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);

    const [imageSrc, setImageSrc] = useState(null);
    const [addName, setAddName] = useState("")
    const [addCategory, setAddCategory] = useState("")
    const [addSupplier, setAddSupplier] = useState("")
    const [addPrice, setAddPrice] = useState("")


    const boxstyle = {
        width:"1500px",
        height:"795px",
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

    const imgbox = {
        width : "200px",
        height : "200px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        margin : "auto",
    }
    
    useEffect(() => {
        const link = search.url + "/api/v1/headquarters/stock-management/stocks?category= &item_name= "
        axios.get(link)
        .then((response) => {
            setitem(response.data)
            console.log(response.data)
            setLoading(false)
        })
        .catch(function (error){
            console.log(error)
        })
    }, [])
    
    const dispatch = useDispatch();

    if (search.headquaurtersSearch){
        const link = search.url + "/api/v1/headquarters/stock-management/stocks?category=" + search.headCategory + "&item_name=" + search.headName
        axios.get(link)
        .then((response) => {
            setitem(response.data)
            console.log(response.data)
            setLoading(true)
        })
        .catch(function (error){
            console.log(error)
        })

        dispatch({ type: 'headquaurtersEnd' });
    }

    const result = (loading) && item.data.data_list.slice(page*10, (page+1)*10).map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"50px", display:"flex", width:"96.5%", backgroundColor:isHovering[index]}}>
            <Link to={"/admin/detail/"+"cokacola"}  style={{ textDecoration: "none" }}>
                <div style={{display:"flex", margin:"20px"}}>
                    <div style={{display:"flex"}} onMouseOver={() => {
                        const temp = ["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"];
                        temp[index] = "#2B7175";
                        setIsHovering(temp);
                    }} 
                    onMouseOut={() => {
                        setIsHovering(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);
                    }}>
                        <div style={{display:"flex"}}>
                        <div style={{width:"100px"}}/>
                        <div style={{width:"450px", textAlign:"left"}}>{data.item_name}</div>
                        <div style={{width:"300px", textAlign:"left"}}>{data.supply_price}</div>
                        <div style={{width:"300px", textAlign:"left"}}>{data.stock_quantity}</div>
                        <div style={{width:"300px", textAlign:"left"}}>{data.warehousing_date}</div>
                    </div>
                    </div>
                </div>
            </Link>
        </div> 
    })

    {/* url 수정 필요 */}
    const addItem = () => {
        const link = search.url + "/api/v1/headquarters/stock-management/stocks"
        
        const formData = new FormData()
        formData.append('imageFile',imageSrc)
        
        const data = {
            "item_name": addName,
            "category": addCategory,
            "supplier": addSupplier,
            "supply_price": addPrice,
        };

        formData.append(
            "data",
            new Blob([JSON.stringify(data)], { type: "application/json" })
          );
        const config = {"Content-Type": 'multipart/form-data'};
        axios.post(link, formData, config)
        .then(res => {
            // 성공 처리
        }).catch(err => {
            // 에러 처리
        });
        setSmallModal(true)
    }
    const AddNameChange = event => {
        setAddName(event.target.value);
    };
    const AddCategoryChange = event => {
        setAddCategory(event.target.value);
    };
    const AddSupplierChange = event => {
        setAddSupplier(event.target.value);
    };
    const AddPriceChange = event => {
        setAddPrice(event.target.value);
    };

    const onUpload = (e) => {
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);

        return new Promise((resolve) => { 
            reader.onload = () => {	
                setImageSrc(reader.result || null); // 파일의 컨텐츠
                resolve();
            };
        });
    }

    return <div style={boxstyle}>

        {/* 상품 추가 완료 모달 */}
        {(smallModalOpen) && <Modal style={smallModalstyle} isOpen={smallModalOpen}>
        <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>상품 추가</p>
            <p style={{marginTop:"40px", marginLeft:"10px", fontSize:"25px"}}>{addName} 상품이 추가되었습니다.</p>    
            <button style={{marginLeft:"180px", marginTop:"120px", }} className={ModuleStyle.whiteSmallButton} onClick={() => {setSmallModal(false); setmodal(false);}}>닫기</button>
        </Modal>}

        {/* 상품 추가 모달 */}
        {(modalopen) && <Modal style={modalstyle} isOpen={modalopen}>
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>상품 추가</p>   
            <div style={{display:"flex"}}>
                <label style={imgbox} for="input-file">
                    {(imageSrc == null) && <div style={{marginTop:"80px", marginLeft:"20px"}}><p style={{fontSize:"25px"}}> 이미지 추가하기</p></div>} 
                    {(imageSrc != null ) && <div style={{display:"flex"}}><div style={{margin:"auto"}}><img style={{height:"200px"}} src={imageSrc} /></div></div>}
                </label>
                <input accept="image/*" multiple type="file" onChange={e => onUpload(e)} style={{display:"none"}} id="input-file" />
            </div>
            <div style={{display:"flex"}}>
                <div style={{marginLeft:"auto", color:"red"}}>*필수사항</div> 
            </div>
            <div style={{height:"580px"}}>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"0px", fontSize:"25px"}}>상품명</p>
                    <p style={{marginTop:"35px", marginRight:"72px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   상품명을 입력하세요" onChange={AddNameChange}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"62px", fontSize:"25px"}}>카테고리</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   카테고리를 입력하세요" onChange={AddCategoryChange}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"80px", fontSize:"25px"}}>공급처</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   공급처명을 입력하세요" onChange={AddSupplierChange}></input>
                </div>
                <div style={{display:"flex"}}>
                    <p style={{marginTop:"35px", marginRight:"5px", fontSize:"25px"}}>공급가</p>
                    <p style={{marginTop:"35px", marginRight:"63px", fontSize:"25px", color:"red"}}>*</p>
                    <input type="text" className={ModuleStyle.dropdownBox} placeholder="   공급가를 입력하세요" onChange={AddPriceChange}></input>
                </div>
            </div>
            <div style={{display:"flex", marginTop:"auto"}}>
                <button style={{marginLeft:"290px", marginRight:"10px"}} className={ModuleStyle.whiteButton} onClick={() => setmodal(false)}>취소</button>
                <button className={ModuleStyle.blueButton} onClick={addItem}>상품 추가</button>
            </div>
        </Modal>}

        <div style={{display:"flex"}}>
            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"1200px", fontSize:"30px"}}>보유재고</p>
            <button style={{marginTop:"50px"}} className={ModuleStyle.blueSmallButton} onClick={() => {setmodal(true)}}>상품 추가</button>
        </div>
        {(loading) &&
        <div>
            <div style={{height:"660px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"130px"}}/>
                    <div style={{width:"450px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"300px", textAlign:"left"}}>판매가(원)</div>
                    <div style={{width:"300px", textAlign:"left"}}>재고</div>
                    <div style={{width:"300px", textAlign:"left"}}>입고일</div>
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