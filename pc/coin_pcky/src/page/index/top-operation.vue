<template>
  <div class="top-operation">
    <div class="off-login" v-if="!isLogin">
      <div class="operat-item">
        <div class="operat-login" @click="$router.push({ path: '/user/login' })">
          <!-- Log In -->
          {{ currCoinLangFun("login") }}
        </div>
      </div>
      <div class="operat-item">
        <div class="operat-register" @click="$router.push({ path: '/user/register' })">
          <!-- Register -->
          {{ currCoinLangFun("register") }}
        </div>
      </div>
    </div>
    <div class="no-login" v-else>
      <div class="operat-item">
        <div
          style="margin-right: 24px"
          @click="newWindowOpen('/account/entrust')"
        >{{ currCoinLangFun("entrust", "full") }}</div>
      </div>
      <div class="operat-item">
        <el-tooltip placement="bottom" popper-class="operat-property-tooltip fit-tc-background">
          <div slot="content">
            <div class="content">
              <div class="content-total-box">
                <div class="border-bottom-line__second">
                  <div class="total-operat flex-box">
                    <div class="fit-tc-tip">
                      {{ currCoinLangFun("total_assets", "user") }}
                      <span>
                        <i
                          class="fa"
                          style="cursor: pointer"
                          :class="{
                            'fa-eye-slash': !assetSetting.showAllAssets,
                            'fa-eye': assetSetting.showAllAssets,
                          }"
                          @click="setAssetsConfig"
                        ></i>
                      </span>
                    </div>
                    <div
                      class="fit-tc-primary"
                      style="cursor: pointer"
                      @click="$router.push({ path: '/account/wallet/bills' })"
                    >
                      <i class="el-icon-tickets"></i>
                      {{ currCoinLangFun("bill", "user") }}
                    </div>
                  </div>
                  <div class="total-text">
                    <div class="fit-tc-primary" style="font-size: 24px">
                      ≈{{
                      showAssetFun(computeBTC(allAssets.usdt, allAssets.rate))
                      }}
                      BTC
                    </div>
                    <div
                      class="fit-tc-tip"
                      style="line-height: 20px"
                    >{{ showAssetFun(exchangeRateFilter(allAssets.usdt)) }}</div>
                  </div>
                </div>
              </div>
              <template v-for="item in walletData">
                <div
                  class="content-wallet-item fit-tc-hover"
                  :key="item.type"
                  v-if="item.type != '0'"
                  @click="toAssetsPage(item.type)"
                >
                  <div class="flex-box border-bottom-line__second">
                    <div class="wallet-text">
                      <div class="title fit-tc-tip hover">{{ item.name }}</div>
                      <div class="money fit-tc-primary">
                        ≈{{ showAssetFun(computeBTC(item.usdt, item.rate)) }}
                        BTC
                        <span
                          class="fit-tc-tip"
                        >
                          /
                          {{
                          showAssetFun(exchangeRateFilter(item.usdt))
                          }}
                        </span>
                      </div>
                    </div>
                    <div class="wallet-icon">
                      <i class="el-icon-arrow-right fit-tc-tip hover"></i>
                    </div>
                  </div>
                </div>
              </template>
            </div>
          </div>
          <div
            style="margin-right: 24px"
            @click="toAssetsPage('1')"
          >
            {{ currCoinLangFun("assets", "user") }}
            <i class="el-icon-caret-bottom"></i>
          </div>
        </el-tooltip>
      </div>
      <div class="operat-item">
        <el-tooltip placement="bottom" popper-class="operat-info-tooltip fit-tc-background">
          <div slot="content">
            <div class="info-box">
              <div class="flex-box info-conent border-bottom-line__second">
                <div class="info">
                  <div class="info-account fit-tc-primary">
                    {{ replaceAccount }}
                    <span
                      class="fit-tc-primary active-text"
                      v-if="userInfo.real_name_status == '0'"
                    >{{ currCoinLangFun("info_wrz", "center") }}</span>
                    <span
                      class="fit-error"
                      v-if="userInfo.real_name_status == '-1'"
                    >{{ currCoinLangFun("kyc_rzsb", "center") }}</span>
                  </div>
                  <div class="info-id fit-tc-tip">UID {{ userInfo.uid }}</div>
                </div>
                <div class="info-level fit-tc-hover hover flex-box fit-tc-primary active-text">
                  <span></span>
                  {{ userInfo.level_str }}
                </div>
              </div>
            </div>
            <div
              class="info-item fit-tc-hover"
              @click="
                $router.push({
                  path: '/account/center/info',
                })
              "
            >
              <div class="item-text">
                <div class="title fit-tc-primary">{{ currCoinLangFun("user_center", "user") }}</div>
                <div class="icon">
                  <i class="fit-tc-tip hover el-icon-right"></i>
                </div>
              </div>
            </div>
            <div
              class="info-item fit-tc-hover"
              @click="
                $router.push({
                  path: '/account/center/security',
                })
              "
            >
              <div class="item-text">
                <div class="title fit-tc-primary">{{ currCoinLangFun("security_set", "user") }}</div>
                <div class="icon">
                  <i class="fit-tc-tip hover el-icon-right"></i>
                </div>
              </div>
            </div>
            <div class="info-item fit-tc-hover" v-if="false">
              <div class="item-text">
                <div class="title fit-tc-primary">{{ currCoinLangFun("text_hdzzx", "top") }}</div>
                <div class="icon">
                  <i class="fit-tc-tip hover el-icon-right"></i>
                </div>
              </div>
            </div>
            <div
              class="info-item fit-tc-hover"
              @click="
                $router.push({
                  path: '/account/center/kyc',
                })
              "
            >
              <div class="item-text">
                <div
                  class="title fit-tc-primary"
                >{{ currCoinLangFun("identity_certificat", "user") }}</div>
                <div class="icon">
                  <i class="fit-tc-tip hover el-icon-right"></i>
                </div>
              </div>
            </div>
            <div class="info-link border-bottom-line__second"></div>
            <div @click="outLogin" class="info-item fit-tc-hover" style="margin-top: 8px">
              <div class="item-text">
                <div class="title fit-tc-primary">{{ currCoinLangFun("exit", "user") }}</div>
              </div>
            </div>
          </div>
          <div class="operat-avatar">
            <img :src="userInfo.avatar" alt />
          </div>
        </el-tooltip>
      </div>
    </div>
    <div class="operat-item" @click="langDialog = true">
      <div class="operat-lang">
        <img class="img" :src="themeName == 'theme-night' ?allImgUrl.top_lang_night:allImgUrl.top_lang_daytime" />
      </div>
    </div>
    <div
      class="operat-item"
      @click="
        setThemeNameFun(
          themeName == 'theme-night' ? 'theme-daytime' : 'theme-night'
        )
      "
    >
      <div class="operat-theme">
        <img class="img" :src="themeName == 'theme-night' ?allImgUrl.top_theme_night:allImgUrl.top_theme_daytime" />

      </div>
    </div>

    <el-dialog
      :title="currCoinLangFun('tips', 'all')"
      :visible.sync="langDialog"
      width="632px"
      center
      :modal-append-to-body="false"
      :append-to-body="true"
      custom-class="dialog-switch-lang"
    >
      <span>
        <div class="lang-content" v-show="active == 'lang'">
          <div
            v-for="item in langData"
            :key="item.value"
            class="lang-item"
            @click="setLanguageFun(item)"
            :class="{ active: language == item.value }"
          >{{ item.text }}</div>
        </div>
        <div class="lang-content" v-show="active == 'exchange'">
          <div
            v-for="item in exchangeData"
            :key="item.value"
            class="lang-item"
            @click="setExchangeFun(item)"
            :class="{ active: exchange == item.exchange_value }"
          >{{ item.quote_currency }}</div>
        </div>
      </span>
      <span slot="title">
        <div class="dialogTitle">
          <div class="box-tabs">
            <span
              class="box-tabs-item"
              @click="active = 'lang'"
              :class="{ active: active == 'lang' }"
            >{{ currCoinLangFun("language") }}</span>
            <span
              class="box-tabs-item"
              @click="active = 'exchange'"
              :class="{ active: active == 'exchange' }"
            >{{ currCoinLangFun("exchange") }}</span>
          </div>
        </div>
      </span>
      <span slot="footer" class="dialog-footer"></span>
    </el-dialog>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import { getDicTableData, getDataApi, setHtLangApi } from '@/api/system'
import { numberFilterFun, toThousands } from '@/util/util'

import config from '@/config/index'
export default {
  data() {
    return {
      isMoney: false,
      pageName: 'top',
      langDialog: false,
      langData: [], 
      exchangeData: [], 
      active: 'lang',
      exchangTimer: null,
      walletData: [], 
      allAssets: { name: '总资产', rate: '1.0', usdt: '0.0' }, 
    }
  },
  computed: {
    ...mapGetters([
      'isLogin',
      'userInfo',
      'themeName',
      'language',
      'exchange',
      'languageName',
      'exchangeName',
      'exchangeNum',
      'exchangeCode',
      'assetSetting',
      'allImgUrl',
    ]),
    replaceAccount() {
      let text = this.userInfo.email || this.userInfo.phone
      if (this.userInfo.email) {
        return text.slice(0, 2) + '***' + text.slice(-2)
      } else if (this.userInfo.phone) {
        return text.slice(0, 3) + '***' + text.slice(-4)
      } else {
        return '--'
      }
    },
  },
  mounted() {
    setHtLangApi(this.language)
    this.setThemeNameFun(this.themeName)
    this.getLangListFun()
    this.getExChangeFun()
    this.exchangTimer = setInterval(() => {
      this.getExChangeFun()
    }, config.exchangeTime)

  },
  methods: {
    ...mapMutations([
      'SET_THEME_NAME',
      'SET_LANGUAGE',
      'SET_EXCHANGE',
      'SET_EXCHANGE_NUM',
      'SET_ASSETS_SETTING',
      'SET_USER_INFO',
    ]),
    ...mapActions(['FedLogOut', 'getUserInfoData']),
    setAssetsConfig() {
      this.SET_ASSETS_SETTING({
        ...this.assetSetting,
        showAllAssets: !this.assetSetting.showAllAssets,
      })
    },
    showAssetFun(value) {
      return this.assetSetting.showAllAssets ? value : '***'
    },
    toAssetsPage(type) {
      this.$router.push({
        path: '/account/wallet/assets',
      })
      this.SET_ASSETS_SETTING({
        ...this.assetSetting,
        type,
      })
    },
    newWindowOpen(path, params = {}) {
      let routeUrl = this.$router.resolve({
        path: path,
        query: { ...params },
      })
      window.open(routeUrl.href, '_blank')
    },
    outLogin() {
      this.FedLogOut().then(() => {
        this.$router.push({
          path: '/',
        })
      })
    },
    currCoinLangFun(code, type = 'top') {
      return this.coinLangFun(type, code)
    },
    setThemeNameFun(themen) {
      console.log(themen)
      this.SET_THEME_NAME(themen)
      document.body.className = themen
    },
    getLangListFun() {
      getDicTableData('coin_lang').then((res) => {
        this.langData = res.data.data
      })
    },
    computeBTC(usdt, rate) {
      if (usdt && rate) {
        return `${toThousands(Number(usdt).myDiv(Number(rate)).toFixed(8))}`
      } else {
        return '0'
      }
    },
    exchangeRateFilter(val, money) {
      if (!val) {
        return ''
      }
      if (money) {
        val = Number(val).myMul(money)
      }
      return `${this.exchangeCode}${toThousands(
        Number(val).myMul(this.exchangeNum)
      )}`
    },
    getExChangeFun() {
      getDataApi('1531553843420971009', {
        pageSize: -521,
        country_flag: '1',
      }).then((res) => {
        this.exchangeData = res.data.data.records
        this.exchangeData.forEach((item) => {
          if (item.exchange_value == this.exchange) {
            this.SET_EXCHANGE_NUM(item.rate)
          }
        })
      })
    },
    async setLanguageFun(item) {
      let oldLang = this.language
      console.log(item.value, this.$i18n.locale)
      this.$i18n.locale = item.value
      this.SET_LANGUAGE({
        language: item.value,
        languageName: item.text,
      })
      if (item.value != oldLang) {
        setTimeout(() => {
          this.reloadViewFun()
          setHtLangApi(item.value)
        }, 0)
      }

      if (this.isLogin) {
        let userInfo = await this.getUserInfoData()
        this.SET_USER_INFO({
          ...this.userInfo,
          ...userInfo,
        })
      }
    },
    setExchangeFun(item) {
      this.SET_EXCHANGE({
        exchange: item.exchange_value,
        exchangeName: item.quote_currency,
        exchangeCode: item.exchange_code,
        exchangeNum: item.rate,
      })
    },
  },
}
</script>

<style lang="scss" scoped>
.top-operation {
  display: flex;
  align-items: center;
  .off-login,
  .no-login {
    display: flex;
    align-items: center;
  }
  .operat-item {
    font-size: 14px;
    cursor: pointer;
    .operat-login,
    .operat-register {
      padding: 2px 16px;
      height: 24px;
      line-height: 24px;
    }
    .operat-register {
      margin-left: 12px;
      border-radius: 2px;
      font-weight: 600;
    }
    .operat-login {
      background-color: #F2F2F2;
      color: black;
    }
    .operat-lang {
      display: flex;
      align-items: center;
      margin-left: 16px;
      .img {
        display: block;
      }
      .img-hover {
        display: none;
      }
      img {
        width: 22px;
        margin-right: 2px;
        margin-top: 2px;
      }
    }
    .operat-theme {
      font-size: 22px;
      margin-left: 16px;
      height: 22px;
      img{
        width: 25px;
        height: 25px;
        margin-top: -2px;
      }
    }
    .operat-avatar {
      img {
        width: 34px;
        height: 34px;
      }
    }
  }
  .operat-info {
    display: flex;
    align-items: center;
    font-size: 14px;
    .info {
      padding-left: 4px;
      width: 50px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .info-user {
      font-size: 18px;
    }
  }
}
</style>
<style lang="scss">
.dialog-switch-lang {
  .dialogTitle {
    .box-tabs {
      display: flex;
      align-items: center;
      border-bottom: 1px solid;
    }
    .box-tabs-item {
      font-size: 16px;
      line-height: 24px;
      padding: 0 0 14px;
      margin: 0 36px 0 0;
      font-weight: 500;
      margin-bottom: -1px;
      text-align: center;
      transition: color 0.3s;
      position: relative;
      white-space: nowrap;
      cursor: pointer;
      &::after {
        content: '';
        position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 2px;
      }
    }
  }
  .lang-content {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    .lang-item {
      font-weight: 400;
      width: 122px;
      height: 30px;
      cursor: pointer;
      margin-bottom: 8px;
      margin-right: 16px;
      line-height: 30px;
      text-indent: 12px;
      &.active {
        position: relative;
        &:before {
          background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAQCAYAAAAMJL+VAAAACXBIWXMAABYlAAAWJQFJUiTwAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAEhSURBVHgBrZRBboJAFIbfG6CJO4/AEruSjXtugLHpVo/QnqTpCbTrpqk3cNmkaQMrdckRXGmio0/eRAgGxBnl2w0D3//e8AChYbzwx0Xb+SZp9ZdTP0FoECV3HmZA5AJSIoH6FjTEmVyBbQHoNdJBWa6IN9IK7g6okydTfyXyG5+jIRhyTc4L1cHj4G9MCCNEnKx34jXbNJanL5Z2dsDTk1/qPP2/AdDLpQrukTMCcf+RtlGUdVuOjLwwcqvkbhi1ec515GrLpCKWt+z9jIvQkecBOiG3yM8C6kL4i7RJjE3lpYAsRPC/pCgrPaUnrwxguulxbNPjqArhgThIy9eRXwzI6Ax+J4BiWJQDHoL5Zy8GTUTd5uKrNwKC99MyNpUzR3yWySlnFfQ9AAAAAElFTkSuQmCC);
          position: absolute;
          right: 8px;
          top: 50%;
          transform: translateY(-50%);
          content: '';
          width: 12px;
          height: 8px;
          background-repeat: no-repeat;
          background-size: 100% 100%;
        }
      }
    }
  }
}
.operat-property-tooltip {
  padding: 0;
  width: 400px;
  .content {
    padding: 20px 0;
    font-size: 14px;
    .content-total-box {
      padding: 0 24px;
      .total-operat {
        justify-content: space-between;
      }
    }
    .content-wallet-item {
      padding: 0 24px;
      cursor: pointer;
      .wallet-text {
        flex: 1;
        .money {
          font-size: 18px;
          span {
            font-size: 14px;
            margin-left: 4px;
          }
        }
      }
      .wallet-icon {
        i {
          font-size: 20px;
          display: none;
        }
      }
      &:hover {
        i {
          display: block;
        }
      }
    }
    .border-bottom-line__second {
      padding: 16px 0;
    }
  }
}
.operat-info-tooltip {
  padding: 20px 0 10px;
  width: 320px;
  overflow: hidden;
  .info-box {
    padding: 0 24px;
    margin-bottom: 8px;
    .border-bottom-line__second {
      padding: 16px 0;
      align-items: center;
    }
    .info {
      flex: 1;
      .info-account {
        font-size: 24px;
        span {
          font-size: 14px;
        }
      }
      .info-id {
        font-size: 14px;
        padding-top: 16px;
      }
    }
    .info-level {
      padding: 0 16px 0 8px;
      height: 24px;
      border-radius: 14px;
      margin-right: -36px;
      cursor: pointer;
      span {
        display: block;
        width: 20px;
        height: 20px;
        background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAACXBIWXMAABCcAAAQnAEmzTo0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAGbSURBVHgB7ZRBTsJAFED/L2jijiOwBFY2Aok7uEGJ4FY5AXIC4ATiCaxbS0xvgDsTlcAK6q5HcOcC6HcGy6Yy7UzBxMW8pMlk5v/f18nvB9BoNBrN/wZlggrNtxoCdtgyxzLchVO+gz8iUahwOb3CYG1Htmdfq0zdd81PODCxQsXWewcIhoJjaamwTo0tc2RkbO/RfBDFGqKDQnPSi5HhnJ5k1/eQJNOc3IZ1LPbU+G2XeG1VIQPIgmSsUmsilNp8FNBNdJ9iaguFgtWyAYg+JEBE17ukuAwC9X8lIPm0yjRE9WJ7qGC95PHoeMzemocEENGeO2ftTd7uH+FHZpmte67pC+tAAipSDJc1rZtWRkpoK2Vkj6bE51AaJGU4Bkjguec+YFBn9spzh+ewXmrIyITx8oQTe6ySgxiYc6c6k42XuqEt3qjyTEHQlo0nhLaKjLLQRuqpagdA3eRI6npO2QZFlIU4H6PKkA23geic9cxgwWIgBUo9FIX1VJ/1VC8qMx9V+pCSvYSiUvvKHIzixavNH9BoNBrN4fkGgnqs9xCMyG4AAAAASUVORK5CYII=)
          no-repeat 50%;
        background-size: 100% auto;
      }
    }
  }
  .info-item {
    padding: 12px 24px;
    cursor: pointer;
    .item-text {
      display: flex;
      justify-content: space-between;
    }

    .title {
      font-size: 16px;
      line-height: 22px;
    }
    i {
      font-size: 20px;
      display: none;
    }
    &:nth-last-of-type(1) {
      margin-bottom: 8px;
    }
    &:hover {
      i {
        display: block;
      }
    }
  }
  .info-link {
    height: 1px;
    margin: 8px 24px 0;
  }
}
</style>
