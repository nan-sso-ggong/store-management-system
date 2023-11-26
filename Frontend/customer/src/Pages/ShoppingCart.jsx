import React, { useEffect, useState } from "react";
import { useRecoilState, useRecoilValue } from 'recoil';
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
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
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
  margin-left:30px;
`
const STORE=styled.div`

`

function ShoppingCart() {
    const [cart, setCart] = useRecoilState(cartState);
    const storeId = useRecoilValue(selectedStoreIdState);
    const storeName = useRecoilValue(storeNameState);
    const storeAddress = useRecoilValue(storeAddressState);
    const [selectedItems, setSelectedItems] = useState({});
    const [selectAll, setSelectAll] = useState(false);

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
                            <div><FaMapLocationDot /><span>픽업장소</span></div>
                            <div><span>CS25 {storeName}</span></div>
                            <div><span>{storeAddress}</span></div>
                        </STORE>
                        <div>
                            <div><span>총 상품금액:{totalAmount}</span></div>
                            <div><span>결제수단:</span></div>
                        </div>
                    </RIGHTCONTENT>
                </RightDiv>
            </Container>
        </div>
    );
}

export default ShoppingCart;