import React, {useEffect, useState} from "react";
import { useNavigate,useParams } from 'react-router-dom';
import styled from "styled-components";
import api from "../Axios";

const Receipt = styled.div`
  width: 900px;
  height: 565px;
  border: 2px solid rgba(150,150,150,0.1);;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
`
const Content = styled.div`
  display:flex;
  justify-content: center;
  margin-top: 45px;
`
const Title = styled.div`
  text-align: center;
  margin-top: 30px;
  font-size: 25px;
  margin-bottom: 20px;
`
const Text = styled.div`
width:800px;
  margin-left: 50px;
`
const CONTENTS = styled.div`
  margin-left: 15px;
  width: 800px;
  height: 267px;
  div {
    margin-top: 15px;
  }
  span{
    margin-right:60px;
  }
`
const PAY = styled.div`
  margin-left:16px;
  margin-top:15px;
  font-size: 17px;
  div{
    display: flex;
    justify-content: flex-end;
    font-size: 20px;
  }
`
const Button = styled.div`
  margin-top:60px;
  display:flex;
  justify-content: center;
  button{
    margin-left: 10px;
    cursor: pointer;
    width:80px;
    height:40px;
    font-size:15px;
    background-color: #397CA8;
    border-radius: 5px;
    border-color: white;
    color:white;
    &:hover{
      background-color: darkblue;
    }
  }
`
function PaymentListDetail(){
    const [content,setContent]=useState({});
    const navigate = useNavigate();
    const {id} = useParams();
    const getContent = async () => {
        try {
            const resp = await api.get(`/customers/history/${id}`);
            if(resp && resp.data && resp.data.data) {
                setContent(resp.data.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };

    const moveToList =()=>{
        navigate(`/customer/paymentlist`);
    }
    const refund = async()=>{
        try {
            await api.post(`/customers/refund/${id}`);
            alert('환불되었습니다.');
            navigate(`/customer/paymentlist`);
        } catch (error) {
            console.error('Error updating the board: ', error);
        }
    }

    useEffect(() => {
        getContent();
    }, [id]);
    return(<>
            <Content>
            <Receipt>
                <Title><span>구매 내역 조회</span></Title>
                    <Text>
                        <div style={{width:"800px", height:"36px",borderBottom:"3px solid lightgrey"}}>
                            <span style={{marginLeft:"8px",fontSize:"18px"}}>구매정보</span>
                        </div>
                        <CONTENTS>
                            <div><span style={{marginRight:"74px"}}>상품명</span><span>{content.itemName}</span></div>
                            <div><span>픽업장소</span><span style={{marginRight:"20px"}}>{content.storeName}</span><span>({content.storeAddress})</span></div>
                            <div><span>주문번호</span><span>{content.orderNumber}</span></div>
                            <div><span>주문일자</span><span>{content.orderDate}</span></div>
                            <div><span>결제수단</span><span>{content.paymentType}</span></div>
                            <div><span style={{marginRight:"44px"}}>적립 포인트</span><span>{content.savedPoint} P</span></div>
                            <div><span>주문상태</span><span style={{fontWeight:"bold"}}>{content.orderStatus}</span></div>
                        </CONTENTS>
                        <div style={{width:"800px", height:"36px",borderTop:"3px solid lightgrey"}}>
                            <PAY>
                                <span style={{marginRight:"30px"}}>결제정보</span>
                                <span style={{marginRight:"5px"}}>총 결제금액 {content.totalPrice} 원</span> +
                                <span style={{marginLeft:"6px"}}>포인트 사용 {content.usedPoint} P</span>
                                <div>
                                <span style={{marginRight:"10px"}}>총 주문금액</span><span>{content.totalPrice+content.usedPoint}원</span>
                                </div>
                            </PAY>
                        </div>
                        <Button>
                            <button onClick={refund}>환불하기</button>
                            <button onClick={moveToList}>돌아가기</button>
                        </Button>
                    </Text>
            </Receipt>
            </Content>
        </>
    )
}

export default PaymentListDetail;