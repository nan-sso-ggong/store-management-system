import { atom } from 'recoil';

export const selectedStoreIdState = atom({
    key: 'selectedStoreIdState', // unique ID (with respect to other atoms/selectors)
    default: null, // default value (aka initial value)
});
export const storeNameState = atom({
    key: 'storeNameState', // unique ID (with respect to other atoms/selectors)
    default: '매장을 선택해주세요', // default value (aka initial value)
});

export const userNameState = atom({
    key: 'userNameState', // 고유한 ID (문자열)
    default: '비회원', // 기본값
});