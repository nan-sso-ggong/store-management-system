import React, { useEffect, useState } from "react";
import {useNavigate} from "react-router-dom";
import { useRecoilState, useRecoilValue } from 'recoil';
import api from "../Axios";
import {cartState, selectedStoreIdState, storeNameState, storeAddressState} from '../state';
import styled from 'styled-components';
import { FaRegCheckCircle } from "react-icons/fa";
import { FaRegTrashAlt } from "react-icons/fa";
import { FaMapLocationDot } from "react-icons/fa6";

const H2 = styled.h2`
  margin-left: 5%;
  margin-top:3%;
`;
const Container = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
`;
const LeftDiv = styled.div`
  width: 75%;
  margin-left:5%;
`;
const RightDiv = styled.div`
  display: flex;
  width: 35%;
  height: 65vh;
  margin-right: 5%;
  border: 2px solid lightgrey;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
`;
const SELECT = styled.div`
  display: flex;
  width: 753px;
  height:40px;
  button{
    margin-right:10px;
    cursor:pointer;
    height:40px;
    width:110px;
    font-size:15px;
    border-radius: 5px;
    border-color: white;
    background-color: #397CA8;
    color:white;
    &:hover{
      background-color: darkblue;
    }
    span{
      margin-left:5px;
    }
  }
`
const PRODUCT=styled.div`
  margin-top:20px;
`
const TableContainer = styled.div`
  height:420px;
  width:755px;
  overflow-y: auto;
  border-top: 2px solid lightgrey;
  border-bottom: 2px solid lightgrey;
  &::-webkit-scrollbar {
    width: 8px;
  }

  &::-webkit-scrollbar-thumb {
    background-color: lightgrey;
    border-radius: 4px;
  }

  &::-webkit-scrollbar-track {
    background-color: rgba(150,150,150,0.15);
  }
`;
const ITEM =styled.div`
  display:flex;
  flex-direction: row;
  align-items: center;
  border-bottom:2px solid lightgrey;
  height:90px;
  img{
    width:70px;
    height:70px;
    border:1px solid rgba(150,150,150,0.1);
  }
`
const Checkbox = styled.input.attrs({ type: 'checkbox' })`
  width: 18px;
  height: 18px;
`;
const A = styled.div`
  height:400px;
  width:740px;
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  font-size:18px;
  color: darkgray;
`
const RIGHTCONTENT = styled.div`
  margin-top:20px;
`
const STORE=styled.div`
  width: 372px;
  border-bottom:2px solid gray;
    span{
      font-size:20px;
    }
  div{
    margin-bottom:10px;
    margin-left:15px;
  }
`

const PURCHASEDETAIL = styled.div`
  padding-top:30px;
  padding-bottom:15px;
  width:372px;
  height:205px;
  background-color: #F5FBEF;
  div{
    margin-left:15px;
    margin-bottom:23px;
    font-size: 18px;
  }
  select{
    width:110px;
    height:30px;
  }
  input{
    width:85px;
    height:30px;
  }
  button{
    margin-left: 10px;
    height:35px;
    cursor: pointer;
    border-radius: 5px;
    border-color: white;
    background-color: #6B8F73;
    color:white;
    &:hover{
      background-color: darkgreen;
    }
  }
  
`
const PAY = styled.div`
  display: flex;
  align-items: center;
  justify-content: center; /* 가로 중앙 정렬 */
  font-size:28px;
  background-color: #6B8F73;
  color:white;
  &:hover{
    background-color: darkgreen;
  }
  cursor:pointer;
  box-shadow: rgba(0, 0, 0, 0.16) 0 1px 4px;
  width:372px;
  height:83px;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
`

function ShoppingCart() {
    const [cart, setCart] = useRecoilState(cartState);
    const [pointCheck, setPointCheck] = useState({});
    const [inputPoint, setInputPoint] = useState(0);
    const [usedPoint, setUsedPoint] = useState(0);
    const [paymentType, setPaymentType] = useState('card');
    const storeId = useRecoilValue(selectedStoreIdState);
    const storeName = useRecoilValue(storeNameState);
    const storeAddress = useRecoilValue(storeAddressState);
    const [selectedItems, setSelectedItems] = useState({});
    const [selectAll, setSelectAll] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        const savedCart = localStorage.getItem('cart');
        if (savedCart) {
            setCart(JSON.parse(savedCart));
        }
    }, []);

    useEffect(() => {
        localStorage.setItem('cart', JSON.stringify(cart));
    }, [cart]);

    useEffect(() => {
        const savedStoreId = localStorage.getItem('storeId');
        if (storeId !== savedStoreId) {
            setCart([]);
            localStorage.setItem('cart', JSON.stringify([]));
        }
    }, [storeId]);

    useEffect(() => {
        localStorage.setItem('storeId', storeId);
    }, [storeId]);

    useEffect(() => {
        if (selectAll) {
            const newSelectedItems = cart.reduce((acc, item) => {
                acc[item.id] = true;
                return acc;
            }, {});
            setSelectedItems(newSelectedItems);
        } else {
            setSelectedItems({});
        }
    }, [selectAll, cart]);
    const handleCheckboxChange = (id) => (e) => {
        setSelectedItems({ ...selectedItems, [id]: e.target.checked });
    };
    const handleDelete = () => {
        const newCart = cart.filter((item) => !selectedItems[item.id]);
        setCart(newCart);
        localStorage.setItem('cart', JSON.stringify(newCart));
        alert("선택항목이 삭제되었습니다.");
    };
    let totalAmount = cart.reduce((acc, item) => {
        return acc + item.quantity * item.price;
    }, 0);

    const getPoint = async () => {
        try {
            const resp = await api.get(`/customers/store/cart`);
            if(resp && resp.data && resp.data.data) {
                setPointCheck(resp.data.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };

    const checkPoint = () => {
        const inputPointAsInt = parseInt(inputPoint);
        if (isNaN(inputPointAsInt) || inputPointAsInt > pointCheck.point) {
            alert('포인트가 부족하거나 올바른 값이 아닙니다.');
        } else {
            setUsedPoint(inputPointAsInt);
        }
    };

    const handleInputChange = (e) => {
        const inputValue = e.target.value;
        if (inputValue >= 0) {
            setInputPoint(inputValue);
        } else {
            alert('포인트는 0 이상의 값을 입력해주세요.');
        }
    };

    const payment = () => {
        const storedCart = JSON.parse(localStorage.getItem('cart')) || [];

        const items = storedCart.map((item) => ({
            id: item.id,
            quantity: item.quantity,
        }));

        const data = {
            type: paymentType,
            point: usedPoint,
            totalPrice: totalAmount,
            itemName: storedCart.length > 0 ? storedCart[0].name : null,
            itemCount: storedCart.length,
            items: items,
        };

        api.post(`/customers/store/${storeId}/payment`, data)
            .then(() => {
                alert(`상품이 구매되었습니다.`);
                navigate(`/customer/checkpayment`);
                // localStorage의 'cart' 항목을 비운다.
                localStorage.setItem('cart', JSON.stringify([]));
                setCart([]);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    useEffect(() => {
        getPoint();
    }, []);

    return (
        <div>
            <div><H2>장바구니</H2></div>
            <Container>
                <LeftDiv>
                    <SELECT>
                        <button onClick={() => setSelectAll(!selectAll)}>
                            <FaRegCheckCircle />
                            <span>{selectAll ? '전체 해제' : '전체 선택'}</span>
                        </button>
                        <button onClick={handleDelete}>
                            <FaRegTrashAlt />
                            <span>선택 삭제</span>
                        </button>
                    </SELECT>
                    <PRODUCT>
                        <TableContainer>
                            <table>
                                {cart.length === 0 ? (
                                    <A><span>장바구니가 비었습니다.</span></A>
                                ) : (
                                    cart.map(item => (
                                        <tr>
                                            <td key={item.id}>
                                                <ITEM>
                                                    <div style={{marginLeft:20}}>
                                                        <Checkbox
                                                            checked={selectedItems[item.id] || false}
                                                            onChange={handleCheckboxChange(item.id)}
                                                        />
                                                    </div>
                                                    <div style={{ width: '80px',marginLeft:30}}><img src={item.thumbnail} alt="상품 사진" /></div>
                                                    <div style={{ width: '343px',textAlign: 'center' }}>{item.name}</div>
                                                    <div style={{ width: '140px' }}>{item.quantity}개</div>
                                                    <div style={{ width: '100px' }}>{item.price*item.quantity}원</div>
                                                </ITEM>
                                            </td>
                                        </tr>
                                    ))
                                )}
                            </table>
                        </TableContainer>
                    </PRODUCT>
                </LeftDiv>
                <RightDiv>
                    <RIGHTCONTENT>
                        <STORE>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <FaMapLocationDot size="2em" style={{ marginRight: "10px" }} />
                                <span>픽업장소</span>
                            </div>
                            <div><p style={{fontWeight:"bold"}}>CS25 {storeName}</p></div>
                            <div><p>{storeAddress}</p></div>
                        </STORE>
                        <PURCHASEDETAIL>
                            <div><span>총 상품금액 : {totalAmount}원</span></div>
                            <div>
                                <span>결제 방법 : </span>
                                <select
                                    name="payment"
                                    value={paymentType}
                                    onChange={e => setPaymentType(e.target.value)}>
                                    <option value="card">신용/체크카드</option>
                                    <option value="bank-transfer">무통장입금</option>
                                    <option value="account-transfer">계좌이체</option>
                                    <option value="phone">휴대폰결제</option>
                                </select>
                            </div>
                            <div>
                                <span>포인트 사용 : </span>
                                <input
                                    type="text"
                                    name="point"
                                    placeholder={`보유: ${pointCheck.point}P`}
                                    onChange={handleInputChange}
                                />
                                <button onClick={checkPoint}>사용</button>
                            </div>
                            <div>
                                {`총 결제금액 : ${totalAmount}원 - ${usedPoint}원 = ${totalAmount - usedPoint}원`}
                            </div>

                        </PURCHASEDETAIL>
                        <PAY onClick={payment}>주문 및 결제하기</PAY>
                    </RIGHTCONTENT>
                </RightDiv>
            </Container>
        </div>
    );
}

export default ShoppingCart;