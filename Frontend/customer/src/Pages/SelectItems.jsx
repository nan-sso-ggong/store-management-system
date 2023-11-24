import React,{useEffect, useState } from "react";
import api from "../Axios";
import styled from "styled-components";
import { useParams } from 'react-router-dom';
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
    margin-top:40px;
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
const BUTTON = styled.div`
  display: flex;
  justify-content: center;
  align-Items: center;
  button{
    border:0;
    background-color: white;
  }
  span{
    font-size:20px;
    color: black;
  }
`
function SelectItems(){
    const[product, setProduct] = useState([]);
    const [number, setNumber] = useState(1);
    const[selectedItem, setSelectedItem] = useState(null);
    const { storeId } = useParams();
    const getInfo = async() =>{
        try {
            const resp = await api.get(`/customer/search-store/${storeId}`);
            if(resp && resp.data.data.datalist) {
                setProduct(resp.data.data.datalist);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    }

    const selectItem = (item_id) => {
        setSelectedItem(item_id);
    }

    const onIncrease = () => {
        setNumber(number + 1);
    }

    const onDecrease = () => {
        setNumber(number - 1);
    }

    useEffect(() => {
        getInfo();
    }, []);

    return(
        <div>
        <div>
        <H2>상품 목록</H2>
        </div>
        <Container>
        <LeftDiv>
            <SEARCH>
                <select name="category">
                    <option value="whole">전체</option>
                    <option value="icecream">아이스크림</option>
                    <option value="snack">과자</option>
                    <option value="ramen">라면</option>
                    <option value="drink">음료</option>
                </select>
                <input type="text" name="search" placeholder="상품명을 입력해주세요" />
                <button>
                    <div><IoIosSearch /> 검색</div>
                </button>
            </SEARCH>
            <PRODUCT>
                <div>
                    <table>
                        {product && [...Array(Math.ceil(product.length / 2))].map((_, rowIndex) => (
                            <tr key={rowIndex}>
                                {[...Array(2)].map((_, colIndex) => {
                                    const item = product[rowIndex * 2 + colIndex];
                                    return item ? (
                                        <td key={item.item_id} onClick={() => selectItem(item.item_id)}>
                                            <ITEMS
                                                style={{
                                                    cursor: 'pointer',
                                                    backgroundColor: selectedItem === item.item_id ? '#F5FCFF' : '#fff',
                                                }}>
                                                <DIV><img src={item.item_thumbnail} alt="물품사진"/></DIV>
                                                <DIV2>{item.item_name}</DIV2>
                                                <DIV>{item.item_price}원</DIV>
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
        </LeftDiv>
        <RightDiv>
            <div>
                {product.find(item => item.item_id === selectedItem) ? (
                    <INFO>
                        <div><img src={product.find(item => item.item_id === selectedItem).item_thumbnail} alt="상품사진"/></div>
                        <div><p>상품명: {product.find(item => item.item_id === selectedItem).item_name}</p></div>
                        <div><p>가격: {product.find(item => item.item_id === selectedItem).item_price}원</p></div>
                        <BUTTON>
                         <p>상품수량: </p>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <button onClick={onDecrease}><FiMinusCircle size={25}/></button>
                                <span style={{ margin: '0 10px' }}>{number}</span>
                                <button onClick={onIncrease}><FiPlusCircle size={25}/></button>
                            </div>
                        </BUTTON>
                    </INFO>
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