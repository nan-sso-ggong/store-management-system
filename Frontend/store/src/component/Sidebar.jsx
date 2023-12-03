import React, {useState} from "react";
import { Link } from "react-router-dom";
import styled, { createGlobalStyle } from 'styled-components';
import { AiOutlineHome } from "react-icons/ai";
import { TbBoxSeam } from "react-icons/tb";
import { HiClipboardDocumentList } from "react-icons/hi2";
import { RiFileList3Line } from "react-icons/ri";

const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
`;
const SIDEBAR= styled.div`
  height: 88vh;
  position: sticky;
  top: 50px;
  max-width: 15.2%;
  border-right:2px solid #F0F1F3;
`
const UL = styled.ul`
`
const LI = styled.li`
  display :flex;
  margin-left:30px;
  margin-top: 60px;
  Link{
    color: inherit;
    text-decoration: none;
  }
`
const StyledLink = styled(Link)`
  text-decoration: none;
  font-size: 18px;
  color: #747679;
  &:hover {
    color: black;
    font-weight:bold;
  }
`
export default function Sidebar() {
    return (
            <SIDEBAR>
                <div>
                <UL>
                    <LI>
                        <StyledLink to={'/store/selectstore'}>
                            <AiOutlineHome color="#747679"/>
                            <span> HOME</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/store/order'}>
                            <HiClipboardDocumentList color="#747679" />
                            <span> 발주 관리</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/store/inventory'}>
                            <TbBoxSeam color="#747679"/>
                            <span> 재고 관리</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/store/paymentlist'}>
                            <RiFileList3Line color="#747679"/>
                            <span> 주문 내역</span>
                        </StyledLink>
                    </LI>
                </UL>
                </div>
            </SIDEBAR>
    );
}