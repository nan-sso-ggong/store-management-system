import React, { useState, useEffect } from "react";
import Modal from "react-modal/lib/components/Modal";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"

import { FaPlus, FaMinus } from "react-icons/fa";

import axios from 'axios'

function Inventory(){

    const { search } = useSelector((state) => state)

    const [category, setcategory] = useState("카테고리를 선택하세요")
    const [open, setopen] = useState(false)
    const [name, setname] = useState("")
    const [supplier, setSupplier] = useState("")

    const [item, setitem] = useState([])
    const [loading, setLoading] = useState(false);
    const [storeLoading, setStoreLoading] = useState(false);
    const [page, setPage] = useState(0);

    const [modalopen, setmodal] = useState(false)
    const [smallModalopen, setSmallModal] = useState(false)
    const [completeModalopen, setCompleteModal] = useState(false)

    const [storeSearchModal, setStoreSearchModal] = useState(false)
    const [storeName, setStoreName] = useState("")
    const [storeAddr, setStroeAddr] = useState("")

    const [searchList, setSearchList] = useState([])
    const [isSearch, setSearch] = useState(false)

    const [selectList, setSelectList] = useState([])
    const [isSelect, setSelect] = useState(false)

    const [isComplete, setComplete] = useState(false)

    const [isHovering, setIsHovering] = useState(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);

    const listStyle = {
        zIndex:10,
        width:"300px",
        height:"40px",
        border:"1px solid #F0F1F3",
        backgroundColor:"white",
        textAlign:"center",
        lineHeight:"40px",
        position: "relative",
    }

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
            width: '1600px',
            height: '900px',
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

    const storeModalstyle = {
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
            width: '600px',
            height: '250px',
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

    const completeModalstyle = {
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
            width: '600px',
            height: '830px',
            margin: 'auto',
            border: '1px solid #ccc',
            background: '#fff',
            WebkitOverflowScrolling: 'touch',
            borderRadius: '1%',
            outline: 'none',
            padding: '2%',
            zIndex:20,

        }
    }

    const imgbox = {
        width : "200px",
        height : "200px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        margin : "auto"
    }

    const savename = event => {
        setname(event.target.value);
    };

    const saveSupplier = event => {
        setSupplier(event.target.value);
    }

    const saveStoreName = event => {
        setStoreName(event.target.value);
    };

    const saveStoreAddr = event => {
        setStroeAddr(event.target.value);
    };


    const dispatch = useDispatch();

    {/* 상단 search에서 검색하기 */}
    if (search.releaseSearch){
        const link = search.url + "/api/v1/headquarters/stock-management/stocks?item_name=" + search.releaseName + "&category=" + search.releaseCategory + "&supplier" + search.releaseSupplier
        axios.get(link)
        .then((response) => {
            setitem(response.data)

            setLoading(true)
            setStoreLoading(false)
        })
        .catch(function (error){
            console.log(error)
        })

        dispatch({ type: 'releaseEnd' });
    };

    {/* 점포 검색 */}
    const storeSearch = () => {
        const link = search.url + "/api/v1/headquarters/release-management/stocks?store_name=" + storeName + "&address=" + storeAddr

        axios.get(link)
        .then((response) => {
            setitem(response.data)

            setLoading(false)
        })
        .catch(function (error){
            console.log(error)
        })
        setLoading(false);
        setStoreLoading(true);
    };

    {/* 초기 default값 검색 */}
    useEffect(() => {
        const link = search.url + "/api/v1/headquarters/release-management/stocks?store_name= &address= "
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

    {/* 모달에서 검색 */}
    const searching = () => {
        const link = search.url + "/api/v1/headquarters/warehousing-management/warehousing-request?lack=0?item_name=" + name + "&category=" + category + "&supplier=" + supplier
        axios.get(link)
        .then((response) => {
            const templist = response.data;
            for (var i = 0; i < templist.data.pageInfo.totalElements; i++) {
                templist.data.data_list[i].add = 0
            }
            setSearchList(templist)
            setSearch(true)
        })
        .catch(function (error){
            console.log(error)
        })
    };

    {/* 모달에서 재고 부족 상품 검색 */}
    const lackSearching = () => {
        const link = search.url + "/api/v1/headquarters/warehousing-management/warehousing-request?lack=1?item_name=1&category=1&supplier=1" 
        axios.get(link)
        .then((response) => {
            const templist = response.data;
            for (var i = 0; i < templist.data.pageInfo.totalElements; i++) {
                templist.data.data_list[i].add = 0
            }
            setSearchList(templist)
            setSearch(true)
        })
        .catch(function (error){
            console.log(error)
        })
    };

    const releaseComplete = () => {
        const link = search.url + "/api/v1/headquarters/warehousing-management/warehousing-request"
        const data_list = []
        for (var i = 0; i< selectList.length; i++){
            data_list.push({"item_id":selectList.item_id, "additional_qunatity":selectList.add})
        }
        const data = {"data_list":data_list}
        const config = {"Content-Type": 'application/json'};
        axios.post(link, data, config)
        .then(res => {
            
        }).catch(err => {
            // 에러 처리
        });
        setSearch(false);
        setSearchList([]);
    }

    {/* 입고 신청 완료 목록 */}
    const completeResult = (isComplete) && selectList.map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"45px", display:"flex", width:"96.5%"}}>
            <div style={{display:"flex", margin:"20px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"0px"}}/>
                    <div style={{width:"200px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"120px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"70px", textAlign:"left"}}>{data.add}</div>
                </div>
            </div>
        </div> 
    })

    {/* 선택 목록 */}
    const selectResult = (isSelect) && selectList.map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"45px", display:"flex", width:"96.5%"}}>
            <div style={{display:"flex", margin:"20px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"45px"}}/>
                    <div style={{width:"250px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"190px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"200px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"220px", textAlign:"left"}}>{data.order_quantity}</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"70px", textAlign:"left"}}>{data.add}</div>
                    <FaMinus onClick={() => {
                        const updatedList = [...selectList.slice(0, index), ...selectList.slice(index + 1)];
                        setSelectList(updatedList);
                    }}/>
                </div>
            </div>
        </div> 
    })

    {/* 검색 목록 */}
    const searchResult = (isSearch) && searchList.data.data_list.map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"45px", display:"flex", width:"96.5%"}}>
            <div style={{display:"flex", margin:"20px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"45px"}}/>
                    <div style={{width:"250px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"190px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"200px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"220px", textAlign:"left"}}>{data.order_quantity}</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"70px", textAlign:"left"}}><input type="text" className={ModuleStyle.smallInputBox} placeholder={0} onChange={(e) => {
                        const updatedList = { ...searchList };
                        updatedList.data.data_list[index].add = e.target.value;
                        setSearchList(updatedList);
                    }}></input></div>
                    <FaPlus onClick={() => {
                        const templist = [...selectList, searchList.data.data_list[index]];
                        setSelectList(templist);
                        setSelect(true);
                    }}/>
                </div>
            </div>
        </div> 
    })

    {/* 기본 발주 목록 (상품검색, 카테고리, 공급처) */}
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
                    <div style={{width:"200px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"220px", textAlign:"left"}}>{data.order_quantity}</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"190px", textAlign:"left"}}>{data.warehousing_date}</div>
                    <div style={{width:"80px", textAlign:"left"}}>{data.stock_status}</div>
                
                </div>
            </div>
        </div> 
    })

    {/* 점포 검색 발주 목록 */}
    const storeResult = (storeLoading) && item.data.data_list.slice(page*10, (page+1)*10).map((data, index) => {
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
                    <div style={{width:"30px"}}/>
                    <div style={{width:"230px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"180px", textAlign:"left"}}>{data.order_quantity}</div>
                    <div style={{width:"160px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.warehousing_date}</div>
                    <div style={{width:"100px", textAlign:"left"}}>{data.stock_status}</div>
                    <div style={{width:"150px", textAlign:"left"}}>{data.store_name}</div>
                </div>
            </div>
        </div> 
    })


    return <div style={boxstyle}>
        {/* 점포 검색 모달 */}
        {(storeSearchModal) && <Modal style={storeModalstyle} isOpen={storeSearchModal}>
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>점포 검색</p>   
            <div style={{display:"flex", height:"60px"}}>
            <div style={{marginLeft:"20px", marginRight:"25px", marginTop:"10px", display:"flex"}}>
                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"55px"}}>점포명</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="점포명을 입력하세요" onChange={saveStoreName}></input>
            </div>
            </div>
            <div style={{display:"flex"}}>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px" , marginTop:"50px"}}>지역 검색</p>
                <input type="text" style={{marginTop:"50px"}} className={ModuleStyle.inputBox} placeholder=" 지역을 입력하세요" onChange={saveStoreAddr}></input>
                <button style={{marginLeft:"10px", marginTop:"50px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {storeSearch(); setStoreSearchModal(false);}}>검색</button>
            </div>
        </Modal>}

        {/* 입고 신청 모달 -> 입고 신청 버튼 */}
        {(smallModalopen) && <Modal style={smallModalstyle} isOpen={smallModalopen}>
            <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>입고 신청</p>
            <p style={{marginTop:"40px", marginLeft:"10px", fontSize:"25px"}}>입고 신청 하시겠습니까?</p>    
            <button style={{marginLeft:"120px", marginTop:"120px", }} className={ModuleStyle.whiteSmallButton} onClick={() => setSmallModal(false)}>취소</button>
            <button style={{marginLeft:"10px", marginTop:"120px", }} className={ModuleStyle.blueSmallButton} onClick={() => {setCompleteModal(true); setSmallModal(false); setComplete(true); releaseComplete();}}>입고 신청</button>
        </Modal>}

        {/* 입고 신청 모달 -> 입고 신청 완료 */}
        {(completeModalopen) && <Modal style={completeModalstyle} isOpen={completeModalopen}>
            <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>입고 신청</p>
            <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"25px"}}>신청 완료됐습니다.</p> 
            <p style={{marginTop:"60px", marginLeft:"10px", fontSize:"35px"}}>신청 내역</p>  
            <div style={{display:"flex"}}>
                <div style={{width:"30px", textAlign:"left"}}/>
                <div style={{width:"200px", textAlign:"left"}}>상품명</div>
                <div style={{width:"120px", textAlign:"left"}}>공급가(원)</div>
                <div style={{width:"180px", textAlign:"left"}}>공급처</div>
                <div style={{width:"40px", textAlign:"left"}}>수량</div> 
            </div>
            <div style={{height:"480px", overflow:"auto"}}>
                    {completeResult}
            </div>
            <button style={{marginLeft:"250px", marginTop:"30px", }} className={ModuleStyle.whiteSmallButton} onClick={()=> {setCompleteModal(false); setmodal(false); setSelect(false); setSelectList([])}}>닫기</button>
        </Modal>}

        {/* 입고 신청 모달 */}
        {(modalopen) && <Modal style={modalstyle} isOpen={modalopen}>
            {/* 입고 신청 모달 - 검색 */}
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>입고 신청</p>   
            <div style={{marginLeft:"0px", marginRight:"25px", marginTop:"-25px", display:"flex", height:"100px"}}>
                <p style={{fontSize:"25px", marginLeft:"0px", marginRight:"25px"}}>상품검색</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="상품명을 입력하세요" onChange={savename}></input>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px"}}>카테고리</p>
                <div>
                    <div style={{marginTop:"20px"}} className={ModuleStyle.inputBox} onClick={() => setopen(true)}>{category}</div>
                    {(open) && <div style={{height:"250px", overflow:"auto"}}>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("아이스크림")}}>아이스크림</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("라면")}}>라면</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("음료")}}>음료</div>
                        <div style={listStyle} onClick={() => {setopen(false); setcategory("과자")}}>과자</div>
                    </div>}
                </div>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px"}}>공급처</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="공급처명을 입력하세요" onChange={saveSupplier}></input>
                
            </div>
            <div style={{display:"flex", height:"50px", borderBottom:"1px solid #D0D3D9"}}>
                <button style={{marginLeft:"1350px"}} className={ModuleStyle.whiteSmallLongButton} onClick={lackSearching}>재고 부족 상품 검색</button>
                <button style={{marginLeft:"10px"}} className={ModuleStyle.whiteSmallButton} onClick={searching}>검색</button>
            </div>
            {/* 입고 신청 모달 - 검색 결과 */}
            <div>
                <p style={{marginTop:"20px", marginLeft:"0px", fontSize:"30px"}}>검색 결과</p>
                <div style={{display:"flex"}}>
                    <div style={{width:"0px"}}/>
                    <div style={{width:"300px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"280px", textAlign:"left"}}>공급가(원)</div>
                    <div style={{width:"280px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"280px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"280px", textAlign:"left"}}>보유 수량</div>
                    <div style={{width:"150px", textAlign:"left"}}>추가</div>
                </div>
                <div style={{height:"500px", borderBottom:"1px solid #D0D3D9", overflow:"auto"}}>
                    {searchResult}
                </div>
            </div>
            {/* 입고 신청 모달 - 선택 목록 */}
            <div>
                <p style={{marginTop:"20px", marginLeft:"0px", fontSize:"30px"}}>선택 목록</p>
                <div style={{display:"flex"}}>
                    <div style={{width:"0px"}}/>
                    <div style={{width:"300px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"280px", textAlign:"left"}}>공급가(원)</div>
                    <div style={{width:"280px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"280px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"280px", textAlign:"left"}}>보유 수량</div>
                    <div style={{width:"150px", textAlign:"left"}}>추가 수량</div>
                </div>
                <div style={{height:"500px", borderBottom:"1px solid #D0D3D9", overflow:"auto"}}>
                    {selectResult}
                </div>
            </div>
            <div style={{display:"flex", height:"50px"}}>
                <button style={{marginLeft:"1350px", marginTop:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => setmodal(false)}>취소</button>
                <button style={{marginLeft:"10px", marginTop:"30px"}} className={ModuleStyle.blueSmallButton} onClick={() => {(isSelect) && setSmallModal(true); (isSelect == false) && alert("입고 신청 내역이 존재하지 않습니다.")}}>입고 신청</button>
            </div>
        </Modal>}


        <div style={{display:"flex"}}>
            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"1100px", fontSize:"30px"}}>발주목록</p>
            <button style={{marginTop:"20px", marginRight:"10px"}} className={ModuleStyle.blueSmallButton} onClick={() => {setmodal(true)}}>상품 추가</button>
            <button style={{marginTop:"20px", marginRight:"10px"}} className={ModuleStyle.whiteSmallButton} onClick={() => {setStoreSearchModal(true)}}>점포 검색</button>
        </div>

        {/* 기본 발주 목록 (상품검색, 카테고리, 공급처)*/}
        {(loading) &&
        <div>
            <div style={{height:"600px"}}>
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

        {/* 점포 검색 발주 목록 */}
        {(storeLoading) &&
        <div>
            <div style={{height:"600px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"70px"}}/>
                    <div style={{width:"220px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"170px", textAlign:"left"}}>공급가(원)</div>
                    <div style={{width:"180px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"180px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"160px", textAlign:"left"}}>보유 수량</div>
                    <div style={{width:"170px", textAlign:"left"}}>발주 신청 날짜</div>
                    <div style={{width:"100px", textAlign:"left"}}>상태</div>
                    <div style={{width:"200px", textAlign:"left"}}>점포명</div>
                </div>
                {storeResult}
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