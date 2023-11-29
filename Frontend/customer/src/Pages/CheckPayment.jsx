import React from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import styled from "styled-components";
import {selectedStoreIdState} from '../state';

const CONTAINER = styled.div`
  margin-left:70px;
  margin-top:50px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
  border: 2px solid rgba(150,150,150,0.1);
  width: 1150px;
  height: 550px;
  `
const CONTENT = styled.div`
  margin-top:30px;
`
const NAME = styled.div`
  width:1050px;
  height:80px;
  border-bottom: 3px solid rgba(150,150,150,0.5);
  margin-left:40px;
`
const INFO = styled.div`
  margin-left:40px;
  border-bottom: 3px solid rgba(150,150,150,0.5);
  div{
    margin-top:25px;
    margin-left:20px;
  }
  width:1050px;
  height:240px;
`
const PAY = styled.div`
  margin-left:60px;
  width:1050px;
  height:90px;
  div{
    margin-top:15px;
  }
`
const Button = styled.div`
  display: flex;
  justify-content: flex-end;
  button{
    cursor: pointer;
    width:80px;
    height:40px;
    font-size:14px;
    background-color: #397CA8;
    border-radius: 5px;
    border-color: white;
    color:white;
    &:hover{
      background-color: darkblue;
    }
`
function CheckPayment() {
    const location = useLocation();
    const Data = location.state.data;
    const { id, orderNumber, itemName, totalPrice, savedPoint, usedPoint, orderDate, paymentType, storeName, storeAddress } = Data;
    const navigate = useNavigate();
    const storeId = selectedStoreIdState;
    const moveToItems = () => {
            navigate(`/customer/${storeId}/selectitems`);
    };
    const moveToList = () => {
        navigate(`/customer/paymentlist`);
    };

    return(
        <>
            <div>
                <CONTAINER>
                    <CONTENT>
                        <NAME>
                            <span style={{color:'#7D3838',fontSize:"50px"}}>결제가 완료</span>
                            <span style={{fontSize:"50px"}}>되었습니다</span>
                        </NAME>
                        <INFO>
                            <div><span style={{marginRight:"43px"}}>상품명</span><span>{itemName}외 3개</span></div>
                            <div><span style={{marginRight:"30px"}}>픽업장소</span><span>CS25 {storeName}</span><span style={{marginLeft:"20px"}}>({storeAddress})</span></div>
                            <div><span style={{marginRight:"30px"}}>주문번호</span><span>{orderNumber}</span></div>
                            <div><span style={{marginRight:"30px"}}>주문일자</span><span>{orderDate}</span></div>
                            <div><span style={{marginRight:"30px"}}>결제수단</span><span>{paymentType}</span></div>
                        </INFO>
                        <PAY>
                            <div><span style={{marginRight:"20px"}}>총 주문 금액 {totalPrice}원 - 포인트 사용 {usedPoint}원</span>/<span style={{marginLeft:"20px"}}> 적립된 포인트 {savedPoint}원</span></div>
                            <div>
                                <span style={{marginRight:"20px",fontSize:"26px",fontWeight:"bold"}}>총 결제 금액</span>
                                <span style={{fontSize:"26px",fontWeight:"bold"}}>{totalPrice-usedPoint}원</span>
                            </div>
                        </PAY>
                        <Button>
                            <button onClick={moveToItems}>추가구매</button>
                            <button style={{marginLeft:"15px",marginRight:"55px"}} onClick={moveToList}>구매내역</button>
                        </Button>
                    </CONTENT>
                </CONTAINER>
            </div>
        </>
    )
}

export default CheckPayment;

