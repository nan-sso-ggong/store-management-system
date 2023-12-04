import React,{useEffect, useState } from "react";
import api from "../Axios"
import styled from "styled-components";
import { useSelector } from 'react-redux';

const H2 = styled.h2`
  margin-left: 5%;
  margin-top:3%;
`;
const TopComponent = styled.div`
    margin-left:5%;
  margin-top:20px;
  border: 2px solid lightgrey;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
  width:1140px;
  height: 100px;
  input{
    font-size:14px;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border: 1px solid rgba(150,150,150,0.5);
  }
  button{
    cursor:pointer;
    height:40px;
    width:80px;
    border-radius: 5px;
    border-color: white;
    font-size:14px;
    background-color: #397CA8;
    color:white;
    cursor:pointer;
    &:hover{
      background-color: darkblue;
    }
  }
`
const BottomComponent = styled.div`
  width:1140px;
  height:390px;
  margin-left:5%;
  margin-top:1.5%;
  border: 2px solid lightgrey;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
`
const OrderStatus = styled.div`
  display: flex;
  align-items: center;
  margin-top:15px;
  margin-left:15px;
  form{
    margin-left:20px;
    label{
      margin-left:20px;
    }
  }
`
const OrderDate = styled.div`
  display: flex;
  align-items: center;
  margin-top:15px;
  margin-left:15px;
  input{
    width:180px;
    height:30px;
  }
`
const Table =styled.table`
  width:1140px;
  max-height:500px;
  border-top: 3px solid black;
  border-collapse: collapse;
  th{
    border-bottom: 1px solid lightgrey;
    height:35px;
  }
`
const Checkbox = styled.input.attrs({ type: 'checkbox' })`
  width: 18px;
  height: 18px;
  &:disabled {
    background-color: darkgray;
  }
`;

const Tr = styled.tr`
  height:50px;
  :hover {
    cursor: pointer;
  }
`;
const Td = styled.td`
  text-align: center;
  border-bottom: 1px solid lightgrey;
  ${Tr}:hover & {
    background-color: #EFF8FB;
    font-weight: bold;
  }
`;

const Topitem = styled.div`
  margin-left:20px; 
  display: flex;
  justify-content: space-between;
  align-items: center; /* 세로 중앙 정렬 */
  width: 1100px; 
  height:50px;
  button{
    cursor:pointer;
    height:40px;
    width:80px;
    border-radius: 5px;
    border-color: white;
    font-size:14px;
    background-color: #397CA8;
    color:white;
    cursor:pointer;
    &:hover{
      background-color: darkblue;
    }
  }
`
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
    const [listItem,setListItem]= useState({datalist:[],pageInfo:{}});
    const [selectedOption, setSelectedOption] = useState('전체'); //주문상태 관리
    const [currentPage, setCurrentPage] = useState(0);
    const [selectedItems, setSelectedItems] = useState({});
    const { users } = useSelector((state) => state);
    const [store]= useState(users.store_id);
    const [search, setSearch] = useState({
        order_state: '',
        start_date: '',
        end_date: '',
        page: 0,
        size: 6
    });
    console.log(store);
    const getItem = async (page=0) => {
        try {
            const queryString = Object.entries(search)
                .map((e) => e.join('='))
                .join('&');
            const resp = await api.get(`managers/store/${store}/customer_orders?`+queryString);
            if(resp && resp.data && resp.data.data && resp.data.data.datalist) {
                setListItem(resp.data.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };
    //픽업 완료 버튼 로직
    const handlePickupComplete = async () => {
        // 선택된 주문의 주문번호를 배열로 만듭니다.
        const selectedOrderIds = Object.keys(selectedItems)
            .filter((orderId) => selectedItems[orderId])
            .map((orderId) => ({ "customer_order_id": Number(orderId) }));

        try {
            await api.patch(`/managers/store/${store}/customer_orders`, selectedOrderIds);
            alert("픽업완료 되었습니다.")
            // 요청이 성공하면, 선택된 항목을 초기화하고 데이터를 다시 불러옵니다.
            setSelectedItems({});
            getItem(currentPage);
        } catch (error) {
            console.error('Error updating data: ', error);
        }
    };
    const handleChange = (event) => {
        setSelectedOption(event.target.value);
    }
    const moveToPage = (page) => {
        setCurrentPage(page);
        setSearch({
            ...search,
            page: page,
        });
    };

    const handleCheckboxChange = (id) => (e) => {
        setSelectedItems({ ...selectedItems, [id]: e.target.checked });
    };

    useEffect(() => {
        getItem(currentPage);
    }, [currentPage,search]);

    return(
        <div>
            <div>
                <H2>주문내역</H2>
            </div>
            <TopComponent> {/*상단 주문상태 주문일자*/}
                <OrderStatus>
                    <div>주문상태</div>
                    <div>
                        <form>
                            <label>
                                <input type="radio" value="전체"
                                       checked={selectedOption === '전체'}
                                       onChange={handleChange} />
                                전체
                            </label>
                            <label>
                                <input type="radio" value="픽업대기"
                                       checked={selectedOption === '픽업대기'}
                                       onChange={handleChange} />
                                픽업대기
                            </label>
                            <label>
                                <input type="radio" value="픽업완료"
                                       checked={selectedOption === '픽업완료'}
                                       onChange={handleChange} />
                                픽업완료
                            </label>
                            <label>
                                <input type="radio" value="환불"
                                       checked={selectedOption === '환불'}
                                       onChange={handleChange} />
                                환불
                            </label>
                        </form>
                    </div>
                </OrderStatus>
                <OrderDate>
                    <div>주문일자</div>
                    <div style={{marginLeft:"45px",marginRight:"20px"}}>
                        <input type="text" placeholder="시작날짜 ex) 2023-12-01"/> ~ <input type="text" placeholder="끝 날짜 ex) 2023-12-01"/>
                    </div>
                    <div>
                        <button>검색</button>
                    </div>
                </OrderDate>
            </TopComponent>
            <BottomComponent> {/*하단 주문내역조회*/}
                <div>
                    <Topitem>
                        <span style={{fontSize:"20px"}}>주문 내역 조회</span>
                        <button onClick={handlePickupComplete}>픽업 완료</button>
                    </Topitem>
                    <div>
                        <Table>
                            <tbody>
                            <tr>
                                <th style={{width:"50px"}}>선택</th>
                                <th style={{width:"120px"}}>주문자</th>
                                <th style={{width:"200px"}}>주문번호</th>
                                <th style={{width:"230px"}}>주문상품</th>
                                <th style={{width:"150px"}}>주문합계</th>
                                <th style={{width:"230px"}}>주문일자</th>
                                <th style={{width:"150px"}}>주문상태</th>
                            </tr>
                            {listItem.datalist.map((list)=> {
                                const items = list.item_cu_fielditem_name[0].split(",");
                                const firstItem = items[0];
                                const itemCount = items.length;
                                const date = new Date(list.order_created_at);
                                const year = date.getFullYear();
                                const month = ("0" + (date.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더해줍니다.
                                const day = ("0" + date.getDate()).slice(-2);
                                const hours = ("0" + date.getHours()).slice(-2);
                                const minutes = ("0" + date.getMinutes()).slice(-2);
                                const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;
                                return(
                                <Tr key={list.order_id}>
                                    <Td>
                                        <Checkbox
                                        checked={selectedItems[list.order_id] || false}
                                        onChange={handleCheckboxChange(list.order_id)}
                                        disabled={list.order_state !== "픽업대기"}
                                        />
                                    </Td>
                                    <Td>{list.customer_name}</Td>
                                    <Td>{list.order_id}</Td>
                                    <Td>{`${firstItem} 외 ${itemCount - 1}개`}</Td>
                                    <Td>{list.payment_total_price}</Td>
                                    <Td>{formattedDate}</Td>
                                    <Td style={{
                                        color: list.order_state === "환불처리" ? "red" :
                                            list.order_state === "픽업대기" ? "green" :
                                                list.order_state === "픽업완료" ? "blue" : "initial"
                                    }}>
                                        {list.order_state}
                                    </Td>
                                </Tr>
                                )
                                })}
                            </tbody>
                        </Table>
                    </div>
                </div>
            </BottomComponent>
            <PAGEBUTTON>
                {[...Array(listItem.pageInfo.totalPages)].map((_, index) => (
                    <button onClick={() => moveToPage(index)}
                            style={{
                                color: currentPage === index ? 'darkblue' : 'black',
                                fontWeight: currentPage === index ? 'bold' : 'normal', // 현재 페이지이면 굵게
                                textDecoration: currentPage === index ? 'underline' : 'none' // 현재 페이지이면 밑줄
                            }}
                    >{index + 1}</button>
                ))}
            </PAGEBUTTON>
        </div>
    )
}

export default PaymentList;