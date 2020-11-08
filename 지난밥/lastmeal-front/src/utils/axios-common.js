import axios from 'axios';

// axios 객체 생성
export default axios.create({
    // baseURL: 'http://127.0.0.1:8000/api/',
    baseURL: 'https://j3d205.p.ssafy.io/api/', 
    headers: {
        'Content-type': 'application/json',
    },
});
   