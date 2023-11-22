import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from "react-router-dom";
import api from '../Axios';
import styled from "styled-components";

const H2 = styled.h2`
  margin-left: 10%;
`;
function SelectStore(){
    const [search, setSearch] = useState(""); // 상태 추가

    const handleInputChange = (event) => {
        setSearch(event.target.value);
    }

    return(
        <div>
            <H2>점포선택</H2>
            <div>
                <span>매장명 검색</span>
                <input type="text" name="search" value={search} onChange={handleInputChange} placeholder="매장명을 입력해주세요" />
            </div>
        </div>
    )
}

export default SelectStore;