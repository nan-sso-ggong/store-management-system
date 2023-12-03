import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import api from '../Axios';
import styled from "styled-components";
import { IoIosSearch } from "react-icons/io";
import { useRecoilState } from 'recoil';
import { selectedStoreIdState, storeNameState, storeAddressState } from '../state';

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
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
  span{
    font-size:18px;
  }
  input{
    width:500px;
    height:50px;
    font-size:18px;
    border-radius: 5px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
    border: 1px solid rgba(150,150,150,0.1);
  }
  button{
    cursor:pointer;
    height:55px;
    width:80px;
    font-size:16px;
    border-radius: 5px;
    border-color: white;
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
  height: 250px;   
  overflow-y: auto; 
  border-top: 2px solid black;
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
  table {
    width:990px;
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
    border-radius: 5px;
    border-color: white;
    color:white;
    &:hover{
      background-color: darkblue;
    }
  }
`

function SelectStore(){
    const navigate = useNavigate();
    const [search, setSearch] = useState([]); // 상태 추가
    const [searchText,setSearchText] = useState('');
    const [tempStoreName, setTempStoreName] = useState(''); // 임시 상점 이름 상태
    const [tempStoreId, setTempStoreId] = useState(null); // 임시 상점 ID 상태
    const [tempStoreAddress, setTempStoreAddress] = useState(''); // 임시 상점 주소 상태
    const [storeAddress, setStoreAddress] = useRecoilState(storeAddressState); // 상점 주소 상태
    const [storeName, setStoreName] = useRecoilState(storeNameState);
    const [selectedStoreId, setSelectedStoreId] = useRecoilState(selectedStoreIdState);

    const getSearch = async () => {
        try {
            const resp = await api.get(`/customers/store?name=${encodeURIComponent(searchText)}`);
            if(resp && resp.data && resp.data.data) {
                setSearch(resp.data.data);
            } else {
                console.error('No data received');
            }
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };
    const selectStore = (storeId, storeName, storeAddress) => {
        setTempStoreId(storeId);
        setTempStoreName(storeName);
        setTempStoreAddress(storeAddress);
    };

    const moveToItems = () => {
        if (tempStoreId) {
            setStoreName(tempStoreName);
            setSelectedStoreId(tempStoreId);
            setStoreAddress(tempStoreAddress);
            localStorage.setItem('storeAddress', tempStoreAddress);
            alert(`선택된 매장으로 이동합니다.`);
            navigate(`/customer/${tempStoreId}/selectitems`);
        } else {
            alert('매장이 선택되지 않았습니다.');
        }
    };

    return(
        <div>
            <H2>점포선택</H2>
            <CONTAINER>
                <SEARCH>
                    <span>매장명 검색</span>
                    <input type="text" name="search" value={searchText} onChange={(e) => setSearchText(e.target.value)} placeholder="매장명을 입력해주세요" />
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
                            <tr key={store.id}  onClick={() => selectStore(store.id, store.name, store.address)}
                                style={{
                                    cursor: 'pointer',
                                    backgroundColor: tempStoreId === store.id ? '#F5FCFF' : '#fff',
                                    fontWeight: tempStoreId === store.id ? 'bold' : 'normal'
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