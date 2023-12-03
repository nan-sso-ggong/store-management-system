import axios from "axios";
import React, { useEffect, useState } from "react";
import Modal from "react-modal/lib/components/Modal";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

function Home() {

    const [stores, setStores] = useState([])
    const [loading, setLoading] = useState(false)
    const [modal, setModal] = useState([false, false, false, false, false, false, false, false, false, false,
                                        false, false, false, false, false, false, false, false, false, false])
    const [successModal, setSuccessModal] = useState(false)
    const [makeModal, setMakeModal] = useState(false)
    const [successModal2, setSuccessModal2] = useState(false)
    const [phoneNumber, setPhone] = useState("")
    const [address, setAddress] = useState("")
    const [store_name, setStoreName] = useState("")
    const [image, setImage] = useState(null);

    const dispatch = useDispatch();
    const navigater = useNavigate()

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
            width: '330px',
            height: '210px',
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

    const Modalstyle = {
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
            height: '350px',
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

    const blueSmallButton = {
        width: "90px",
        height: "35px",
        backgroundColor: "#1366D9",
        borderRadius: "5px",
        color: "white",
        fontSize: "16px",
        outline: "none",
        boxShadow: "none",
        marginLeft:"10px"
    }

    const whiteSmallButton = {
        width: "90px",
        height: "35px",
        backgroundColor: "#FFFFFF",
        border:"1px solid lightgray",
        borderRadius: "5px",
        color: "#858D9D",
        fontSize: "16px",
        outline: "none",
        boxShadow: "none",
    }

    const editSmallButton = {
        width: "110px",
        height: "40px",
        backgroundColor: "white",
        borderRadius: "5px",
        border: "1px solid #E8F1FD",
        color: "#1366D9",
        fontSize: "16px",
        outline: "none",
        boxShadow: "none",
        display:"table",
        textAlign:"center",
    }

    useEffect(() => {
        const url = "http://13.125.112.60:8080/api/v1/managers/store"
        axios.get(url)
        .then((response) => {
            if (response.data.success){
                setStores(response.data.data)
                console.log(response.data)
                setLoading(true)
            }
        })
        .catch(function (error){
            console.log(error)
        })
    }, [])

    const editStore = () => {
        const url = "http://13.125.112.60:8080/api/v1/managers/store/{storeId}/edit"
        
        const data = {
            "store_address":address,
            "store_call_number":phoneNumber
        };

        const config = {"Content-Type": 'application/json'};

        axios.patch(url, data, config)
        .then(response => {
            if (response.data.success){
                setSuccessModal(true);
            }
            else
            {
                alert("편집에 실패했습니다!")
            }
        }).catch(err => {
            alert("편집에 실패했습니다!")// 에러 처리
        });
    }

    const addStore = () => {
        const url = "http://13.125.112.60:8080/api/v1/managers/stores"
        
        const formData = new FormData()
        formData.append('imageFile',image)

        const data = {
            "name":store_name,
            "address":address,
            "callNumber":phoneNumber,
        };

        formData.append(
            "requestDto",
            new Blob([JSON.stringify(data)], { type: "application/json" })
        );

        const config = {"Content-Type": 'multipart/form-data'};

        axios.patch(url, formData, config)
        .then(response => {
            if (response.data.success){
                if (response.data.success){
                    setSuccessModal2(true);
                }
                else{
                    alert("점포 추가 신청에 실패했습니다!")
                }
            }
            else
            {
                alert("점포 추가 신청에 실패했습니다!")
            }
        }).catch(err => {
            alert("점포 추가 신청에 실패했습니다!")// 에러 처리
        });
    }

    const changeAddr = (event) => {
        setAddress(event.target.value)
    }

    const changePhone = (event) => {
        setPhone(event.target.value)
    }

    const changeName = (event) => {
        setStoreName(event.target.value)
    }

    const onUpload = (e) => {
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);

        return new Promise((resolve) => { 
            reader.onload = () => {	
                setImage(reader.result || null); // 파일의 컨텐츠
                resolve();
            };
        });
    }

    const storeList = (loading) && stores.map((data, index) => {
        return <div style={{border:"1px solid #F0F1F3", borderRadius:"5px", marginBottom:"5px", padding:"10px"}}>
            <div style={{display:"flex"}}>
                <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"20px"}}>CS25 {data.store_name}</p>
                <div style={{marginLeft:"auto"}}>
                    <div style={editSmallButton}> <p style={{display:"table-cell", verticalAlign:"middle"}} onClick={() => {
                        setPhone(data.store_tel);
                        setAddress(data.address);
                        const temp = [...modal];
                        temp[index] = true;
                        setModal(temp);
                    }}>편집</p></div>
                </div>
            </div>
            <div style={{display:"flex"}} onClick={() => {
                const d = { store_name: data.store_name };
                dispatch({ type: 'storeSave', payload: d });
            }}>
                <img src={data.store_image} style={{width:"400px"}}/>
                <div style={{marginLeft:"20px"}}>
                    <p style={{color:"#5D6679"}}>{data.store_name}</p>
                    <p style={{color:"#5D6679"}}>주소 : {data.address}</p>
                    <p style={{color:"#5D6679"}}>연락처 : {data.store_tel}</p>
                </div>
            </div>
            {(modal[index]) && <Modal style={smallModalstyle} isOpen={modal[index]}>
            <div style={{display:"flex"}}>
                <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"20px"}}>현재 점포명</p>
                <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"15px", color:"#1366D9", marginTop:"15px"}}>{data.store_name}</p>
            </div>
            <div style={{display:"flex"}}>
                <p style={{marginTop:"20px", marginLeft:"10px", fontSize:"15px", width:"100px", color:"#48505E"}}>주소</p>
                <textArea placeholder={address} onChange={() => {setAddress();}} style={{width:"200px", height:"40px", fontSize:"10px", resize:"none", border:"1px solid lightgray"}}/>
            </div>
            <div style={{display:"flex", marginTop:"10px"}}>
                <p style={{marginTop:"7px", marginLeft:"10px", fontSize:"15px", width:"100px", color:"#48505E"}}>연락처</p>
                <input type="text" placeholder={phoneNumber} onChange={() => {setPhone();}} style={{width:"200px", height:"20px", fontSize:"10px", border:"1px solid lightgray"}}/>
            </div>
            <div style={{style:"flex", marginTop:"15px", marginLeft:"130px"}}>
                <button style={whiteSmallButton} onClick={() => {
                    setPhone("");
                    setAddress("");
                    const temp = [...modal];
                    temp[index] = false;
                    setModal(temp);
                }}>취소</button>
                <button style={blueSmallButton} onClick={() => {editStore();}}>수정</button>
            </div>
            </Modal>}
            {(successModal) && <Modal style={smallModalstyle} isOpen={modal[index]}>
                <p style={{marginTop:"0px", marginLeft:"10px", fontSize:"25px"}}>점포 수정</p>
                <p style={{marginTop:"0px", marginLeft:"10px", fontSize:"15px"}}>수정이 완료되었습니다.</p>
                <div style={{marginLeft:"120px", marginTop:"100px"}}>
                    <button style={whiteSmallButton} onClick={() => {
                        setPhone("");
                        setAddress("");
                        const temp = [...modal];
                        temp[index] = false;
                        setModal(temp);
                        setSuccessModal(false);
                    }}>취소</button>
                </div>
            </Modal>}
    </div>})

    

    return <div style={{backgroundColor:"#F0F1F3"}}>
        <div style={{padding:"10px"}}>
            <div style={{backgroundColor:"white", width:"100%", height:"100vh"}}>
                <div style={{display:"flex", padding:"10px"}}>
                <p style={{marginTop:"0px", marginLeft:"10px", fontSize:"30px"}}>점포선택</p>
                    <div style={{marginLeft:"auto"}}>
                        <button style={blueSmallButton} onClick={()=>{setMakeModal(true);}}>점포 추가</button>
                    </div>
                </div>
                <div style={{padding:"10px"}}>
                    <div style={{width:"100%", height:"85vh", overflow:"auto", padding:"5px"}}>
                        {storeList}
                    </div>
                </div>
            </div>
        </div>

        {(makeModal) && <Modal style={Modalstyle} isOpen={makeModal}>
            <p style={{marginTop:"0px", marginLeft:"10px", fontSize:"25px", color:"#383E49"}}>점포 추가</p>
            <div style={{display:"flex"}}>
                <p style={{marginTop:"7px", marginLeft:"10px", fontSize:"15px", width:"100px", color:"#48505E"}}>점포명</p>
                <input type="text" onChange={() => {setStoreName();}} style={{width:"200px", height:"20px", fontSize:"10px", resize:"none", border:"1px solid lightgray"}}/>
            </div>
            <div style={{display:"flex", marginTop:"10px"}}>
                <p style={{marginTop:"7px", marginLeft:"10px", fontSize:"15px", width:"100px", color:"#48505E"}}>주소</p>
                <textArea onChange={() => {setAddress();}} style={{width:"200px", height:"40px", fontSize:"10px", resize:"none", border:"1px solid lightgray"}}/>
            </div>
            <div style={{display:"flex", marginTop:"20px"}}>
                <p style={{marginTop:"7px", marginLeft:"10px", fontSize:"15px", width:"100px", color:"#48505E"}}>연락처</p>
                <input type="text" onChange={() => {setPhone();}} style={{width:"200px", height:"20px", fontSize:"10px", resize:"none", border:"1px solid lightgray"}}/>
            </div>
            <label style={{display:"flex"}} for="input-file">
                    <p style={{marginTop:"25px", marginLeft:"10px", fontSize:"15px", width:"100px", color:"#48505E"}}>이미지</p>
                    {(image == null) && <div style={{marginTop:"20px"}}>
                        <div style={{width:"200px", height:"60px", fontSize:"10px", resize:"none", border:"1px solid lightgray", display:"flex"}}>
                            <p style={{fontSize:"15px", color:"#48505E", margin:"auto"}}>불러오기</p>
                        </div>
                    </div>} 
                    {(image != null ) && <div style={{display:"flex"}}><div style={{margin:"auto"}}><img style={{height:"60px", width:"200px"}} src={image} /></div></div>}
            </label>
            <input accept="image/*" multiple type="file" onChange={e => onUpload(e)} style={{display:"none"}} id="input-file" />

            <div style={{style:"flex", marginTop:"15px", marginLeft:"130px"}}>
                <button style={whiteSmallButton} onClick={() => {setMakeModal(false); setAddress(""); setStoreName(""); setPhone("");}}>취 소</button>
                <button style={blueSmallButton} onClick={() => {addStore();}}>점포 추가</button>
            </div>
        </Modal>}

        {(successModal2) && <Modal style={Modalstyle} isOpen={successModal2}>
            <p style={{marginTop:"0px", marginLeft:"10px", fontSize:"25px", color:"#383E49"}}>점포 추가</p>
            <p style={{marginTop:"0px", marginLeft:"10px", fontSize:"15px"}}>점포 추가 신청 완료되었습니다.</p>
            <div style={{marginTop:"150px" ,marginLeft:"120px"}}>
                <button style={whiteSmallButton} onClick={() => {setMakeModal(false); setSuccessModal2(false)}}>닫기</button>
            </div>
        </Modal>}
    </div>
  }
  
  export default Home;