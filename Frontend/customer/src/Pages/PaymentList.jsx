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
const PAGEBUTTON = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
  button {
    border: none;
    font-size: 15px;
    margin-right: 5px;
    background-color: white;
    color: ${props => props.current ? 'red' : 'black'};
    &:hover {
      font-weight:bold;
      cursor: pointer;
    }
  }
`;

function PaymentList(){
    const [listItem,setListItem]= useState({data:{datalist:[],pageInfo:{}}});
    const navigate = useNavigate();
    const [currentPage, setCurrentPage] = useState(0);

    const getItem = async (page=0) => {
        try {
            const resp = await api.get(`/customers/history?page=${page}&size=6`);
            if(resp && resp.data && resp.data.data && resp.data.data.datalist) {
                setListItem(resp.data.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };
    const moveToPage = (page) => {
        setCurrentPage(page);
    };

    useEffect(() => {
        getItem(currentPage);
    }, [currentPage]);
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
                {listItem.data.datalist.map((list)=> (
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
            <PAGEBUTTON>
                {[...Array(listItem.data.pageInfo.totalPages)].map((_, index) => (
                    <button onClick={() => moveToPage(index)}
                            style={{
                                color: currentPage === index ? 'darkblue' : 'black',
                                fontWeight: currentPage === index ? 'bold' : 'normal', // 현재 페이지이면 굵게
                                textDecoration: currentPage === index ? 'underline' : 'none' // 현재 페이지이면 밑줄
                            }}
                    >{index + 1}</button>
                ))}
            </PAGEBUTTON>
        </LIST>
        </div>
    </>)
}

export default PaymentList;