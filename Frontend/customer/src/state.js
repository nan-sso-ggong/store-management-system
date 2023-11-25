import { atom } from 'recoil';

export const selectedStoreName = atom({
    key: 'selectedStoreName',
    default: "미선택",
});