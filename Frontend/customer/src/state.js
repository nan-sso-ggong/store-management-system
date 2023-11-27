import { atom } from 'recoil';
export const selectedStoreIdState = atom({
    key: 'selectedStoreIdState',
    default: localStorage.getItem('storeId') || null,
});
export const storeNameState = atom({
    key: 'storeNameState',
    default: localStorage.getItem('storeName') || '매장을 선택해주세요',
});
export const userNameState = atom({
    key: 'userNameState',
    default: localStorage.getItem('userName') || '비회원',
});
export const cartState = atom({
    key: 'cartState',
    default: JSON.parse(localStorage.getItem('cart')) || [],
});
export const storeAddressState = atom({
    key: 'storeAddressState',
    default: localStorage.getItem('storeAddress') || '',
});