import React,{useEffect, useState } from "react";
import api from "../Axios";
import styled from "styled-components";
import { useRecoilState } from 'recoil';
import { selectedStoreIdState,cartState} from '../state';
import {IoIosSearch} from "react-icons/io";
import { FiMinusCircle } from "react-icons/fi";
import { FiPlusCircle } from "react-icons/fi";

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
  justify-content: center; /* 가로 중앙 정렬 */
  width: 35%;
  height: 65vh;
  margin-right: 5%;
  border: 2px solid lightgrey;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  p{
    font-size: 18px;
  }
  img{
    width:200px;
    height:200px;
    border: 1px solid black;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border: 1px solid rgba(150,150,150,0.1);
  }
`;
const INFO = styled.div`
    margin-top:35px;
  height:360px;
  button{
    border:0;
    background-color: white;
    cursor: pointer;
  }
`
const ITEM = styled.div`
margin-left:90px;
  font-size:18px;
  div{
    margin-top:15px;
  }
`
const WORD = styled.div`
    margin-top:220px;
    font-size: 18px;
    color: rgba(150,150,150,1);
`
const SEARCH = styled.div`
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  width: 750px;
  border: 2px solid lightgrey;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  input{
    width:400px;
    height:35px;
    font-size:14px;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border: 1px solid rgba(150,150,150,0.1);
  }
  button{
    cursor:pointer;
    height:40px;
    width:100px;
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
  select{
    height: 40px;
    width: 100px;
    border-radius: 5px;
    font-size: 15px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border: 1px solid rgba(150,150,150,0.1);
  }
  & > * {
    margin: 20px;
  }
`
const PRODUCT=styled.div`
  margin-top:20px;
  img{
    width:70px;
    height:70px;
    border:1px solid rgba(150,150,150,0.1);
  }
  table{
    height:380px;
    width:755px;
    border-top:2px solid lightgrey;
    border-bottom:2px solid lightgrey;
  }
`
const ITEMS = styled.div`
  display: flex;
  align-items: center;
  justify-content: center; /* 가로 중앙 정렬 */
  margin-left:20px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  border: 1px solid rgba(150,150,150,0.1);
`
const DIV = styled.div`
width:80px;
  text-align: center;
`
const DIV2 = styled.div`
width:180px;
  text-align: center;
`
const CART = styled.div`
  display: flex;
  justify-content: center;
  align-Items: center;
  font-size:25px;
  background-color: #6B8F73;
  color:white;
  &:hover{
    background-color: darkgreen;
  }
  cursor:pointer;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  width:380px;
  height:86px;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
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
function SelectItems(){
    const[product, setProduct] = useState({datalist:[],pageInfo:{}});
    const [number, setNumber] = useState(1);
    const[selectedItem, setSelectedItem] = useState(null);
    const [storeId, setStoreId] = useRecoilState(selectedStoreIdState);
    const [cart, setCart] = useRecoilState(cartState);
    const [currentPage, setCurrentPage] = useState(0);
    const [search, setSearch] = useState({
        category: '',
        name: '',
        page: 0,
        size: 8
    });
    const getInfo = async(page=0) =>{
        try {
            const queryString = Object.entries(search)
                .map((e) => e.join('='))
                .join('&');
            const resp = await api.get(`/customers/store/${storeId}?`+queryString);
            if(resp && resp.data && resp.data.data && resp.data.data.datalist) {
                setProduct(resp.data.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    }
    const selectItem = (id) => {
        setSelectedItem(id);
        setNumber(1);
    }
    const onIncrease = () => {
        setNumber(number + 1);
    }
    const onDecrease = () => {
        if (number > 1) {
            setNumber(number - 1);
        }
    }
    const addToCart = () => {
        const selectedItemInfo = product.datalist.find(item => item.id === selectedItem);
        const existingItem = cart.find(item => item.id === selectedItem);
        alert("상품이 장바구니에 담겼습니다.");

        let updatedCart;
        if (existingItem) {
            updatedCart = cart.map(item =>
                item.id === selectedItem
                    ? { ...item, quantity: item.quantity + number }
                    : item
            );
        } else {
            const itemToAdd = {
                id: selectedItem,
                quantity: number,
                thumbnail: selectedItemInfo.thumbnail,
                name: selectedItemInfo.name,
                price: selectedItemInfo.price,
            };
            updatedCart = [...cart, itemToAdd];
        }

        setCart(updatedCart);
        localStorage.setItem('cart', JSON.stringify(updatedCart));
    };

    const moveToPage = (page) => {
        setCurrentPage(page);
    };
    const onChange = (event) => {
        const { value, name } = event.target;
        setSearch({
            ...search,
            [name]: value,
        });
    };

    const onSearch = () => {
        if (search.category !== '' && search.name !== '') {
            setSearch({
                ...search,
                page: 0,
            });
            setCurrentPage(0);
            getInfo();
        }
    };

    useEffect(() => {
        const localStoreId = localStorage.getItem('storeId');

        if(localStoreId) {
            setStoreId(localStoreId);
        }
        getInfo(currentPage);
    }, [currentPage,search]);

    return(
        <div>
        <div>
        <H2>상품 목록</H2>
        </div>
        <Container>
        <LeftDiv>
            <SEARCH>
                <select name="category" onChange={onChange}>
                    <option value="전체">전체</option>
                    <option value="아이스크림">아이스크림</option>
                    <option value="과자">과자</option>
                    <option value="라면">라면</option>
                    <option value="음료">음료</option>
                </select>
                <input type="text" name="name" id="" onChange={onChange} placeholder="상품명을 입력해주세요" />
                <button onClick={onSearch}>
                    <div><IoIosSearch /> 검색</div>
                </button>
            </SEARCH>
            <PRODUCT>
                <div>
                    <table>
                        {product.datalist && [...Array(Math.ceil(product.datalist.length / 2))].map((_, rowIndex) => (
                            <tr key={rowIndex}>
                                {[...Array(2)].map((_, colIndex) => {
                                    const item = product.datalist[rowIndex * 2 + colIndex];
                                    return item ? (
                                        <td key={item.id} onClick={() => selectItem(item.id)}>
                                            <ITEMS
                                                style={{
                                                    cursor: 'pointer',
                                                    backgroundColor: selectedItem === item.id ? '#F5FCFF' : '#fff',
                                                }}>
                                                <DIV><img src={item.thumbnail} alt="물품사진"/></DIV>
                                                <DIV2>{item.name}</DIV2>
                                                <DIV>{item.price}원</DIV>
                                            </ITEMS>
                                        </td>
                                    ) : (
                                        <td key={`empty-${rowIndex}-${colIndex}`} />
                                    );
                                })}
                            </tr>
                        ))}
                    </table>
                </div>
            </PRODUCT>
            <PAGEBUTTON>
                {[...Array(product.pageInfo.totalPages)].map((_, index) => (
                    <button onClick={() => moveToPage(index)}
                            style={{
                                color: currentPage === index ? 'darkblue' : 'black',
                                fontWeight: currentPage === index ? 'bold' : 'normal', // 현재 페이지이면 굵게
                                textDecoration: currentPage === index ? 'underline' : 'none' // 현재 페이지이면 밑줄
                            }}
                    >{index + 1}</button>
                ))}
            </PAGEBUTTON>
        </LeftDiv>
        <RightDiv>
            <div>
                {product.datalist.find(item => item.id === selectedItem) ? (
                    <>
                    <INFO>
                        <ITEM>
                        <div><img src={product.datalist.find(item => item.id === selectedItem).thumbnail} alt="상품사진"/></div>
                        <div>상품명: {product.datalist.find(item => item.id === selectedItem).name}</div>
                        <div>가격: {product.datalist.find(item => item.id === selectedItem).price}원</div>
                            <div>
                                상품수량:
                                <button
                                    onClick={onDecrease}
                                    style={{ color: number > 1 ? 'initial' : 'lightgrey' }}
                                >
                                    <FiMinusCircle size={18}/>
                                </button>
                                {number}
                                <button onClick={onIncrease}><FiPlusCircle size={18}/></button>
                            </div>
                        </ITEM>
                    </INFO>
                    <CART onClick={addToCart}>장바구니 담기</CART>
                    </>
                    ) : (
                    <WORD>상품을 선택해주세요</WORD>
                )}
            </div>
        </RightDiv>
        </Container>
        </div>
    )
}

export default SelectItems;