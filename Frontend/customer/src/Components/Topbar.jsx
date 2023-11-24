import React, {useState} from "react";
import styled from "styled-components";
import { TiShoppingCart } from "react-icons/ti";
import { useRecoilValue } from 'recoil';
import { selectedStoreName } from '../state'; //

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
    width: 120px;
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
    font-size: 22px;
  }
  span{
    font-size: 15px;
    margin-top:7px;
    margin-left: 2px;
  }
  div{
    width: 60px;
    margin-right: 40px;
  }
`;

function Topbar(){
    const [name , setname] = useState("users")
    const storeName = useRecoilValue(selectedStoreName);

    return(
        <BOX>
            <LEFT>
            <h1>CS25</h1>
                <div>
                    <span>{storeName}점</span>
                </div>
            </LEFT>
            <RIGHT>
                <h3>{name}</h3>
                    <span>님 반갑습니다</span>
                <div>
                    <TiShoppingCart size="45" color="#5D6679"/>
                </div>
            </RIGHT>
        </BOX>
    )
}

export default Topbar;