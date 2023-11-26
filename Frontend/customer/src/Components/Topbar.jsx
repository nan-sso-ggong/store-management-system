import React from "react";
import styled from "styled-components";
import { TiShoppingCart } from "react-icons/ti";
import { useRecoilValue } from 'recoil';
import { storeNameState,userNameState } from '../state';

const BOX = styled.div`
  height:80px;
  border:2px solid #F0F1F3;
  background-color:white;
  display :flex;
  text-align:center;
  justify-content: space-between;
  align-items: center;
`;
const LEFT = styled.div`
  text-align: left;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-left:40px;
  h1{
    font-size: 50px;
    color: lightseagreen;
  }
  div{
    width: 160px;
    font-size: 13px;
    margin-left: 10px;
    margin-top: 30px;
    color: darkgreen;
    font-weight: bold;
  }
`;
const RIGHT = styled.div`
  text-align: right;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top:15px;
  h3{
    font-size: 20px;
  }
  span{
    font-size: 15px;
    margin-top:5px;
    margin-left: 3px;
  }
  div{
    width: 60px;
    margin-right: 40px;
  }
`;

function Topbar(){
    const storeName = useRecoilValue(storeNameState);
    const userName = useRecoilValue(userNameState);
    return(
        <BOX>
            <LEFT>
            <h1>CS25</h1>
                <div>
                    <span>{storeName}</span>
                </div>
            </LEFT>
            <RIGHT>
                <h3>{userName}</h3>
                    <span>님 반갑습니다</span>
                <div>
                    <TiShoppingCart size="45" color="#5D6679"/>
                </div>
            </RIGHT>
        </BOX>
    )
}

export default Topbar;