import React, { useState, useEffect } from 'react';
import axios from "axios";

const SignupForm = () => {
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [name, setName] = useState('');
    const [number, setNumber] = useState('');
    const [email, setEmail] = useState('');
    const [storeName, setStoreName] = useState('');
    const [storePhoneNumber, setStorePhoneNumber] = useState('');
    const [passwordMatchError, setPasswordMatchError] = useState(false);
    const [passwordFormatError, setPasswordFormatError] = useState(false);
    const [image, setImage] = useState(null);

    useEffect(() => {
        handlePasswordBlur(); // 초기 로딩 시에도 실행
    }, [confirmPassword]);

    useEffect(() => {
        // 비밀번호가 숫자와 영문의 조합인지 확인
        isPasswordValid();
    }, [password]);

    const isPasswordValid = () => {
        const isPasswordValidCheck = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,}$/.test(password);
        if (!isPasswordValidCheck) {
            setPasswordFormatError(true);
        } else {
            setPasswordFormatError(false);
        }
    };

    const handlePasswordBlur = () => {
        if (password !== confirmPassword) {
            setPasswordMatchError(true);
        } else {
            setPasswordMatchError(false);
        }
    };

    const handleImageChange = (e) => {
        // 이미지 변경 시 실행되는 핸들러
        const selectedImage = e.target.files[0];
        setImage(selectedImage);
    };

    const isFormValid = () => {
        // 입력값이 모두 채워져 있는지 확인
        return (
            id &&
            password &&
            confirmPassword &&
            name &&
            number &&
            email &&
            storeName &&
            storePhoneNumber &&
            !passwordFormatError &&
            !passwordMatchError
        );
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(isFormValid()+"asdf");
        if (!isFormValid()) {
            // 입력값이 유효하지 않으면 회원가입 로직 실행하지 않음
            return;
        }

        const data = {
            login_id: id,
            password: password,
            name: name,
            email: email,
            phone_number: number,
            store_name: storeName,
            store_callnumber: storePhoneNumber,
            store_thumbnaail: image,
        };

        axios.post('https://21fbeac1-c1d4-41dc-aeeb-e04b9315664e.mock.pstmn.io/api/v1/managers/join', data,{

        })
            .then(response => {
                // Handle the response from the server
                console.log(response.data);
                // You may want to redirect the user or perform other actions based on the response
            })
            .catch(error => {
                console.error('Error:', error);
                // Handle errors if any
            });
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                아이디:
                <input type="text" value={id} onChange={(e) => setId(e.target.value)} />
            </label>
            <br />
            <label>
                비밀번호:
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </label>
            {passwordFormatError && (
                <p style={{ color: 'red' }}>비밀번호는 숫자와 영문의 조합으로 8자 이상이어야 합니다.</p>
            )}
            <br />
            <label>
                비밀번호 확인:
                <input
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    style={{ borderColor: passwordMatchError ? 'red' : '' }}
                />
                {passwordMatchError && (
                    <p style={{ color: 'red' }}>비밀번호와 비밀번호 확인이 일치하지 않습니다.</p>
                )}
            </label>
            <br />
            <br />
            <label>
                이름:
                <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
            </label>
            <br />
            <label>
                번호:
                <input type="text" value={number} onChange={(e) => setNumber(e.target.value)} />
            </label>
            <br />
            <label>
                이메일:
                <input type="text" value={email} onChange={(e) => setEmail(e.target.value)} />
            </label>
            <br />
            <label>
                점포 이름:
                <input type="text" value={storeName} onChange={(e) => setStoreName(e.target.value)} />
            </label>
            <br />
            <label>
                점포 전화번호:
                <input
                    type="text"
                    value={storePhoneNumber}
                    onChange={(e) => setStorePhoneNumber(e.target.value)}
                />
            </label>
            <br />
            <label>
                이미지 업로드:
                <input type="file" accept="image/*" onChange={handleImageChange} />
            </label>
            <br />
            <button type="submit">회원가입</button>
        </form>
    );
};

export default SignupForm;