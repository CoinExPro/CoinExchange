import {
  setStore,
  getStore,
} from '@/util/store'
import config from '@/config/index'
import { getDataApi } from '@/api/system';
import { analysisFunction } from '@/util/util';

const common = {
  state: {
    logo: getStore({ name: 'logo' }) || {},
    language: getStore({ name: 'language' }) || 'en',
    languageName: getStore({ name: 'languageName' }) || 'English',
    defaultLang: getStore({ name: 'defaultLang' }) || 'en',
    // language:  'en',
    // languageName: 'English',
    // defaultLang: 'en',
    exchange: getStore({ name: 'exchange' }) || 'cny',
    exchangeName: getStore({ name: 'exchangeName' }) || 'CNY',
    exchangeCode: getStore({ name: 'exchangeCode' }) || '¥',
    exchangeNum: getStore({ name: 'exchangeNum' }) || '1',
    themeName: getStore({ name: 'themeName' }) || 'theme-night',
    config: config,
    languageData: getStore({ name: 'languageData' }) || '',
    allImgUrl: getStore({ name: 'allImgUrl' }) || {},
    dealType: getStore({ name: 'dealType' }) || '',//现货cash  杠杠pry  
    currCoin: getStore({ name: 'currCoin' }) || {},//当前币种信息
    prySelf: getStore({ name: 'prySelf' }) || {},//是否 自动借款 自动还款
    contractType: getStore({ name: 'contractType' }) || '', //合约类型 ubw  bbw
    contractUnit: getStore({ name: 'contractUnit' }) || '',//合约计价单位
    assetSetting: getStore({ name: 'assetSetting' }) || { showAllAssets: true, type: 1 },//showAllAssets显示隐藏资产，type当前显示的钱包类型
    userVariation: {},//用户账户变动记录
    merchantVariation: {},//商家账户变动记录
    isRouterAlive: true,//是否重载页面
  },
  actions: {
    getAllLangData({ commit }) {
      getDataApi('1530094068845895681', {
        pageSize: -521,
      }).then(res => {
        let data = res.data.data.records
        commit('SET_LANGUAGE_DATA', data)
      })
    },
    getAllImgUrlData({ commit }) {
      getDataApi('1598229929599741953', {
        pageSize: -521,
      }).then(res => {
        let data = res.data.data.records
        commit('SET_All_IMG_URL', data)
      })
    }
  },
  mutations: {
    SET_IS_ROUTER_ALIVE: (state, isRouterAlive) => {
      state.isRouterAlive = isRouterAlive
    },
    SET_ASSETS_SETTING: (state, assetSetting) => {
      state.assetSetting = assetSetting
      setStore({
        name: 'assetSetting',
        content: state.assetSetting
      })
    },
    SET_LOGO: (state, logo) => {
      state.logo = logo
      setStore({
        name: 'logo',
        content: state.logo
      })
    },
    SET_LANGUAGE: (state, { language, languageName }) => {
      state.language = language
      state.languageName = languageName
      setStore({
        name: 'language',
        content: state.language
      })
      setStore({
        name: 'languageName',
        content: state.languageName
      })
    },
    SET_DEFAULT_LANG: (state, defaultLang) => {
      state.defaultLang = defaultLang
      setStore({
        name: 'defaultLang',
        content: state.defaultLang
      })
    },
    SET_EXCHANGE: (state, { exchange, exchangeName, exchangeCode, exchangeNum }) => {
      state.exchange = exchange
      state.exchangeName = exchangeName
      state.exchangeCode = exchangeCode
      state.exchangeNum = exchangeNum
      setStore({
        name: 'exchange',
        content: state.exchange
      })
      setStore({
        name: 'exchangeName',
        content: state.exchangeName
      })
      setStore({
        name: 'exchangeCode',
        content: state.exchangeCode
      })
      setStore({
        name: 'exchangeNum',
        content: state.exchangeNum
      })
    },
    SET_THEME_NAME: (state, themeName) => {
      state.themeName = themeName;
      setStore({
        name: 'themeName',
        content: state.themeName,
      })
    },
    SET_DEAL_TYPE: (state, dealType) => {
      state.dealType = dealType;
      setStore({
        name: 'dealType',
        content: state.dealType,
      })
    },
    SET_CURR_COIN: (state, currCoin) => {
      state.currCoin = currCoin;
      setStore({
        name: 'currCoin',
        content: state.currCoin,
      })
    },
    SET_LANGUAGE_DATA: (state, languageData) => {
      let lang = {}
      languageData.forEach(item => {
        try {
          if (item.attr_page != 'ht_config') {
            lang[`${item.attr_page}_${item.code}`] = analysisFunction(`function getData(){${item.text}}`)()
          }
        } catch (error) {
          console.warn(`数据格式异常:编号  ${item.code} ==> ` + error);
        }
      })
      setTimeout(() => {
        state.languageData = lang;
        console.log('所有翻译数据=====>', lang)
        setStore({
          name: 'languageData',
          content: state.languageData,
        })
      }, 0);

    },
    SET_All_IMG_URL: (state, allImgUrl) => {
      let imgs = {}
      allImgUrl.forEach(item => {
        imgs[item.code] = item.img
      })
      state.allImgUrl = imgs;
      setStore({
        name: 'allImgUrl',
        content: state.allImgUrl,
      })
    },
    SET_EXCHANGE_NUM: (state, exchangeNum) => {
      state.exchangeNum = exchangeNum;
      setStore({
        name: 'exchangeNum',
        content: state.exchangeNum,
      })
    },
    SET_PRY_SELF: (state, prySelf) => {
      state.prySelf = prySelf;
      setStore({
        name: 'prySelf',
        content: state.prySelf,
      })
    },
    SET_CONTRACT_TYPE: (state, contractType) => {
      state.contractType = contractType;
      setStore({
        name: 'contractType',
        content: state.contractType,
      })
    },
    SET_CONTRACT_UNIT: (state, contractUnit) => {
      state.contractUnit = contractUnit;
      setStore({
        name: 'contractUnit',
        content: state.contractUnit,
      })
    },
    SET_USER_VARIATION: (state, userVariation) => {
      state.userVariation = userVariation;
    },
    SET_MERCHANT_VARIATION: (state, merchantVariation) => {
      state.merchantVariation = merchantVariation;
    },

  }
}
export default common