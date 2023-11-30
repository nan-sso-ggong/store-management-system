import React, {useEffect, useState} from "react";
import { useNavigate,useParams } from 'react-router-dom';
import styled from "styled-components";
import api from "../Axios";

const Receipt = styled.div`
  width: 900px;
  height: 500px;
  border: 2px solid lightgrey;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
`
const Content = styled.div`
  display:flex;
  justify-content: center;
  align-items: center;
`
const Title = styled.div`
  text-align: center;
  margin-top: 30px;
  font-size: 23px;
  margin-bottom: 20px;
`
const Text = styled.div`
width:800px;
  margin-left: 50px;
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
    useEffect(() => {
        getContent();
    }, [id]);
    return(<>
            <Content>
            <Receipt>
                <Title><span>영수증</span></Title>
                <>
                    <Text>
                <div style={{width:"800px", height:"30px",borderBottom:"3px solid lightgrey"}}>
                    <span style={{marginLeft:"8px"}}>구매정보</span>
                </div>
                <div style={{marginLeft:"15px"}}>
                    <div><span style={{marginRight:"20px"}}>상품명</span><span>{content.itemName}</span></div>
                    <div><span style={{marginRight:"20px"}}>픽업장소</span><span>{content.storeName}</span><span>({content.storeAddress})</span></div>
                    <div><span style={{marginRight:"20px"}}>주문번호</span><span>{content.orderNumber}</span></div>
                    <div><span style={{marginRight:"20px"}}>주문일자</span><span>{content.orderDate}</span></div>
                    <div><span style={{marginRight:"20px"}}>결제수단</span><span>{content.paymentType}</span></div>
                    <div><span style={{marginRight:"20px"}}>주문상태</span><span>{content.orderStatus}</span></div>
                    <div><span style={{marginRight:"20px"}}>적립된 포인트</span><span>{content.savedPoint}</span></div>
                </div>
                <div>
                    <div><span>결제정보</span><span>총 결제금액 {content.totalPrice}</span>+<span>포인트 사용 {content.usedPoint}</span></div>
                    <div><span>총 주문금액</span><span>{content.totalPrice+content.usedPoint}</span></div>
                </div>
                    </Text>
                </>
            </Receipt>
            </Content>
        </>
    )
}

export default PaymentListDetail;