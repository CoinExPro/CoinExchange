<template>
  <div class="newhome-box background-color">
    <!-- banner -->
    <div class="newhome-banner-box">
      <div class="banner-content">
        <div class="banner-title title-color">{{ currCoinLangFun('index_banner_content.title_code') }}</div>
        <div class="banner-tip title-color">{{ currCoinLangFun('index_banner_content.tip_code') }}</div>
        <div class="banner-register" v-if="!isLogin">
          <avue-input v-model="index_value" :placeholder="currCoinLangFun('index_banner_input_tip')"></avue-input>
          <div class="btn" @click="$router.push({ path: '/user/register', query: { text: index_value } })">
            {{ currCoinLangFun('register') }}</div>
        </div>
        <div class="banner-gn flex-box">
          <div class="gn-item" v-for="(item, index) in getConfigFun('index_banner_content.icon_list', [])" :key="index">
            <div class="item-img">
              <el-image :src="getImgUrlFun(item.img_code)" />
            </div>
            <div class="item-title">{{ currCoinLangFun(item.title_code) }}</div>
            <div class="item-tip">{{ currCoinLangFun(item.tip_code) }}</div>
          </div>
        </div>
      </div>
      <el-image class="banner-img" v-show="themeName != 'theme-night'"
        :src="getImgUrlFun('index_banner_content.banner_code', 'white')"></el-image>
      <el-image class="banner-img" v-show="themeName == 'theme-night'"
        :src="getImgUrlFun('index_banner_content.banner_code', 'black')"></el-image>
    </div>
    <!-- 新闻 -->
    <div class="newhome-content newhome-news-box">
      <div class="news-top-box">
        <div class="news-top flex-box">
          <el-image v-show="themeName != 'theme-night'" :src="getImgUrlFun('index_news.img_code', 'white')"></el-image>
          <el-image v-show="themeName == 'theme-night'" :src="getImgUrlFun('index_news.img_code', 'black')"></el-image>
          <div class="top-title">NEWS</div>
          <div class="gg-carousel-box">
            <el-carousel height="24px" :autoplay="true" direction="vertical" indicator-position="none">
              <el-carousel-item v-for="item in gg_newsData" :key="item">
                <div class="top-text title-color"
                  @click="$router.push({ path: `/article/main/content/${item.article_type}/${item.id}` })">
                  {{ item.article_title }}</div>
                <div class="top-date">{{ item.article_time }}</div>
              </el-carousel-item>
            </el-carousel>
          </div>
          <div class="top-more flex-box">
            <span @click="$router.push({ path: getConfigFun('index_news.more_path') })">
              {{
                currCoinLangFun("view_more", "all")
              }}
            </span>
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </div>
      <div class="news-list">
        <div class="list-item" v-for="(item, index) in hd_newsData" :key="index">
          <el-image :src="item.article_img"
            @click="$router.push({ path: `/article/main/content/${item.article_type}/${item.id}` })">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </div>
      </div>
    </div>
    <!-- 行情 -->
    <div class="newhome-content market-container">
      <div class="market-wrapper">
        <div class="market-title content-title">
          <div class="title-txt title-color">{{ currCoinLangFun("market_title") }}</div>
        </div>
        <div class="market-nav flex-box">
          <ul class="flex-box">
            <li v-for="item in getConfigFun('index_market.market_list', [])" :key="item.value"
              :class="{ active: marketActive == item.value }" @click="marketActive = item.value">
              <span>{{ currCoinLangFun(item.label_code, item.label_path ? item.label_path : 'home_index') }}</span>
            </li>
          </ul>
        </div>

        <div class="market-table background-color">
          <el-table class="none-boder background-color" :data="currMarketTable" style="width: 1240px" height="386px">
            <el-table-column width="234">
              <template slot="header" slot-scope="scope">
                <span>{{ currCoinLangFun("table_coin_name", "exchange") }}</span>
              </template>
              <template slot-scope="scope">
                <div class="flex-box table-bzmc">
                  <el-image v-if="scope.row.bzicon" style="
                        width: 24px;
                        height: 24px;
                        margin-right: 4px;
                        margin-left: 20px;
                      " :src="scope.row.bzicon" lazy></el-image>
                  <span class="fit-tc-primary">{{ scope.row.bzmc }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="252" align="right" :label="currCoinLangFun('table_coin_price', 'exchange')">
              <template slot-scope="scope">
                <div class="fit-tc-primary">
                  {{ scope.row.close }} /
                  <span class="fit-tc-tertiary">
                    {{
                      exchangeFilter("close", scope.row)
                    }}
                  </span>
                </div>
              </template>
            </el-table-column>
            <el-table-column width="135" align="right" :label="currCoinLangFun('table_coin_zdf', 'exchange')">
              <template slot-scope="scope">
                <span :style="{
                  color: scope.row.zdf >= 0 ? '#27c28e' : '#e04d43',
                }">{{ scope.row.zdf | dzfFilter }}</span>
              </template>
            </el-table-column>
            <el-table-column width="232" align="right" :label="currCoinLangFun('orders_table_dir_amount', 'full')">
              <template slot-scope="scope">
                <span class="fit-tc-primary">{{ scope.row.cjl | toThousandsFilter }}</span>
              </template>
            </el-table-column>
            <el-table-column width="252" align="right" :label="currCoinLangFun('menu_cje', 'move_index')">
              <template slot-scope="scope">
                <span class="fit-tc-primary">
                  {{
                    exchangeFilter("cje", scope.row)
                  }}
                </span>
              </template>
            </el-table-column>
            <el-table-column width="135" align="right" label>
              <template slot-scope="scope">
                <el-button class="amount-btn" style="
                      background: none;
                      font-size: 18px;
                      padding: 8px 12px;
                      margin-right: 20px;
                    " @click="openDealFun(scope.row)">{{ currCoinLangFun("item_three", "top") }}</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div class="market-more flex-box">
          <span @click="$router.push({ path: getConfigFun('index_market.more_path') })">
            {{
              currCoinLangFun("view_more", "all")
            }}
          </span>
          <i class="el-icon-arrow-right"></i>
        </div>
      </div>
    </div>
    <!-- 介绍 -->
    <div class="newhome-introduce-bg">
      <div class="introduce-title">
        {{ currCoinLangFun(getConfigFun('index_js_content.data_title', [])) }}
      </div>
      <div class="newhome-content newhome-introduce-box">
        <div class="introduce-item" v-for="(item, index) in getConfigFun('index_js_content.data_list', [])" :key="index">
          <div class="item-type item-type-1" v-if="item.style_type == '1'">
            <div class="type-title-box">
              <div class="title title-color">{{ currCoinLangFun(item.title_code) }}</div>
              <div class="tip title-color">{{ currCoinLangFun(item.tip_code) }}</div>
            </div>
            <div class="img">
              <el-image :src="getImgUrlFun(item.img_code)" style="margin-top:30px"></el-image>
            </div>
          </div>
          <div class="item-type item-type-2" v-else-if="item.style_type == '2'">
            <div class="title title-color">{{ currCoinLangFun(item.title_code) }}</div>
            <div class="tip title-color">{{ currCoinLangFun(item.tip_code) }}</div>
            <div class="icon-list">
              <div class="icon-item" v-for="(child, i) in item.icon_list" :key="i">
                <div class="icon-item-img">
                  <el-image v-show="themeName != 'theme-night'" :src="getImgUrlFun(child.img_code, 'white')"></el-image>
                  <el-image v-show="themeName == 'theme-night'" :src="getImgUrlFun(child.img_code, 'black')"></el-image>
                </div>
                <div class="icon-item-title title-color">
                  {{ child.title_code ? currCoinLangFun(child.title_code) : child.title_text }}</div>
                <div class="icon-item-tip title-color">{{ currCoinLangFun(child.tip_code) }}</div>
              </div>
            </div>
          </div>
          <div class="item-type item-type-3" v-else-if="item.style_type == '3'">
            <el-image :src="getImgUrlFun(item.img_code)"></el-image>
          </div>
        </div>
      </div>
    </div>
    <!-- CoinExPro交易，随时随地 -->
    <div class="newhome-content newhome-deal">
      <div class="deal-title">
        <div class="title title-color">{{ currCoinLangFun('index_js_7_title') }}</div>
        <div class="title-tip title-color">{{ currCoinLangFun('index_js_7_tip') }}</div>
      </div>
      <div class="deal-content">
        <div class="content-left">
          <el-image :src="getImgUrlFun('index_js_appxz_left')"></el-image>
        </div>
        <div class="content-download">
          <div class="download-util" v-for="(item, index) in getConfigFun('index_js_content.data_list', [])" :key="index">
            <div class="ewm-box" v-if="item.style_type == '5'">
              <div class="ewm">
                <div class="qrcode-img" id="qrcode_Ios" ref="qrcode_Ios" v-show="appType == 'ios'"></div>
                <div class="qrcode-img" id="qrcode_Android" ref="qrcode_Android" v-show="appType == 'android'"></div>
              </div>
              <div class="text">
                <div class="text-download title-color">{{ currCoinLangFun('index_ewm_download') }}</div>
                <div class="text-type title-color">
                  <span @click="appType = 'ios'" :class="{ active: appType == 'ios' }">ios</span> &
                  <span @click="appType = 'android'" :class="{ active: appType == 'android' }">Android</span>
                </div>
              </div>
            </div>
            <div class="icon-list" v-if="item.style_type == '5'">
              <div class="icon-item" v-for="(child, i) in item.icon_list" :key="i" @click="openUrlFun(child.path_url)">
                <div class="icon-item-img">
                  <el-image v-show="themeName != 'theme-night'" :src="getImgUrlFun(child.img_code, 'black')"></el-image>
                  <el-image v-show="themeName == 'theme-night'" :src="getImgUrlFun(child.img_code, 'black')"></el-image>
                </div>
                <div class="icon-text">
                  <div class="icon-item-tip">{{ child.tip_text }}</div>
                  <div class="icon-item-text">{{ child.text }}</div>
                </div>
              </div>
            </div>

            <div class="equipment-list" v-if="item.style_type == '5'">
              <div class="icon-item" v-for="(child, i) in item.app_icon_list" :key="i" @click="openUrlFun(child.path_url)">
                <div class="icon-item-img">
                  <el-image v-show="themeName != 'theme-night'" :src="getImgUrlFun(child.img_code, 'white')"></el-image>
                  <el-image v-show="themeName == 'theme-night'" :src="getImgUrlFun(child.img_code, 'white')"></el-image>
                </div>
                <div class="icon-text">
                  <div class="icon-item-text">{{ child.text }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 学院 -->
    <div class="newhome-content newhome-college">
      <div class="college-title">
        <div class="title content-title title-color">{{ currCoinLangFun('college') }}</div>

      </div>
      <div class="college-list">
        <div class="list-item" v-for="(item, index) in collegeData" :key="index" @click="
          $router.push({
            path: `/article/main/content/${item.article_type}/${item.id}`,
          })
          ">
          <div class="item-img">
            <el-image :src="item.article_img">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
          <div class="item-text">{{ item.article_title }}</div>
        </div>
      </div>
    </div>
    <!-- 货币之旅 -->
    <div class="newhome-tour">
      <el-image v-show="themeName != 'theme-night'" :src="getImgUrlFun('index_tour.img_code', 'white')"></el-image>
      <el-image v-show="themeName == 'theme-night'" :src="getImgUrlFun('index_tour.img_code', 'black')"></el-image>
      <div class="tour-content">
        <div class="title title-color">{{ currCoinLangFun('index_tour.title_code') }}</div>
        <div class="btn-box" v-if="!isLogin">
          <div class="btn" @click="$router.push({ path: '/user/login' })">{{ currCoinLangFun('register') }}</div>
          <div class="btn" @click="$router.push({ path: '/user/register' })">{{ currCoinLangFun('login', 'top') }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getDataApi } from '@/api/system'
import { mapGetters, mapMutations } from 'vuex'
import { getMqttInitDataApi, getExchangeOptionalApi } from '@/api/fullScreen'

import { analysisFunction, toThousands, numberFilterFun } from '@/util/util'
import QRCode from 'qrcodejs2'
import mqtt from '@/mixins/mqtt'


export default {
  mixins: [mqtt],
  data() {
    return {
      window: '',
      index_config: {},
      index_value: '',
      gg_newsData: [],
      hd_newsData: [],
      allMarketData: {},
      marketTabs: [],
      marketSearch: '',
      marketActive: '',
      marketMqtt: null,
      marketZxData: [],
      appType: 'ios',
      appIosUrl: false,
      appAndroidUrl: false,
      collegeData: [],
    }
  },
  watch: {
    language() {
      this.selectLangInit()
    },
  },
  computed: {
    ...mapGetters([
      'allImgUrl',
      'isLogin',
      'themeName',
      'language',
      'exchangeCode',
      'exchange',
      'exchangeNum',
    ]),
    currMarketTable() {
      let data = [
        {
          "type": null,
          "symbolName": "ARB/USDT",
          "amount": 18207888.72,
          "open": 1.1026,
          "close": 1.1626,
          "high": 1.2246,
          "count": 47631.6,
          "low": 0.9576,
          "vol": 171342052.74699,
          "zdf": 5.44,
          "bzmc": "ARB/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/e0d49e73ba19524b2dcca6e97285530b.png",
          "cje": 171342052,
          "cjl": 18207888,
          "coinCoinId": "1642857795053461506",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": -1
        },
        {
          "type": null,
          "symbolName": "LUNA/USDT",
          "amount": 11188886.8092,
          "open": 0.5226,
          "close": 0.5891,
          "high": 0.6336,
          "count": 28736.04,
          "low": 0.4323,
          "vol": 50741419.32282,
          "zdf": 12.72,
          "bzmc": "LUNA/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/ac038084fa95e9edc9f308ee2960d0c8.webp",
          "cje": 50741419,
          "cjl": 11188886,
          "coinCoinId": "1531552115388694529",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 1
        },
        {
          "type": null,
          "symbolName": "BTC/USDT",
          "amount": 8781.9142116,
          "open": 36566.91,
          "close": 36457.89,
          "high": 37972.24,
          "count": 230205.36,
          "low": 35600,
          "vol": 2696490589.7437425,
          "zdf": -0.3,
          "bzmc": "BTC/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/0b9a958086b06f2b89fa7e64cf18fe58.webp",
          "cje": 2696490589,
          "cjl": 8781,
          "coinCoinId": "1531443165221703682",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 1
        },
        {
          "type": null,
          "symbolName": "LUNC/USDT",
          "amount": 40971967794.1224,
          "open": 0.00007163,
          "close": 0.00007372,
          "high": 0.00007649,
          "count": 23168.64,
          "low": 0.00005946,
          "vol": 24050975.38176519,
          "zdf": 2.92,
          "bzmc": "LUNC/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/ec6804e9e39caee082e679af2852e0cc.png",
          "cje": 24050975,
          "cjl": 40971967794,
          "coinCoinId": "1568781233682063361",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 1
        },
        {
          "type": null,
          "symbolName": "ETH/USDT",
          "amount": 139314.352344,
          "open": 1911.01,
          "close": 2096.31,
          "high": 2136.99,
          "count": 206692.2,
          "low": 1904.39,
          "vol": 2365347872.802405,
          "zdf": 9.7,
          "bzmc": "ETH/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/df895337f7077d0d57961e40b61e4390.webp",
          "cje": 2365347872,
          "cjl": 139314,
          "coinCoinId": "1531556799105056769",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 2
        },
        {
          "type": null,
          "symbolName": "XRP/USDT",
          "amount": 93000594.6,
          "open": 0.6954,
          "close": 0.6515,
          "high": 0.7049,
          "count": 78605.4,
          "low": 0.63,
          "vol": 519106802.3849,
          "zdf": -6.31,
          "bzmc": "XRP/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/xrp.png",
          "cje": 519106802,
          "cjl": 93000594,
          "coinCoinId": "1531606489104236545",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 3
        },
        {
          "type": null,
          "symbolName": "LINK/USDT",
          "amount": 2121489.2232,
          "open": 14.417,
          "close": 14.371,
          "high": 15.99,
          "count": 82316.04,
          "low": 13.589,
          "vol": 262000440.31473,
          "zdf": -0.32,
          "bzmc": "LINK/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/744f24f2202ab25b217ef90661f726a6.jpg",
          "cje": 262000440,
          "cjl": 2121489,
          "coinCoinId": "1538491858902384642",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 4
        },
        {
          "type": null,
          "symbolName": "BCH/USDT",
          "amount": 25209.47712,
          "open": 248.9,
          "close": 238,
          "high": 257.8,
          "count": 12798.72,
          "low": 212,
          "vol": 50555742.0323,
          "zdf": -4.38,
          "bzmc": "BCH/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/8f32ee057042670b8d941df537d5b233.jpg",
          "cje": 50555742,
          "cjl": 25209,
          "coinCoinId": "1538492344602787841",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 6
        },
        {
          "type": null,
          "symbolName": "ETC/USDT",
          "amount": 641382.8196,
          "open": 18.6,
          "close": 21.11,
          "high": 21.84,
          "count": 33343.92,
          "low": 17.92,
          "vol": 107017264.9025,
          "zdf": 13.49,
          "bzmc": "ETC/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/e1815748a9fa114edc5afa6d67959012.png",
          "cje": 107017264,
          "cjl": 641382,
          "coinCoinId": "1568785247622602754",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 7
        },
        {
          "type": null,
          "symbolName": "EOS/USDT",
          "amount": 4118904.72,
          "open": 0.708,
          "close": 0.682,
          "high": 0.722,
          "count": 6555.6,
          "low": 0.629,
          "vol": 23608308.6686,
          "zdf": -3.67,
          "bzmc": "EOS/USDT",
          "bzicon": "http://149.28.224.149:9000/coinexpro/temp/bi/4d8739aca6fcfe44e15394bc344ea8b0.jpg",
          "cje": 23608308,
          "cjl": 4118904,
          "coinCoinId": "1538492963052912642",
          "tradeTurnover": null,
          "topic": "xh_detail_",
          "sort": 8
        }
      ]
      return data
    },
  },
  filters: {
    toThousandsFilter(value) {
      return toThousands(value)
    },
    dzfFilter(value) {
      if (value >= 0) {
        return `+${value}%`
      } else {
        return `${value}%`
      }
    },
  },
  created() {
    getDataApi('1530464853989064705', {
      config_type: 'home_index',
    }).then((res) => {
      let data = res.data.data.records
      data.forEach((item) => {
        try {
          this.index_config[item.config_code] = analysisFunction(
            `function getData(){${item.config_data}}`
          )()
        } catch (error) {
          console.warn('解析异常', error, item.config_data)
        }
      })
      this.marketActive = this.index_config.index_market.market_list[0].value
      console.log(this.marketActive)
      this.initFun()
    })
  },
  methods: {
    ...mapMutations(['SET_CURR_COIN', 'SET_DEAL_TYPE']),
    initFun() {
      this.init = true
      this.selectLangInit()
      this.getMarketIndexData()
      this.getOptionalDataFun()
      this.marketMqtt = this.initMqtt('index', (data) => {
        this.allMarketData = data
      })
      if (this.isLogin) {
        this.initMqtt('all_symbol_detail', (data) => {
          let exchangeData = {
            close: Number(data.close),
            zdf: data.zdf - 0,
            cjl: Number(data.amount),
            cje: Number(data.vol),
          }
          let type = data.type
          this.marketZxData = this.marketZxData.map((item) => {
            if (item.symbol_name == data.symbolName) {
              if ((type == 'xh' && item.contract_type != '0') || !item.contract_type) {
                return item
              }
              if (type == 'ubw' && item.contract_type != '1') {
                return item
              }
              if (type == 'bbw' && item.contract_type != '2') {
                return item
              }
              item = {
                ...item,
                ...exchangeData,
              }
            }
            return item
          })
        })
      }
    },
    //获取自选数据
    getOptionalDataFun() {
      if (!this.isLogin) {
        return false
      }
      getExchangeOptionalApi().then((res) => {
        let data = res.data.data.filter((item) => item.contract_type == '0')
        data = data.map((item) => {
          item.bzicon = item.avatar
          item.bzmc = item.symbol_name
          item.close = Number(item.close)
          item.cjl = Number(item.amount)
          item.cje = Number(item.vol)
          item.zdf = item.zdf - 0
          return item
        })
        this.marketZxData = data
      })
    },
    setQrCodeFun(type, url) {
      setTimeout(() => {
        console.log(this.$refs)
        this.$refs[`qrcode_${type}`][0].innerHTML = ''
        new QRCode(`qrcode_${type}`, {
          width: 120,
          height: 120,
          text: url,
          render: 'canvas',
          correctLevel: QRCode.CorrectLevel.L,
        })
      }, 0)
    },
    selectLangInit() {
      this.getNewsDataFun()
      this.getCollegeDataFun()
    },
    getMarketIndexData() {
      getMqttInitDataApi('index').then((res) => {
        let data = res.data.data
        let keys = Object.keys(data)
        if (keys.length <= 0) {
          return false
        }
        for (let key in data) {
          data[key] = data[key].map((item) => {
            item.zdf = Number(item.zdf)
            return item
          })
        }
        this.allMarketData = data
      })
    },
    getNewsDataFun() {
      getDataApi('1530431184536666113', {
        ...this.index_config.index_news.gg_search_params,
        article_lang: this.language,
      }).then((res) => {
        let data = res.data.data.records
        this.gg_newsData = data.map((item) => {
          item.article_time = item.article_time.split(
            `${new Date().getFullYear()}-`
          )[1]
          return item
        })
      })
      getDataApi('1530431184536666113', {
        ...this.index_config.index_news.hd_search_params,
        article_lang: this.language,
      }).then((res) => {
        let data = res.data.data.records
        this.hd_newsData = data
      })
    },
    getCollegeDataFun() {
      getDataApi('1530431184536666113', {
        ...this.index_config.index_college.search_params,
        article_lang: this.language,
      }).then((res) => {
        let data = res.data.data.records
        this.collegeData = data
      })
    },
    getConfigFun(key, nullValue) {
      let keyArr = key.split('.')
      let data = this.index_config
      let bool = true
      keyArr.forEach((item) => {
        if (data[item] !== undefined && bool) {
          data = data[item]
        } else {
          bool = false
        }
      })
      if (!bool) {
        return nullValue ? nullValue : ''
      }
      if (key == 'index_market.market_list') {
        data = [
          {
            label_code: 'optional',
            label_path: 'exchange',
            value: 'zx',
          },
          ...data,
        ]
      }
      return data
    },
    getImgUrlFun(code, type) {
      if (typeof code == 'string' && code.indexOf('.') != -1) {
        code = this.getConfigFun(code)
      }
      if (type) {
        code = code[type]
      }
      return this.allImgUrl[code] ? this.allImgUrl[code] : ''
    },
    currCoinLangFun(code, type = 'home_index') {
      if (code && code.indexOf('.') != -1) {
        code = this.getConfigFun(code)
      }
      return this.coinLangFun(type, code)
    },
    openUrlFun(url) {
      if (url) {
        window.open(url, '_blank')
      }
    },
    exchangeFilter(type, data) {
      let money = data[type]
      return `${this.exchangeCode}${numberFilterFun(
        Number(money).myMul(this.exchangeNum)
      )}`
    },
    openDealFun(row) {
      this.SET_DEAL_TYPE('cash')
      this.SET_CURR_COIN({
        ...row,
        coinInfo: {},
        coinMoneyData: { closeData: {}, riseFallData: {} },
      })
      let path = `/exchange/full-screen/${row.symbolName.replace('/', '_')}`
      this.$router.push({ path })
    },
  },
}
</script>

<style lang="scss" scoped>
.newhome-box {
  .newhome-content {
    width: 1240px;
    margin: 0 auto;
  }

  .content-title {
    font-size: 40px;
    font-weight: bold;
    margin: 56px 0 40px 0;
  }
}

.newhome-banner-box {
  // position: relative;
  width: 100%;
  display: flex;
  justify-content: center;

  .banner-img {
    padding: 40px 0;
    width: 484px;
    margin-left: 100px;
  }

  .banner-content {

    // position: absolute;
    // left: 10%;
    // top: 50%;
    // transform: translateY(-50%);
    .banner-title {
      font-size: 56px;
      padding-bottom: 24px;
    }

    .banner-tip {
      font-size: 24px;
      padding-bottom: 24px;
    }

    .banner-register {
      display: flex;
      align-items: center;

      /deep/.el-input {
        width: 220px;

        .el-input__inner {
          width: 220px;
          height: 46px;
          background-color: #fff;
        }
      }

      .btn {
        margin-left: 24px;
        width: 98px;
        height: 46px;
        background-color: #0020fd;
        border-radius: 4px 4px 4px 4px;
        font-size: 16px;
        text-align: center;
        line-height: 46px;
        color: #fff;
        cursor: pointer;
      }
    }

    .banner-gn {
      justify-content: space-between;
      width: 552px;
      margin-top: 40px;

      .gn-item {
        width: 120px;

        .item-img {
          margin-bottom: 12px;
          display: flex;
          justify-content: center;

          img {
            width: 80px;
            height: 80px;
          }
        }

        .item-title {
          text-align: center;
          font-size: 18px;
          font-weight: bold;
          margin-bottom: 12px;
        }

        .item-tip {
          font-size: 14px;
          text-align: center;
        }
      }
    }
  }
}

.newhome-news-box {
  width: 100% !important;
  margin: initial !important;

  .news-top-box {
    display: flex;
    align-items: center;
    width: 100%;
    background-color: #f5f6fa;
    height: 68px;
    line-height: 68px;
    margin: 10px 0 30px;
  }

  .news-top {
    width: 1240px;
    margin: 0 auto;
    font-size: 14px;

    .el-image {
      display: flex;
    }

    img {
      width: 24px;
      height: 24px;
    }

    .top-title {
      color: #0020fd;
      padding: 0 24px 0 4px;
      font-weight: 400;
      height: 24px;
      line-height: 24px;
    }

    .gg-carousel-box {
      width: 100%;

      /deep/.el-carousel__container {
        border-right: 1px solid #b3b3b3;
      }

      .el-carousel__item {
        display: flex;
      }
    }

    .top-text {
      height: 24px;
      line-height: 24px;
      cursor: pointer;
    }

    .top-date {
      padding-left: 24px;
      color: #b3b3b3;
      padding-right: 12px;
      font-weight: 400;
      height: 24px;
      line-height: 24px;
    }

    .top-more {
      flex: 0 0 100px;
      color: #0020fd;
      padding-left: 12px;
      cursor: pointer;

      span {
        display: inline-block;
        padding-right: 4px;
        height: 24px;
        line-height: 24px;
      }

      i {
        height: 24px;
        line-height: 26px;
      }
    }
  }

  .news-list {
    width: 1240px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: repeat(4, 295px);
    justify-content: space-between;

    .list-item {
      cursor: pointer;
      border-radius: 4px;
      overflow: hidden;

      /deep/.el-image__inner {
        width: 295px;
        height: 170px;
      }

      /deep/.image-slot {
        width: 295px;
        height: 170px;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid #f1f1f1;
        box-sizing: border-box;

        i {
          font-size: 60px;
          color: #f1f1f1;
        }
      }
    }
  }
}

.market-container {
  width: 100%;
  height: auto;
  padding-bottom: 96px;
  display: flex;
  justify-content: center;

  .market-wrapper {
    width: 1200px;

    .market-title {
      padding: 0;
      height: auto;
      overflow: hidden;

      .title-txt {
        text-align: left;
        line-height: 56px;
        font-weight: 400;
        font-size: 40px !important;
        margin-bottom: 24px !important;
      }
    }

    .market-nav {
      display: flex;
      align-items: flex-start;
      justify-content: space-between;

      ul {
        li {
          padding: 0 0 14px;
          margin-right: 36px;
          font-size: 18px;
          line-height: 28px;
          font-weight: 400;
          cursor: pointer;
          position: relative;
          display: flex;
          align-items: flex-end;
          border-bottom: 2px solid transparent;

          &.active {
            color: #0020fd;
            border-color: #0020fd;

            &::after {
              content: '';
              display: block;
              width: 100%;
              height: 5px;
              position: absolute;
              bottom: 0;
            }
          }
        }
      }

      .market-search {
        .el-input {
          font-size: 14px;
        }
      }
    }

    .market-table {
      margin: 0 -20px;

      /deep/.el-table {
        &::before {
          height: 0;
        }
      }

      /deep/.el-table__header-wrapper {
        thead {
          font-weight: 400;

          tr {
            background: none !important;

            th {
              font-size: 14px;
              font-weight: 400;
              border: none !important;
              padding: 24px 0 0;
              line-height: 14px;
              height: 16px;
              background: none !important;

              span {
                margin-left: 20px !important;
              }
            }
          }
        }
      }

      /deep/.el-table__body-wrapper {
        .el-table__body {
          margin-top: 8px;
          display: block;

          tr {
            background: none !important;
            font-size: 18px;

            td {
              border-bottom: 0;
              padding: 0;
              height: 72px;
            }
          }

          tr:hover>td.el-table__cell {
            background-color: #0020fd;

            .fit-tc-primary {
              color: #fff !important;
            }
          }

          .amount-btn {
            background-color: #fff !important;
            border-color: #0020fd;
            color: #0020fd;
          }
        }
      }
    }

    .market-more {
      height: 20px;
      justify-content: center;
      margin-top: 32px;
      font-size: 14px;
      cursor: pointer;
      color: #0020fd;

      span {
        display: inline-block;
        height: 24px;
        line-height: 22px;
      }

      i {
        line-height: 26px;
      }
    }
  }
}

.newhome-introduce-bg {
  background-color: #f1f4fe;

  .introduce-title {
    text-align: center;
    font-size: 48px;
    font-weight: 700;
    padding: 40px 0 60px;
  }
}

.newhome-introduce-box {
  display: grid;
  grid-template-columns: repeat(2, 45%);
  justify-content: space-between;
  grid-row-gap: 40px;

  .introduce-item {
    position: relative;

    .type-title-box {
      position: absolute;
      left: 0;
      top: 0;
      z-index: 2;
    }

    .item-type {
      .title {
        font-size: 40px;
        font-weight: bold;
        padding-bottom: 24px;
      }

      .tip {
        font-size: 20px;
      }
    }

    .item-type-2 {
      .icon-list {
        margin-top: 40px;
        display: flex;
        grid-column-gap: 100px;

        .icon-item {
          .icon-item-img {
            margin-bottom: 24px;

            /deep/img {
              width: 48px;
              height: 48px;
            }
          }

          .icon-item-title {
            margin-bottom: 12px;
            font-size: 20px;
            font-weight: bold;
          }

          .icon-item-tip {
            font-size: 16px;
          }
        }
      }
    }

    .item-type-4 {
      text-align: center;

      .title {}

      .title-tip {}
    }

    .item-type-5 {}
  }
}

.newhome-deal {
  margin-top: 80px !important;

  .deal-title {
    text-align: center;
    font-size: 56px;
    padding-bottom: 60px;

    .title {
      font-weight: bold;
      padding-bottom: 30px;
    }

    .title-tip {
      font-size: 24px;
    }
  }

  .deal-content {
    display: flex;
    justify-content: space-between;

    .content-left {}

    .content-download {
      width: 50%;

      .download-util {
        .ewm-box {
          background-color: #f7f7f7;
          text-align: center;
          border-radius: 20px;
          padding: 10px 0 30px 0;

          .ewm {
            padding: 24px 0 14px 19px;
            display: flex;
            justify-content: center;
          }

          .qrcode-img {
            width: 140px;
            height: 140px;
            padding: 10px 10px 5px 20px;
            background-color: #fff;
            border-radius: 20px;

            /deep/img {
              padding: 5px;
              box-sizing: border-box;
              background-color: #fff;
            }

            i {
              font-size: 32px;
            }
          }

          .icon-box {
            display: flex;
            align-items: center;
            justify-content: center;
            border: 5px solid #fff;
            border-radius: 4px;
            box-sizing: border-box;
            width: 130px;
            height: 130px;
            margin-right: 10px;
            margin-bottom: 10px;
          }

          .text {
            .text-download {
              font-size: 16px;
              padding-bottom: 16px;
            }

            .text-type {
              font-size: 16px;

              span {
                cursor: pointer;
                text-decoration: underline;

                &.active {
                  color: #0020fd;
                }
              }
            }
          }
        }

        .icon-list {
          margin-top: 40px;
          display: flex;
          flex-wrap: wrap;
          grid-column-gap: 24px;

          .icon-item {
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 10px;
            width: 44%;
            background-color: black;
            color: white;
            padding: 5px 10px;

            .icon-item-img {
              margin: 8px 0;
              display: flex;
              align-items: center;
              justify-content: center;

              /deep/img {
                width: 40px;
                height: 40px;
              }
            }

            .icon-text {
              margin-left: 10px;
              text-align: center;

              .icon-item-tip {
                font-size: 14px;
              }

              .icon-item-text {
                font-size: 18px;
                font-weight: 700;

              }
            }

          }
        }

        .equipment-list {
          display: flex;
          width: 70%;
          margin: 0 auto;
          margin-top: 20px;

          .icon-item {
            cursor: pointer;
            border-radius: 10px;
            width: 35%;
            padding: 5px 10px;

            .icon-item-img {
              margin: 8px 0;
              display: flex;
              align-items: center;
              justify-content: center;

              /deep/img {
                width: 40px;
                height: 40px;
              }
            }

            .icon-text {
              margin-left: 10px;
              text-align: center;

              .icon-item-text {
                font-size: 18px;

              }
            }

          }
        }
      }

    }
  }

}

.newhome-college {
  text-align: center;
  margin-bottom: 100px !important;

  .college-title {
    display: flex;
    align-items: center;

    .title {
      flex: 1;
      font-size: 48px;
    }

    .more {
      color: #0020fd;
      cursor: pointer;
    }
  }

  .college-list {
    display: grid;
    justify-content: space-between;
    grid-template-columns: repeat(4, 295px);
    justify-content: space-between;

    .list-item {
      width: 295px;
      cursor: pointer;

      .item-img {
        border-radius: 4px;
        overflow: hidden;

        /deep/.el-image__inner {
          width: 295px;
          height: 170px;
        }

        /deep/.image-slot {
          width: 295px;
          height: 170px;
          display: flex;
          align-items: center;
          justify-content: center;
          border: 1px solid #f1f1f1;
          box-sizing: border-box;

          i {
            font-size: 60px;
            color: #f1f1f1;
          }
        }
      }

      .item-text {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        padding-top: 12px;
        font-weight: bold;
      }
    }
  }
}

.newhome-tour {
  position: relative;

  .tour-content {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translateX(-50%) translateY(-50%);

    .title {
      font-size: 56px;
      font-weight: bold;
      padding-bottom: 60px;
    }

    .btn-box {
      display: flex;
      grid-column-gap: 24px;

      .btn {
        flex: 1;
        height: 59px;
        border-radius: 4px 4px 4px 4px;
        box-sizing: border-box;
        font-size: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;

        &:nth-child(1) {
          background: #0020fd;
          color: #fff;
        }

        &:nth-child(2) {
          background: #fff;
          border: 1px solid #0020fd;
          color: #0020fd;
        }
      }
    }
  }
}

.theme-daytime {
  .background-color {
    background-color: #ffffff;
  }

  .title-color {
    color: #1a1a1a;
  }
}

.theme-night {
  .background-color {
    background-color: #0c1117;
  }

  .title-color {
    color: #ffffff;
  }

  .item-type-5 {
    .ewm-box {
      background: linear-gradient(90deg, #0c1117 0%, #0020fd 100%);
    }
  }
}
</style>