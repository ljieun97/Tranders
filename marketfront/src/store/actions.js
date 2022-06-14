import {
    FETCH_REFRESH_TOKEN,
    FETCH_PRODUCT,
    FETCH_PRODUCT_LIST,
    FETCH_CHATROOM_LIST,
} from './mutation-types'

import axios from 'axios'

export default {
    fetchProduct({ commit }) {
        return axios.get('http://localhost:7777/product/${productNo}')
            .then((res) => {
                commit(FETCH_PRODUCT, res.data)
            })
    },
    fetchProductList({ commit }) {
        return axios.get('http://localhost:7777/product/list')
            .then((res) => {
                commit(FETCH_PRODUCT_LIST, res.data)
            })
    },
    refreshToken ({commit}) {
        return axios.get('http://localhost:7777/member/refreshToken',{
            headers: {
                'Authorization': 'Bearer '+localStorage.getItem('refresh_token'),
                'Accept' : 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then((res) => {
                commit(FETCH_REFRESH_TOKEN, res.data)
                console.log('refresh Token')
            })
    },
    fetchChatroomList ({ commit }) {
        return axios.get('http://localhost:7777/chatting/list')
            .then((res) => {
                commit(FETCH_CHATROOM_LIST, res.data)
            })
    },
}