import React,{useState,useEffect} from "react";
import { useNavigate } from 'react-router-dom';
import styled from "styled-components";
import api from "../Axios";

const H2 = styled.h2`
  margin-left: 5%;
  margin-top:3%;
`;
const LIST = styled.div`
margin-left:70px;
`
const Table =styled.table`
  width:1140px;
  height:480px;
  border-top: 3px solid black;
  border-collapse: collapse;
  th{
    border-bottom: 1px solid lightgrey;
    height:50px;
  }
`
const Tr = styled.tr`
  :hover {
    cursor: pointer;
  }
`;

const Td = styled.td`
  text-align: center;
  border-bottom: 1px solid lightgrey;
  ${Tr}:hover & {
    background-color: #F5FBEF;
    font-weight: bold;
  }
`;

function PaymentList(){
    const [listItem,setListItem]= useState(null);
    const navigate = useNavigate();
    const getItem = async () => {
        try {
            const resp = await api.get(`/customers/history`);
            if(resp && resp.data && resp.data.data && resp.data.data.datalist) {
                setListItem(resp.data.data.datalist);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };
    useEffect(() => {
        getItem();
    }, []);
    return(<>
        <div>
        <H2>구매내역 조회</H2>
        <LIST>
            <Table>
                <tbody>
                <tr>
                    <th style={{width:"230px"}}>상품명</th>
                    <th style={{width:"230px"}}>주문일자</th>
                    <th style={{width:"230px"}}>주문번호</th>
                    <th style={{width:"150px"}}>주문금액</th>
                    <th style={{width:"150px"}}>점포명</th>
                    <th style={{width:"150px"}}>주문상태</th>
                </tr>
                {listItem && listItem.map((list)=> (
                    <Tr key={list.id} onClick={() => navigate(`/customer/paymentlist/${list.id}`)}>
                        <Td>{list.itemName}</Td>
                        <Td>{list.orderDate}</Td>
                        <Td>{list.orderNumber}</Td>
                        <Td>{list.totalPrice}</Td>
                        <Td>{list.storeName}</Td>
                        <Td>{list.orderStatus}</Td>
                    </Tr>
                ))}
                </tbody>
            </Table>
        </LIST>
        </div>
    </>)
}

export default PaymentList;