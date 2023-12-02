import React, {useEffect} from "react";
import { Link,useLocation,useNavigate } from "react-router-dom";
import styled, { createGlobalStyle } from 'styled-components';
import { useRecoilValue } from 'recoil';
import { selectedStoreIdState} from '../state';
import { AiOutlineHome } from "react-icons/ai";
import { TiShoppingCart } from "react-icons/ti";
import { HiClipboardDocumentList } from "react-icons/hi2";
import { RiFileList3Line } from "react-icons/ri";
import { IoIosLogOut } from "react-icons/io";
import api from "../Axios";

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
  margin-left:25px;
  margin-top: 60px;
  Link{
    color: inherit;
    text-decoration: none;
  }
`
const StyledLink = styled(Link)`
  text-decoration: none;
  font-size: 18px;
  font-weight: ${props => props.isActive ? 'bold' : 'normal'};
  color: ${props => props.isActive ? 'black' : '#747679'};
`
const LOGOUT = styled.div`
  font-size: 18px;
  cursor: pointer;
  color: #747679;
`
export default function Sidebar() {

    const storeId = useRecoilValue(selectedStoreIdState);
    const navigate = useNavigate();

    useEffect(() => {
        localStorage.setItem('storeId', storeId);
    }, [storeId]);
    const location = useLocation();

    const logout = () => {
        api.post(`/customers/logout`)
            .then(response => {
                console.log(response)
                localStorage.removeItem('access_token');
                localStorage.removeItem('refresh_token');
                localStorage.removeItem('userName');
                alert("로그아웃 되었습니다.");
                navigate('/auth');
            })
            .catch(error => {
                console.error(error);
            });
    };
    return (
            <SIDEBAR>
                <div>
                <UL>
                    <LI>
                        <StyledLink to={'/customer/selectstore'} isActive={location.pathname === `/customer/selectstore`}>
                            <AiOutlineHome color="#747679"/>
                            <span> 점포선택</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={`/customer/${storeId}/selectItems`} isActive={location.pathname === `/customer/${storeId}/selectItems`}>
                            <HiClipboardDocumentList color="#747679" />
                            <span> 상품목록</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/customer/shoppingcart'} isActive={location.pathname === `/customer/shoppingcart`}>
                            <TiShoppingCart color="#747679"/>
                            <span> 장바구니</span>
                        </StyledLink>
                    </LI>
                    <LI>
                        <StyledLink to={'/customer/paymentlist'} isActive={location.pathname === `/customer/paymentlist`}>
                            <RiFileList3Line color="#747679"/>
                            <span> 구매내역</span>
                        </StyledLink>
                    </LI>
                    <LI style={{marginTop:"220px"}}>
                        <LOGOUT onClick={logout}>
                            <IoIosLogOut  color="#747679"/>
                            <span> 로그아웃</span>
                        </LOGOUT>
                    </LI>
                </UL>
                </div>
            </SIDEBAR>
    );
}