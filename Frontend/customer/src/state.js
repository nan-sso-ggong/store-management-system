import { atom } from 'recoil';

export const selectedStoreName = atom({
    key: 'selectedStoreName',
    default: "미선택",
});

export const userNameState = atom({
    key: 'userNameState', // 고유한 ID (문자열)
    default: '', // 기본값
});