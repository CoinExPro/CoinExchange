const getters = {
  logo: state => state.common.logo,
  language: state => state.common.language,
  languageName: state => state.common.languageName,
  defaultLang: state => state.common.defaultLang,
  dealType: state => state.common.dealType,
  currCoin: state => state.common.currCoin,
  exchange: state => state.common.exchange,
  exchangeName: state => state.common.exchangeName,
  exchangeCode: state => state.common.exchangeCode,
  exchangeNum: state => state.common.exchangeNum,
  config: state => state.common.config,
  themeName: state => state.common.themeName,
  languageData: state => state.common.languageData,
  allImgUrl: state => state.common.allImgUrl,
  prySelf: state => state.common.prySelf,
  contractType: state => state.common.contractType,
  contractUnit: state => state.common.contractUnit,
  userInfo: state => state.user.userInfo,
  token: state => state.user.token,
  isLogin: state => state.user.isLogin,
  assetSetting: state => state.common.assetSetting,
  userVariation: state => state.common.userVariation,
  merchantVariation: state => state.common.merchantVariation,
  isRouterAlive: state => state.common.isRouterAlive,


}
export default getters