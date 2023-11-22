import React, {useState} from "react";
import { Link } from "react-router-dom";
import styled, { createGlobalStyle } from 'styled-components';
import { AiOutlineHome } from "react-icons/ai";
import { TiShoppingCart } from "react-icons/ti";
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
                        <StyledLink to={'/customer/selectstore'}>
                            <AiOutlineHome color="#747679"/>
                            <span> 점포선택</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/customer/:storeId/selectItems'}>
                            <HiClipboardDocumentList color="#747679" />
                            <span> 상품목록</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/customer/shoppingcart'}>
                            <TiShoppingCart color="#747679"/>
                            <span> 장바구니</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/customer/paymentlist'}>
                            <RiFileList3Line color="#747679"/>
                            <span> 구매내역</span>
                        </StyledLink>
                    </LI>
                </UL>
                </div>
            </SIDEBAR>
    );
}