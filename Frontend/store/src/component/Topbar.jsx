import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import { useSelector, useDispatch } from "react-redux";

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
    width: 170px;
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

    const { users } = useSelector((state) => state)
    const dispatch = useDispatch()

    const [name , setname] = useState("users")
    const [store, setstore]= useState("locate")

    useEffect(() => {
        setname(users.name);
        setstore(users.store_name)
    }, [])

    if (users.store_change){
        setstore(users.store_name)
        dispatch({ type:"change" })
    }

    return(
        <BOX>
            <LEFT>
            <h1>CS25</h1>
                <div>
                    <span>{store}</span>
                </div>
            </LEFT>
            <RIGHT>
                <h3>{name}</h3>
                    <span>점주님</span>
                <div>
                </div>
            </RIGHT>
        </BOX>
    )
}

export default Topbar;