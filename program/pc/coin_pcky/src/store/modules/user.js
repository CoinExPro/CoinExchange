import { setToken, setRefreshToken, removeToken, removeRefreshToken } from '@/util/auth'
import { Message } from 'element-ui'
import { setStore, getStore } from '@/util/store'
import md5 from 'js-md5'
import { getFinger } from '@/util/fingerprint2';



const user = {
  state: {
    isLogin: getStore({ name: 'isLogin' }) || false,
    tenantId: getStore({ name: 'tenantId' }) || '',
    userInfo: getStore({ name: 'userInfo' }) || [],
    token: getStore({ name: 'token' }) || '',
    refreshToken: getStore({ name: 'refreshToken' }) || '',
  },
  actions: {
  
    updatePassword({ commit }, params) {
      let data = JSON.parse(JSON.stringify(params))
      data.newPassword = md5(data.newPassword)
      data.newPassword1 = md5(data.newPassword1)
      data.oldPassword = md5(data.oldPassword)

      return new Promise((resolve, reject) => {
        updatePwd(data).then(res => {
          resolve(res);
        }).catch(error => {
          reject(error);
        })
      })
    },
    
    //获取用户信息
    getUserInfoData() {
      return new Promise((resolve) => {
        getUserInfo().then((res) => {
          const data = res.data.data;
          resolve(data);
        })
      })
    },
    //清除登录信息
    FedLogOut({ commit }) {
      return new Promise((resolve) => {
        commit('SET_TOKEN', '');
        commit('SET_USER_INFO', {});
        commit('SET_IS_LOGIN', false);
        removeToken();
        removeRefreshToken();
        resolve();
      })
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
      setStore({ name: 'isLogin', content: state.isLogin })
    },
    SET_USER_INFO: (state, userInfo) => {
      if (!userInfo.avatar) {
        userInfo.avatar = "/img/user-img.png";
      }
      state.userInfo = userInfo;
      setStore({ name: 'userInfo', content: state.userInfo })
    },
    SET_TENANT_ID: (state, tenantId) => {
      state.tenantId = tenantId;
      setStore({ name: 'tenantId', content: state.tenantId })
    },
    SET_TOKEN: (state, token) => {
      setToken(token);
      state.token = token;
      setStore({ name: 'token', content: state.token })
    },
    SET_REFRESH_TOKEN: (state, refreshToken) => {
      setRefreshToken(refreshToken)
      state.refreshToken = refreshToken;
      setStore({ name: 'refreshToken', content: state.refreshToken })
    },
  },
}
export default user