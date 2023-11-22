import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import api from '../Axios';
import styled from "styled-components";
import { IoIosSearch } from "react-icons/io";

const H2 = styled.h2`
  margin-left: 5%;
  margin-top:3%;
`;
const SEARCH = styled.div`
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  width: 1000px;
  border: 2px solid lightgrey;
  span{
    font-size:18px;
  }
  input{
    width:500px;
    height:50px;
    font-size:18px;
  }
  button{
    cursor:pointer;
    height:55px;
    width:80px;
    font-size:16px;
    background-color: #397CA8;
    color:white;
    &:hover{
      background-color: darkblue;
    }
  }
  & > * {
    margin: 20px;
  }
`
const CONTAINER = styled.div`
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  margin-top:50px;
`
const LIST = styled.div`
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  margin-top:40px;
`
const RESULT =styled.div`
  width: 1000px;
  border-top: 2px solid black;
  table {
    width:1000px;
    text-align: center;
    border-collapse: collapse;
    tr{
      height:50px;
      border-bottom: 1px solid lightgrey;
      &:first-child{
        background-color: #FBFBFB;
      }
    }
    td:nth-child(1) {
      width:300px;
    }
    td:nth-child(2) {
      width:700px;
    }
  }
`
const BUTTON = styled.div`
  display: flex;
  justify-content: center;
  margin-top:30px;
  button{
    cursor: pointer;
    width:80px;
    height:40px;
    font-size:15px;
    background-color: #397CA8;
    color:white;
    &:hover{
      background-color: darkblue;
    }
  }
`

function SelectStore(){
    const navigate = useNavigate();
    const [search, setSearch] = useState([]); // 상태 추가
    const [storeName, setStoreName] = useState('');
    const [selectedStoreId, setSelectedStoreId] = useState(null);

    const getSearch = async (page = 0) => { // page 파라미터 추가
        try {
            const resp = await api.get(`/customer/search-store?store_name=${encodeURIComponent(storeName)}`);
            if(resp && resp.data) {
                setSearch(resp.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };

    const moveToItems = () => {
        navigate(`/customer/:storeId/selectitems`);
    };

    const selectStore = (storeId) => {
        setSelectedStoreId(storeId);
    };

    return(
        <div>
            <H2>점포선택</H2>
            <CONTAINER>
                <SEARCH>
                    <span>매장명 검색</span>
                    <input type="text" name="search" value={storeName} onChange={(e) => setStoreName(e.target.value)} placeholder="매장명을 입력해주세요" />
                    <button onClick={getSearch}>
                        <div><IoIosSearch /></div>
                        <div>검색</div>
                    </button>
                </SEARCH>
            </CONTAINER>
            <LIST>
                <RESULT>
                    <table>
                        <tbody>
                        <tr>
                            <td>매장명</td>
                            <td>주소</td>
                        </tr>
                        {search&&search.map((store)=> (
                            <tr key={store.id}  onClick={() => selectStore(store.id)}
                                style={{
                                    cursor: 'pointer',
                                    backgroundColor: selectedStoreId === store.id ? '#F5FCFF' : '#fff',
                                    fontWeight: selectedStoreId === store.id ? 'bold' : 'normal'
                                }}
                            >
                                <td>{store.name}</td>
                                <td>{store.address}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </RESULT>
            </LIST>
            <BUTTON>
                <button onClick={moveToItems}>선택</button>
            </BUTTON>
        </div>
    )
}

export default SelectStore;