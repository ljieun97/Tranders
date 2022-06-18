import {
    FETCH_PRODUCT,
    FETCH_PRODUCT_LIST,
    FETCH_CHATROOM,
    FETCH_CHATROOM_LIST,
    FETCH_LOGIN_TOKEN, FETCH_REFRESH_TOKEN,
    FETCH_COMMUNITY_BOARD_LIST,
    FETCH_COMMUNITY_BOARD,
} from './mutation-types'
import cookies from "vue-cookies";

export default {
    [FETCH_PRODUCT](state, product) {
        state.product = product
    },
    [FETCH_PRODUCT_LIST](state, products) {
        state.products = products
    },
    [FETCH_LOGIN_TOKEN] (state, token){
        state.accessToken = token.accessToken;
        state.refreshToken = token.refreshToken;
    },
    [FETCH_REFRESH_TOKEN] (state, token){
        /*state.accessToken = token.accessToken;
        state.refreshToken = token.refreshToken;*/

        cookies.set("access_token", token.access_token);
        cookies.set("refresh_token", token.refresh_token);

    },
    [FETCH_CHATROOM] (state, chatroom) {
        state.chatroom = chatroom
    },
    [FETCH_CHATROOM_LIST] (state, chatrooms) {
        state.chatrooms = chatrooms
    },
    [FETCH_COMMUNITY_BOARD_LIST] (state, communityBoards) {
        state.communityBoards = communityBoards
    },
    [FETCH_COMMUNITY_BOARD] (state, communityBoard) {
        state.communityBoard = communityBoard
    },
}

