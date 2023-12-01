import React, { useState, useEffect } from "react";
import Modal from "react-modal/lib/components/Modal";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import ModuleStyle from "../../ModuleStyle.module.css"

import axios from 'axios'

function Inventory(){

    const { search } = useSelector((state) => state)

    const [searchList, setSearchList] = useState([])
    const [isSearch, setSearch] = useState(false)

    const [name, setname] = useState("")
    const [address, setAddress] = useState("")

    const [item, setitem] = useState([])
    const [loading, setLoading] = useState(false);
    const [page, setPage] = useState(0);
    const [modalopen, setmodal] = useState(false)
    const [smallModalopen, setSmallModal] = useState(false)

    const [isHovering, setIsHovering] = useState(["#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"]);
    const [isAllCheck, setAllcheck] = useState(false)

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
            width: '1600px',
            height: '920px',
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
        width : "200px",
        height : "200px",
        border : "5px dashed lightgray",
        borderRadius : "25px",
        margin : "auto"
    }

    const savename = event => {
        setname(event.target.value);
    };

    const saveaddr = event => {
        setAddress(event.target.value);
    };

    const dispatch = useDispatch();

    {/* 상단 search에서 검색하기 */}
    if (search.warehousingSearch){
        const link = search.url + "/api/v1/headquarters/release-management/stocks?store_name=" + search.wareName + "&address=" + search.wareAddr
        axios.get(link)
        .then((response) => {
            setitem(response.data)
            console.log(response.data)
            setLoading(true)
        })
        .catch(function (error){
            console.log(error)
        })

        dispatch({ type: 'warehousingEnd' });
    }
    
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

    {/* 모달에서 발주 목록 검색 */}
    const searching = () => {
        const link = search.url + "/api/v1/headquarters/release-management/release-request?store_name=" + name + "&address=" + address
        axios.get(link)
        .then((response) => {
            const templist = response.data;
            for (var i = 0; i < templist.data.pageInfo.totalElements; i++) {
                templist.data.data_list[i].select = false
            }
            setSearchList(templist)
            setSearch(true)
        })
        .catch(function (error){
            console.log(error)
        })
    };

    {/* 일괄 선택 */}
    const allSelect = (e) => {
        var trueCnt = 0;
        var falseCnt = 0;
        const templist = {...searchList}
        for(var i=0; i < searchList.data.data_list.length; i++){
            if (searchList.data.data_list[i].select == false){ falseCnt++; }
            else { trueCnt++; }
        }
        if (falseCnt == 0)
        {
            for(var i=0; i < searchList.data.data_list.length; i++) { templist.data.data_list[i].select = false; }
        }
        else
        {
            for(var i=0; i < searchList.data.data_list.length; i++) { templist.data.data_list[i].select = true; }
        }
        setSearchList(templist);
        if (trueCnt != searchList.data.data_list.length)
            { setAllcheck(true); }
        else 
            { setAllcheck(false); }
    }

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
                    <div style={{width:"240px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"165px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"190px", textAlign:"left"}}>{data.order_quantity}</div>
                    <div style={{width:"150px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.order_date}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.stock_status}</div>
                    <div style={{width:"150px", textAlign:"left"}}>{data.store_name}</div>
                </div>
            </div>
        </div> 
    })

    {/* 검색 목록 */}
    const searchResult = (isSearch) && searchList.data.data_list.map((data, index) => {
        return <div style={{margin:"10px", borderBottom:"1px solid #D0D3D9", height:"45px", display:"flex", width:"96.5%"}}>
            <div style={{display:"flex", margin:"20px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"200px", textAlign:"left"}}>{data.item_name}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.selling_price}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.supplier}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.order_quantity}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.stock_quantity}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.order_date}</div>
                    <div style={{width:"170px", textAlign:"left"}}>{data.stock_status}</div>
                    <div style={{width:"200px", textAlign:"left"}}>{data.store_name}</div>
                    <input type="checkbox" checked={searchList.data.data_list[index].select} onChange={(e) => {
                        const updatedList = { ...searchList };
                        updatedList.data.data_list[index].select = !updatedList.data.data_list[index].select;
                        setSearchList(updatedList);

                        if (searchList.data.data_list[index].select == false){ setAllcheck(false); }
                    }}></input>
                    
                </div>
            </div>
        </div> 
    })

    {/* 출고 신청 */}
    const warehouse = () => {
        
        const order_ids = []
        for (var i=0; i<searchList.data.data_list.length; i++){
            if (searchList.data.data_list[i].select == true){ order_ids.push(searchList.data.data_list[i].order_id) }
        }
        const data = {"order_ids" : order_ids}
        const config = {"Content-Type": 'application/json'};
        const link = search.url + "/api/v1/headquarters/release-management/release-request"

        axios.patch(link, data, config)
        .then(res => {
            // 성공 처리
        }).catch(err => {
            // 에러 처리
        });
    }


    return <div style={boxstyle}>

        {/* 입고 신청 모달 -> 입고 신청 버튼 */}
        {(smallModalopen) && <Modal style={smallModalstyle} isOpen={smallModalopen}>
            <p style={{marginTop:"10px", marginLeft:"10px", fontSize:"35px"}}>출고 신청</p>
            <p style={{marginTop:"40px", marginLeft:"10px", fontSize:"25px"}}>출고 신청 하시겠습니까?</p>    
            <button style={{marginLeft:"120px", marginTop:"120px", }} className={ModuleStyle.whiteSmallButton} onClick={() => setSmallModal(false)}>취소</button>
            <button style={{marginLeft:"10px", marginTop:"120px", }} className={ModuleStyle.blueSmallButton} onClick={() => {setSmallModal(false); warehouse();}}>출고 신청</button>
        </Modal>}

        {/* 출고 신청 모달 */}
        {(modalopen) && <Modal style={modalstyle} isOpen={modalopen}>
            {/* 출고 신청 모달 - 검색 */}
            <p style={{marginTop:"0px", marginLeft:"0px", fontSize:"35px"}}>출고 신청</p>   
            <div style={{display:"flex", height:"60px"}}>
            <div style={{marginLeft:"20px", marginRight:"25px", marginTop:"-25px", display:"flex"}}>
                <p style={{fontSize:"25px", marginLeft:"5px", marginRight:"55px"}}>점포명</p>
                <input type="text" className={ModuleStyle.inputBox} placeholder="점포명을 입력하세요" onChange={savename}></input>
            </div>
            </div>
            <div style={{display:"flex", borderBottom:"1px solid #D0D3D9"}}>
                <p style={{fontSize:"25px", marginLeft:"25px", marginRight:"25px" , marginTop:"5px"}}>지역 검색</p>
                <input type="text" style={{marginTop:"0px"}} className={ModuleStyle.inputBox} placeholder=" 지역을 입력하세요" onChange={saveaddr}></input>
                <button style={{marginLeft:"10px", marginTop:"0px"}} className={ModuleStyle.whiteSmallButton} onClick={searching}>검색</button>
            </div>
 
            {/* 출고 신청 모달 - 검색 결과 */}
            <div>
                <p style={{marginTop:"20px", marginLeft:"0px", fontSize:"30px"}}>발주 목록</p>
                <div style={{display:"flex"}}>
                    <div style={{width:"30px"}}/>
                    <div style={{width:"200px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"170px", textAlign:"left"}}>공급가(원)</div>
                    <div style={{width:"170px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"170px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"170px", textAlign:"left"}}>보유 수량</div>
                    <div style={{width:"170px", textAlign:"left"}}>발주 신청 날짜</div>
                    <div style={{width:"170px", textAlign:"left"}}>상태</div>
                    <div style={{width:"200px", textAlign:"left"}}>점포명</div>
                    <input type="checkbox" checked={isAllCheck} onChange={() => {allSelect();}}></input>
                    <p style={{marginTop:"0px", marginLeft:"30px", fontSize:"10px"}}>일괄 선택</p>
                </div>
                <div style={{height:"550px", borderBottom:"1px solid #D0D3D9", overflow:"auto"}}>
                    {searchResult}
                </div>
            </div>
            <div style={{display:"flex", height:"50px"}}>
                <button style={{marginLeft:"1370px", marginTop:"30px"}} className={ModuleStyle.whiteSmallButton} onClick={() => setmodal(false)}>취소</button>
                <button style={{marginLeft:"10px", marginTop:"30px"}} className={ModuleStyle.blueSmallButton} onClick={() => {(isSearch) &&setSmallModal(true); (isSearch==false) && alert("출고 신청 내역이 존재하지 않습니다.")}}>출고 신청</button>
            </div>
        </Modal>}

        <div style={{display:"flex"}}>
            <p style={{marginTop:"25px", marginLeft:"25px", marginRight:"1200px", fontSize:"30px"}}>발주 목록</p>
            <button style={{marginTop:"20px", marginRight:"10px"}} className={ModuleStyle.blueSmallButton} onClick={() => {setmodal(true)}}>출고 신청</button>
        </div>
        {(loading) &&
        <div>
            <div style={{height:"590px"}}>
                <div style={{display:"flex"}}>
                    <div style={{width:"80px"}}/>
                    <div style={{width:"230px", textAlign:"left"}}>상품명</div>
                    <div style={{width:"170px", textAlign:"left"}}>공급가(원)</div>
                    <div style={{width:"170px", textAlign:"left"}}>공급처</div>
                    <div style={{width:"170px", textAlign:"left"}}>발주 신청 수량</div>
                    <div style={{width:"170px", textAlign:"left"}}>보유 수량</div>
                    <div style={{width:"170px", textAlign:"left"}}>발주 신청 날짜</div>
                    <div style={{width:"170px", textAlign:"left"}}>상태</div>
                    <div style={{width:"170px", textAlign:"left"}}>점포명</div>
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