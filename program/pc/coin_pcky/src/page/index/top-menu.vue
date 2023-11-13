<template>
  <div class="top-menu">
    <div
      class="top-logo"
      @click="$router.push({ path: '/' })"
      v-if="navType == 'default'"
    >
      <img
        v-show="themeName == 'theme-night'"
        class="night_logo_img"
        :src="logo.night"
      />
      <img
        v-show="themeName != 'theme-night'"
        class="daytime_logo_img"
        :src="logo.daytime"
      />
    </div>
    <div class="menu-list" v-if="navType == 'default'">
      <div class="menu-item-box" v-for="item in menu" :key="item.id">
        <div class="menu-item" v-if="item.nav_show == '1'">
          <div class="item-content" v-if="item.children">
            <el-popover
              v-model="item.isShow"
              placement="bottom"
              width="320"
              trigger="hover"
              popper-class="menu-child-popover common-propver"
            >
              <div class="menu-child-list">
                <div
                  class="menu-child-item-box"
                  v-for="child in item.children"
                  :key="child.id"
                >
                  <div
                    class="menu-child-item"
                    @click="navRouterFun(child)"
                    v-if="child.nav_show == '1'"
                  >
                    <div class="child-content">
                      <h3>{{ currCoinLangFun(child.nav_code + "_title") }}</h3>
                      <p>{{ currCoinLangFun(child.nav_code + "_text") }}</p>
                    </div>
                    <div class="child-icon">
                      <i class="el-icon-right"></i>
                    </div>
                  </div>
                </div>
              </div>
              <div slot="reference" class="item-title">
                <span>{{ currCoinLangFun(item.nav_code) }}</span>
                <i class="el-icon-caret-bottom"></i>
              </div>
            </el-popover>
          </div>
          <div class="item-content" v-else>
            <div class="item-title" @click="navRouterFun(item)">
              {{ currCoinLangFun(item.nav_code) }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="exchange-full-box" v-if="navType == 'exchange'">
      <div class="top-logo" @click="$router.push({ path: '/' })">
        <img
          v-show="themeName == 'theme-night'"
          class="night_logo_img"
          :src="logo.night"
        />
        <img
          v-show="themeName != 'theme-night'"
          class="daytime_logo_img"
          :src="logo.daytime"
        />
        <div class="top-text fit-tc-primary">- {{ dealTitle }} -</div>
      </div>
      <div class="exchange-type-nav">
        <el-popover
          v-model="exchangeShow"
          trigger="hover"
          placement="bottom"
          width="196"
          popper-class="exchange-type-nav-pop"
        >
          <div class="exchange-type-nav-box">
            <div
              class="nav-item hover-bg-i"
              v-for="(item, index) in exchange_nav_data"
              :key="index"
              @click="exchangeNavFun(item)"
            >
              <span>{{ currCoinLangFun(item.nav_code + "_title") }}</span>
              <i class="el-icon-right"></i>
            </div>
          </div>
          <span slot="reference" class="fit-tc-primary">
            <i
              class="el-icon-s-grid"
              style="font-size: 20px; padding-right: 12px"
            ></i>
          </span>
        </el-popover>
      </div>
      <div class="screen-box">
        <div class="screen-box-top flex-box">
          <img class="coin-img" :src="currCoin.bzicon" alt />
          <div class="screen-symbol">
            <el-popover
              placement="bottom"
              width="650"
              trigger="click"
              popper-class="exchange-full-markets-top-prop"
              v-model="isMarkets"
            >
              <div class="exchange-full-markets-top">
                <div class="nav-box fit-ta-border-color" v-if="!isSearch">
                  <div class="nav-list">
                    <div
                      class="item fit-tc-primary"
                      :class="[{ 'active-text': areaTabsValue == item.value }]"
                      v-for="item in areaTabs"
                      :key="item.value"
                      @click="areaThemeHandleCommand(item.value, 'theme')"
                    >
                      {{
                        currCoinLangFun(
                          item.code ? item.code : `coin_plate_${item.value}`,
                          "exchange"
                        )
                      }}
                    </div>
                    <div class="item">
                      <el-dropdown
                        trigger="click"
                        @command="areaThemeHandleCommand"
                      >
                        <span
                          class="el-dropdown-link item fit-tc-primary"
                          :class="[{ 'active-text': areaThemeDrop }]"
                          >{{ areaThemeDropTitle }}</span
                        >
                        <i
                          class="el-icon-caret-bottom fit-tc-primary"
                          :class="[{ 'active-text': areaThemeDrop }]"
                        ></i>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item
                            v-for="item in areaTheme"
                            :key="item.value"
                            :class="[
                              { 'active-text': areaThemeDrop == item.value },
                            ]"
                            :command="item.value"
                            >{{currCoinLangFun('coin_plate_'+item.value,'exchange')}}</el-dropdown-item
                          >
                        </el-dropdown-menu>
                      </el-dropdown>
                    </div>
                  </div>
                  <div class="nav-icon">
                    <i class="el-icon-search" @click="isSearch = true"></i>
                  </div>
                </div>
                <div
                  class="nav-child-box flex-box"
                  v-if="areaCoin.length > 0 && !isSearch"
                >
                  <div
                    class="child-item fit-tc-tertiary"
                    v-for="(item, index) in areaCoin"
                    :key="index"
                    :class="{ 'active-text': areaCoinValue == item }"
                    @click="areaCoinValue = item"
                  >
                    {{ item }}
                  </div>
                </div>
                <div class="nav-search" v-if="isSearch">
                  <el-input
                    :placeholder="currCoinLangFun('search', 'all')"
                    prefix-icon="el-icon-search"
                    v-model="searchValue"
                  >
                    <i
                      slot="suffix"
                      @click="isSearch = false"
                      class="el-input__icon el-icon-close"
                      style="cursor: pointer"
                    ></i>
                  </el-input>
                </div>
              </div>
              <div
                class="
                  exchange-full-markets-content
                  fit-background
                  table-border-null
                "
              >
                <el-table
                  :data="tableData"
                  style="width: 100%"
                  max-height="414px"
                  @row-click="marketsRowClickFun"
                >
                  <el-table-column width="150" align="left">
                    <template slot="header">
                      <div class="table-coin-header">
                        <span
                          class="fit-tc-tertiary"
                          :class="{ 'active-text': coinType == 'hl' }"
                          @click="coinType = 'hl'"
                          >{{ exchangeName }}</span
                        >
                        <i
                          class="el-icon-refresh fit-tc-tertiary active-text"
                          @click="
                            coinType == 'USDT'
                              ? (coinType = 'hl')
                              : (coinType = 'USDT')
                          "
                        ></i>
                        <span
                          class="fit-tc-tertiary"
                          :class="{ 'active-text': coinType == 'USDT' }"
                          @click="coinType = 'USDT'"
                          >USDT</span
                        >
                      </div>
                    </template>
                    <template slot-scope="scope">
                      <span class="fit-tc-primary table-coin-text flex-box">
                        <i
                          class="fa fa-star-o fit-tc-primary fit-tc-tertiary"
                          @click="setOptionalFun('add', scope.row)"
                          v-if="!scope.row.collect"
                        ></i>
                        <i
                          class="fa fa-star"
                          style="color: #2271e6"
                          @click="setOptionalFun('cancel', scope.row)"
                          v-else
                        ></i>
                        <span class="text fit-tc-primary">
                          {{ scope.row.coin_symbol }}
                        </span>
                        <span
                          class="fit-tc-tertiary"
                          v-show="areaTabsValue == 'zx'"
                          >/{{ scope.row.base_symbol }}</span
                        >
                        <span
                          v-if="scope.row.isCou"
                          class="times border-color fit-tc-primary active-text"
                          >{{ scope.row.cou }}x</span
                        >
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    :label="currCoinLangFun('table_coin_close', 'exchange')"
                    sortable
                    align="right"
                    prop="close"
                    width="120"
                  >
                    <template slot-scope="scope">
                      <span>
                        <span
                          class="fit-tc-primary"
                          v-show="coinType == 'hl'"
                          >{{ exchangeRateFilter(scope.row.close) }}</span
                        >
                        <span
                          class="fit-tc-primary"
                          v-show="coinType == 'USDT'"
                          >{{ scope.row.closeText }}</span
                        >
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    :label="currCoinLangFun('table_coin_zdf', 'exchange')"
                    sortable
                    align="center"
                    width="120"
                    prop="zdf"
                  >
                    <template slot-scope="scope">
                      <span
                        class="fit-tc-primary"
                        :class="{
                          'fit-tc-fall': scope.row.zdf < 0,
                          'fit-tc-rise': scope.row.zdf >= 0,
                        }"
                      >
                        {{ scope.row.zdf
                        }}{{ scope.row.zdf !== undefined ? "%" : "" }}
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    :label="currCoinLangFun('table_coin_amount', 'exchange')"
                    sortable
                    width="250"
                    align="right"
                    prop="amount"
                  >
                    <template slot-scope="scope">
                      <span>{{ scope.row.amountText }}</span>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <div slot="reference" class="coin-list fit-tc-primary">
                <span class="coin-title">{{ currCoin.symbol }}</span>
                <i class="el-icon-caret-bottom"></i>
              </div>
            </el-popover>
          </div>
        </div>
        <div class="screen-contrast fit-tc-tip">
          <el-popover
            placement="bottom"
            trigger="click"
            popper-class="exchange-full-comparison-prop"
          >
            <div class="token-desc">
              <div class="exchange-tabs fit-background-di">
                <span
                  class="box-tabs-item fit-tc-tip"
                  @click="priceType = 'jgdb'"
                  :class="{ 'bg-active': priceType == 'jgdb' }"
                  >{{ currCoinLangFun("price_comparison", "exchange") }}</span
                >
                <span
                  class="box-tabs-item fit-tc-tip"
                  @click="priceType = 'bzjs'"
                  :class="{ 'bg-active': priceType == 'bzjs' }"
                  >{{ currCoinLangFun("coin_lntroduction", "exchange") }}</span
                >
              </div>
              <ul
                class="price-diff-wrap fit-tc-primary"
                v-if="priceType == 'jgdb'"
              >
                <li
                  class="
                    price-diff-title
                    flex-box
                    fit-tc-border-color fit-tc-tip
                  "
                >
                  <span>
                    {{ currCoinLangFun("table_coin_trading", "exchange") }}
                  </span>
                  <span>
                    {{ currCoinLangFun("table_coin_close", "exchange") }}
                  </span>
                  <span>
                    {{ currCoinLangFun("table_coin_zdf", "exchange") }}
                  </span>
                </li>
                <li
                  class="price-diff-item fit-tc-border-color"
                  v-for="(item, index) in priceTableData"
                  :key="index"
                >
                  <span>{{ item.symbol_name }}</span>
                  <span>
                    <div>{{ item.closeText }} {{ item.base_symbol }}</div>
                    <div class="mg-t4">
                      ≈ {{ exchangeRateFilter(item.close) }}
                    </div>
                  </span>
                  <span
                    :class="{
                      'fit-tc-fall': item.zdf < 0,
                      'fit-tc-rise': item.zdf >= 0,
                    }"
                    >{{ item.zdf }}%</span
                  >
                </li>
              </ul>
              <div class="coni-introduce" v-else>
                <div class="title fit-tc-border-color">
                  <img
                    :src="getObjData(currCoin.coinInfo.coinCoin, 'avatar')"
                  />
                  <div class="fit-tc-primary">
                    {{ getObjData(currCoin.coinInfo.coinCoin, "name") }}
                  </div>
                  <span class="fit-tc-tip">
                    {{ getObjData(currCoin.coinInfo.coinCoin, "full_name") }}
                  </span>
                </div>
                <div class="info">
                  <div class="info-item fit-tc-border-color">
                    <div class="item-content">
                      <div class="tip fit-tc-tip">
                        {{
                          currCoinLangFun(
                            "lntroduction_circulation",
                            "exchange"
                          )
                        }}
                      </div>
                      <div class="text fit-tc-primary">
                        {{
                          getObjData(
                            currCoin.coinInfo.coinCoin,
                            "circulate_cou",
                            "number"
                          )
                        }}
                        {{ getObjData(currCoin.coinInfo.coinCoin, "symbol") }}
                      </div>
                    </div>
                    <div class="item-content">
                      <div class="tip fit-tc-tip">
                        {{ currCoinLangFun("lntroduction_total", "exchange") }}
                      </div>
                      <div class="text fit-tc-primary">
                        {{
                          getObjData(
                            currCoin.coinInfo.coinCoin,
                            "total_cou",
                            "number"
                          )
                        }}
                        {{ getObjData(currCoin.coinInfo.coinCoin, "symbol") }}
                      </div>
                    </div>
                  </div>
                  <div class="info-item fit-tc-border-color">
                    <div class="item-content">
                      <div class="tip fit-tc-tip">
                        {{ currCoinLangFun("lntroduction_price", "exchange") }}
                      </div>
                      <div class="text fit-tc-primary">
                        {{
                          getObjData(
                            currCoin.coinInfo.coinCoin,
                            "cost",
                            "number"
                          )
                        }}
                      </div>
                    </div>
                    <div class="item-content">
                      <div class="tip fit-tc-tip">
                        {{ currCoinLangFun("lntroduction_date", "exchange") }}
                      </div>
                      <div class="text fit-tc-primary">
                        {{
                          getObjData(
                            currCoin.coinInfo.coinCoin,
                            "publish_time",
                            "date"
                          )
                        }}
                      </div>
                    </div>
                  </div>
                </div>
                <div class="brief">
                  <div class="fit-tc-tip">
                    {{ currCoinLangFun("lntroduction_info", "exchange") }}
                  </div>
                  <p class="fit-tc-primary">
                    {{
                      getObjData(
                        currCoin.coinInfo.coinCoin,
                        "information",
                        "",
                        "str"
                      )
                    }}
                  </p>
                </div>
                <div
                  class="more fit-tc-primary active-text"
                  @click="$router.push({ path: currCoin.coinInfo.moreUrl })"
                  v-if="currCoin.coinInfo.moreUrl"
                >
                  {{ currCoinLangFun("view_more", "all") }}
                  <i class="el-icon-right"></i>
                </div>
              </div>
            </div>
            <div slot="reference" class="contrast-text">
              <i class="fa fa-gg"></i>
              <span>{{ currCoinLangFun("price_comparison", "exchange") }}</span>
            </div>
          </el-popover>
        </div>
      </div>
      <div class="box-line"></div>
      <div class="exchange-data-box">
        <div class="box-item fit-tc-tertiary">
          <div class="tip">
            {{ currCoinLangFun("table_coin_close", "exchange") }}
          </div>
          <el-tooltip
            effect="dark"
            :content="'≈ ' + exchangeRateFilter(currCoin.coinMoneyData.close)"
            placement="bottom"
          >
            <div
              class="text border-bottom"
              :class="currCoin.coinMoneyData.closeData.className"
            >
              {{ currCoin.coinMoneyData.closeData.close }}
            </div>
          </el-tooltip>
        </div>
        <div class="box-item fit-tc-tertiary">
          <div class="tip">
            {{ currCoinLangFun("table_coin_zdf", "exchange") }}
          </div>
          <div
            class="text"
            :class="currCoin.coinMoneyData.riseFallData.className"
          >
            {{ currCoin.coinMoneyData.riseFallData.percentage }} ({{
              currCoin.coinMoneyData.riseFallData.money
            }})
          </div>
        </div>
        <div class="box-item fit-tc-tertiary">
          <div class="tip">
            {{ currCoinLangFun("table_coin_high", "exchange") }}
          </div>
          <div class="text fit-tc-primary">
            {{ currCoin.coinMoneyData.highText }}
          </div>
        </div>
        <div class="box-item fit-tc-tertiary">
          <div class="tip">
            {{ currCoinLangFun("table_coin_low", "exchange") }}
          </div>
          <div class="text fit-tc-primary">
            {{ currCoin.coinMoneyData.lowText }}
          </div>
        </div>
        <div class="box-item fit-tc-tertiary">
          <div class="tip">
            {{ currCoinLangFun("table_coin_amount", "exchange") }}
          </div>
          <el-tooltip
            effect="dark"
            :content="
              '≈ ' +
              exchangeRateFilter(
                currCoin.coinMoneyData.amount,
                currCoin.coinMoneyData.close
              )
            "
            placement="bottom"
          >
            <div class="text border-bottom fit-tc-primary">
              {{ currCoin.coinMoneyData.amountText }}
            </div>
          </el-tooltip>
        </div>
        <div class="box-item fit-tc-tertiary">
          <div class="tip">
            {{ currCoinLangFun("table_coin_vol", "exchange") }}
          </div>
          <el-tooltip
            effect="dark"
            :content="'≈ ' + exchangeRateFilter(currCoin.coinMoneyData.vol)"
            placement="bottom"
          >
            <div class="text border-bottom fit-tc-primary">
              {{ currCoin.coinMoneyData.volText }}
            </div>
          </el-tooltip>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  setTreeDataUtil,
  getStrDataFunction,
  numberFilterFun,
} from "@/util/util";
import { isURL } from "@/util/validate";
import { getDataApi, getDicTableData } from "@/api/system";
import { mapGetters, mapMutations } from "vuex";
import mqtt from "@/mixins/mqtt";
export default {
  mixins: [mqtt],
  data() {
    return {
      isMarkets: false,
      defaultExchange: "",
      exchangeShow: false,
      pageName: "top",
      menu: [],
      exchange: [],
      exchangeNavObj: {},
      //价格对比
      priceType: "jgdb",
      priceData: [],
      // 现货 杠杠
      areaTabsValue: "",
      areaTabs: [], //板块
      areaTheme: [], //主题
      areaThemeDrop: "",
      areaCoin: [],
      areaCoinValue: "",
      tableDataObj: {
        zx: [],
        xh: [], //现货
      },
      isSearch: false,
      searchValue: "",
      coinType: "USDT",
      throttle: false,
    };
  },
  computed: {
    ...mapGetters([
      "logo",
      "themeName",
      "dealType",
      "currCoin",
      "exchangeName",
      "exchangeCode",
      "exchangeNum",
      "isLogin",
    ]),
    navType() {
      let type = "default";
      if (this.$route.path.indexOf("exchange/full-screen") != -1) {
        type = "exchange";
        this.exchangeInit();
      } else {
        this.clearExchange();
      }
      return type;
    },
    dealTitle() {
      if (!this.exchangeNavObj[this.dealType]) {
        return "";
      }
      let code = this.exchangeNavObj[this.dealType].nav_code;
      return this.currCoinLangFun(code + "_title");
    },
    areaThemeDropTitle() {
      let title = this.currCoinLangFun("coin_plate_theme", "exchange");
      if (this.areaThemeDrop) {
        this.areaTheme.forEach((item) => {
          if (item.value == this.areaThemeDrop) {
            title = this.currCoinLangFun(
              "coin_plate_" + item.value,
              "exchange"
            );
          }
        });
      }
      return title;
    },
    tableData() {
      let baseCoin = {};
      let tabsType = this.areaTabsValue == "zx" ? "zx" : "xh";
      let data = this.tableDataObj[tabsType].filter((item) => {
        //现货
        if (tabsType == "xh") {
          if(!item.plate){
            item.plate=''
          }
          let plate = item.plate.split(",");
          //现货 区分板块
          if (
            !plate.includes(this.areaTabsValue) &&
            !plate.includes(this.areaThemeDrop)
          ) {
            return false;
          }
          //设置当前结算币
          if (!this.areaCoinValue) {
            this.areaCoinValue = item.base_symbol;
          }
          //区分 结算币
          baseCoin[item.base_symbol] = "";
          if (item.base_symbol != this.areaCoinValue) {
            return false;
          }
        }
        //是否搜索
        if (
          this.searchValue &&
          item.symbol_name
            .split("/")[0]
            .toLowerCase()
            .indexOf(this.searchValue.toLowerCase()) == -1
        ) {
          return false;
        }
        return true;
      });
      this.setAreaCoinFun(baseCoin);
      return data;
    },
    priceTableData() {
      let data = this.deepClone(
        this.currCoin.coinInfo.coinListMap
          ? this.currCoin.coinInfo.coinListMap
          : []
      );
      return data;
    },
    exchange_nav_data() {
      return this.exchange.filter((item) => item.nav_type != this.dealType);
    },
  },
  created() {
    //获取导航数据
    getDataApi("1530112754394718209", {
      column: "id",
      order: "asc",
      pageSize: -521,
    }).then((res) => {
      let data = res.data.data.records;
      data = setTreeDataUtil(data, "pid");
      //排序
      let sortFun = (obj1, boj2) => {
        let a = obj1.nav_sort;
        let b = boj2.nav_sort;
        if (a > b) return 1;
        if (b > a) return -1;
        return 0;
      };
      data = data.sort(sortFun);
      this.menu = data.map((item) => {
        if (item.children) {
          item.isShow = false;
          item.children = item.children.sort(sortFun);
          item.children.forEach((child) => {
            if (child.id == "1530117755481481217") {
              this.defaultExchange = child.nav_path;
            }
          });
        }
        return item;
      });
      console.log("导航数据===>", data);
    });
    //获取交易内部导航
    getDataApi("1530464853989064705", {
      config_code: "exchange_nav_data",
    }).then((res) => {
      let data = res.data.data.records[0];
      let navData = getStrDataFunction(data.config_data);
      this.exchange = navData;
      let exchangeNavObj = {};
      navData.forEach((item) => {
        exchangeNavObj[item.nav_type] = item;
      });
      this.exchangeNavObj = {
        ...exchangeNavObj,
      };
    });
    if (!this.currCoin.coinMoneyData) {
      this.SET_CURR_COIN({
        ...this.currCoin,
        coinInfo: {},
        coinMoneyData: { closeData: {}, riseFallData: {} },
      });
    }
    this.areaTabs = [
      {
        value: "zx",
        lable: this.currCoinLangFun("optional", "exchange"), //自选
        text: this.currCoinLangFun("optional", "exchange"), //自选
        code: "optional",
      },
    ];
    this.getAreaTabsDataFun();
  },
  methods: {
    ...mapMutations(["SET_DEAL_TYPE", "SET_CURR_COIN", "SET_CONTRACT_TYPE"]),
    currCoinLangFun(code, type = "top") {
      return this.coinLangFun(type, code);
    },
    getObjData(data, type, disposeType, dis) {
      let text = "--";
      if (data && data[type]) {
        if (disposeType == "number") {
          text = numberFilterFun(data[type]);
        } else if (disposeType == "date") {
          text = data[type].split(" ")[0];
        } else {
          text = data[type];
        }
      }
      if (dis == "str") {
        text = getStrDataFunction(text);
        text = this.dataCoinLangFun("", text);
      }
      return text;
    },
    navRouterFun(data) {
      if (!data.nav_path) {
        return false;
      }

      //现货交易  特殊处理
      if (data.id == "1530117082144694273") {
        this.SET_DEAL_TYPE("cash");
      }
      //杠杠交易 特殊处理
      if (data.id == "1530117755481481217") {
        this.SET_DEAL_TYPE("pry");
      }
      //合约 ubw
      if (data.id == "1530120028890718210") {
        this.SET_CONTRACT_TYPE("ubw");
      }
      //合约 bbw
      if (data.id == "1530120509004308481") {
        this.SET_CONTRACT_TYPE("bbw");
      }
      this.menu = this.menu.map((item) => {
        if (item.children) {
          item.isShow = false;
        }
        return item;
      });
      if (data.id == "1530114692465156097" && !this.isLogin) {
        this.$router.push({
          path: `/user/login`,
        });
        return false;
      }
      if (isURL(data.nav_path)) {
        window.open(data.nav_path);
      } else {
        this.$router.push({ path: data.nav_path });
      }
    },
    exchangeNavFun(data) {
      console.log("======>", data, this.currCoin);
      let path = "";
      if (["ubw", "bbw"].includes(data.nav_type)) {
        path = data.nav_path + "BTC_USDT";
      } else {
        path = data.nav_path + this.currCoin.symbol.replace("/", "_");
      }
      if (this.currCoin.isPry === false) {
        path = this.defaultExchange;
        this.SET_DEAL_TYPE("pry");
      }
      console.log(path, this.$route.path);
      if (path == this.$route.path) {
        this.exchangeShow = false;
        this.SET_DEAL_TYPE(data.nav_type);
        return false;
      }
      this.$router.push({ path });
      this.SET_CONTRACT_TYPE(data.nav_type);
    },
    exchangeRateFilter(val, money) {
      if (!val) {
        return "";
      }
      if (money) {
        val = Number(val).myMul(money);
      }
      return `${this.exchangeCode}${numberFilterFun(
        Number(val).myMul(this.exchangeNum)
      )}`;
    },
    marketsRowClickFun(row) {
      this.SET_DEAL_TYPE("cash");
      this.$router.push({
        path: "/exchange/full-screen/" + row.symbol_name.replace("/", "_"),
      });
      this.isMarkets = false;
    },
    //获取主题字典数据
    getAreaTabsDataFun() {
      getDicTableData("coin_plate").then((res) => {
        let data = res.data.data;
        this.areaTheme = data.filter((item) => {
          if (["zbq", "cxq"].includes(item.value)) {
            if (!this.areaTabsValue) {
              this.areaTabsValue = item.value;
            }
            this.areaTabs.push(item);
            return false;
          }
          return true;
        });
        this.areaTabsValue = this.areaTabs[0].value;
      });
    },
    //设置当前tabs
    areaThemeHandleCommand(command, type) {
      if (type == "theme") {
        this.areaThemeDrop = "";
        this.areaTabsValue = command;
      } else {
        this.areaThemeDrop = command;
        this.areaTabsValue = "";
      }
      this.areaCoinValue = "";
    },
    //获取自选数据
    getOptionalDataFun() {
      if (!this.isLogin) {
        return false;
      }
      getExchangeOptionalApi().then((res) => {
        let data = res.data.data;
        data = data.filter((item) => {
          if (item.optional == "hy") {
            return false;
          }
          return true;
        });
        data = data.map((item) => {
          item.coin_symbol = item.symbol_name.split("/")[0];
          item.collect = 1;
          item.cou = item.part_cou;
          item.isCou = true;
          if (item.part_cou <= 1) {
            item.cou = item.full_cou;
          }
          if (item.part_cou <= 1 && item.full_cou <= 1) {
            item.isCou = false;
          }
          item.close = Number(item.close);
          item.closeText = numberFilterFun(item.close);
          item.amount = Number(item.amount);
          item.amountText = `${numberFilterFun(item.amount)} ${
            item.symbol_name.split("/")[0]
          }`;
          return item;
        });
        this.tableDataObj.zx = data;
      });
    },
    //设置结算币
    setAreaCoinFun(data) {
      this.areaCoin = Object.keys(data);
      if (!this.areaCoinValue) {
        this.areaCoinValue = this.areaCoin[0];
      }
    },
    //设置自选
    setOptionalFun(type, data) {
      console.log(data);
      if (!this.isLogin) {
        this.$router.push({ path: "/user/login" });
        return false;
      }
      if (this.throttle) {
        return false;
      }
      this.throttle = true;
      if (type == "add") {
        getExchangeAddOptionalApi(data.id, "1")
          .then((res) => {
            this.throttle = false;
            console.log("自选设置成功===", res.data.data);
            let tabsValue = this.areaTabsValue == "zx" ? "zx" : "xh";
            this.tableDataObj[tabsValue] = this.tableDataObj[tabsValue].map(
              (item) => {
                if (data.id == item.id) {
                  item.collect = 1;
                }
                return item;
              }
            );
            //重新获取自选数据
            this.getOptionalDataFun();
          })
          .catch(() => {
            this.throttle = false;
          });
      } else {
        let tabsValue = "1";
        let id = data.c_id;
        if (this.areaTabsValue != "zx") {
          id = data.id;
        }
        getExchangeClearOptionalApi(id, tabsValue)
          .then((res) => {
            this.throttle = false;
            console.log("取消自选成功===", res.data.data, data);
            this.tableDataObj.zx = this.tableDataObj.zx.filter((item) => {
              if (id == item.c_id) {
                return false;
              }
              return true;
            });
            console.log(id);
            this.tableDataObj.xh = this.tableDataObj.xh.map((item) => {
              if (id == item.id) {
                item.collect = 0;
              }
              return item;
            });
          })
          .catch(() => {
            this.throttle = false;
          });
      }
    },
    //初始化现货 杠杠交易
    exchangeInit() {
      this.getOptionalDataFun();
      this.initMqtt("all_symbol_detail", (data) => {
        let exchangeData = {
          symbol: data.symbolName,
          closeText: numberFilterFun(data.close), //最新成交价
          close: Number(data.close),
          zdf: data.zdf, //涨跌幅
          // high: numberFilterFun(data.high), //24小时最高
          // low: numberFilterFun(data.low), //24小时最低
          amount: Number(data.amount),
          amountText: `${numberFilterFun(data.amount)} ${
            data.symbolName.split("/")[0]
          }`, //24小时成交量
          // vol: data.vol, //24小时成交额
        };
        let currCoin = this.deepClone(this.currCoin);
        if (currCoin.coinInfo.coinListMap) {
          currCoin.coinInfo.coinListMap = currCoin.coinInfo.coinListMap.map(
            (item) => {
              if (item.symbol_name == exchangeData.symbol) {
                item = {
                  ...item,
                  ...exchangeData,
                };
              }
              return item;
            }
          );
          this.SET_CURR_COIN(currCoin);
        }
        //更新现货 自选数据
        let typeArr = ["xh", "zx"];
        typeArr.forEach((type) => {
          this.tableDataObj[type] = this.tableDataObj[type].map((item) => {
            if (item.symbol_name == exchangeData.symbol) {
              item = {
                ...item,
                ...exchangeData,
              };
            }
            return item;
          });
        });
      });
    },
    clearExchange() {
      this.mqttArr.forEach((item) => {
        this.closeMqtt(item);
      });
    },
  },
};
</script>

<style lang="scss">
.menu-child-popover {
  min-width: 320px !important;
  width: auto !important;
  padding: 20px 0;
  border: 0;
  .popper__arrow {
    display: none;
  }
  .menu-child-list {
    .child-icon {
      font-size: 22px;
      opacity: 0;
    }
    .child-content {
      h3 {
        margin: 0;

        font-size: 18px;
        line-height: 28px;
        text-align: left;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        font-weight: 400;
      }
      p {
        font-size: 12px;
        line-height: 16px;
        margin-top: 4px;
        text-align: left;
      }
    }
    .menu-child-item-box {
    }
    .menu-child-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 12px;
      margin: 0 12px;
      cursor: pointer;
      &:hover {
        .child-icon {
          opacity: 1;
        }
      }
    }
  }
}
.exchange-full-markets-top-prop {
  .exchange-full-markets-top {
    .nav-box {
      display: flex;
      align-items: center;
      justify-content: space-between;
      position: relative;
      padding: 0 12px;
      border-bottom: 2px solid;
      .nav-list {
        display: flex;
        align-items: center;
        .item {
          font-size: 14px;
          line-height: 20px;
          padding: 8px 0;
          position: relative;
          cursor: pointer;
          margin-right: 16px;
          .item {
            margin-right: 0;
          }
          &::after {
            content: "";
            display: block;
            width: 12px;
            height: 3px;
            position: absolute;
            bottom: 0;
            left: 50%;
            margin-left: -6px;
          }
          i {
            font-size: 12px;
            padding-left: 2px;
            margin-left: -4px;
          }
        }
      }
      .nav-icon {
        font-size: 12px;
        cursor: pointer;
      }
    }
    .nav-child-box {
      padding: 10px 12px;
      .child-item {
        font-size: 12px;
        line-height: 16px;
        cursor: pointer;
        margin-right: 16px;
      }
    }
    .nav-search {
      .el-input__inner {
        font-size: 12px;
        width: 100%;
        height: 36px;
        line-height: 36px;
        border-radius: 0;
        border-width: 0 0 1px 0;
      }
    }
  }
  .exchange-full-markets-content {
    padding-left: 10px;
    font-size: 12px;
    .el-table {
      font-size: 12px;
      .el-table__header-wrapper {
        th {
          padding: 0;
          height: 34px;
          line-height: 34px;
          font-weight: 500;
        }
      }
      .el-table__body-wrapper {
        td {
          padding: 5px 0 !important;
        }
        tr {
          cursor: pointer;
        }
      }
    }
    .table-coin-header {
      font-size: 12px;
      cursor: pointer;
      i {
        margin: 0 4px;
      }
    }
    .table-coin-text {
      text-align: center;
      i {
        font-size: 12px;
        margin-right: 4px;
      }
      span {
        line-height: 16px;
      }
      .times {
        border: 1px solid;
        padding: 0 2px;
        box-sizing: border-box;
        border-radius: 4px;
        margin-left: 4px;
        display: inline-flex;
        font-weight: 500;
        line-height: 14px;
      }
    }
  }
}
.exchange-full-comparison-prop {
  .token-desc {
    padding: 16px;
    width: 552px;
    .exchange-tabs {
      width: 100% !important;
      border-radius: 2px !important;
      padding: 2px !important;
      display: flex;
      align-items: center;
      margin-bottom: 16px;
      .box-tabs-item {
        cursor: pointer;
        text-align: center;
        font-size: 14px !important;
        line-height: 18px !important;
        width: 50% !important;
        padding: 8px 16px !important;
        font-weight: 400 !important;
        border-radius: 2px !important;
      }
    }
    .price-diff-wrap {
      font-size: 12px;
      line-height: 16px;
      .price-diff-title {
        padding: 8px 0;
        border-top: 1px solid;
        border-bottom: 1px solid;
        align-items: flex-start;
        span {
          flex: 1;
          &:nth-of-type(3) {
            text-align: right;
          }
        }
      }
      .price-diff-item {
        padding: 8px 0;
        border-bottom: 1px solid;
        display: flex;
        align-items: flex-start;
        span {
          flex: 1;
          &:nth-of-type(3) {
            text-align: right;
          }
        }
      }
    }
    .coni-introduce {
      .title {
        display: flex;
        align-items: center;
        img {
          width: 20px;
          height: 20px;
        }
        div {
          font-size: 20px;
          line-height: 24px;
          padding: 0 4px;
        }
        span {
          font-size: 14px;
          line-height: 20px;
        }
      }
      .info {
        .info-item {
          display: flex;
          align-items: center;
          padding: 8px 0;
          border-bottom: 1px solid;
          .item-content {
            flex: 1;
            .tip {
              line-height: 16px;
              font-size: 12px;
            }
            .text {
              font-size: 16px;
            }
          }
        }
      }
      .brief {
        div {
          font-size: 12px;
          line-height: 16px;
          margin: 16px 0 8px;
        }
        p {
          margin-bottom: 15px;
          font-size: 14px;
          line-height: 20px;
        }
      }
      .more {
        font-size: 12px;
        line-height: 16px;
        cursor: pointer;
      }
    }
  }
}
.exchange-type-nav-pop {
  padding: 12px !important;
  .exchange-type-nav-box {
    .nav-item {
      padding: 12px;
      font-weight: 400;
      font-size: 18px;
      line-height: 28px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: space-between;
      i {
        display: none;
      }
      &:hover {
        i {
          display: block;
        }
      }
    }
  }
}
</style>
<style lang="scss" scoped>
.top-menu {
  display: flex;
  .top-logo {
    cursor: pointer;
    height: 45px;
    img {
      width: 180px;
    }
  }
  .menu-list {
    padding: 0 16px;
    display: flex;
    align-items: center;
    .menu-item-box {
      display: flex;
      align-items: center;
    }
    .menu-item {
      margin-right: 24px;
      font-size: 14px;
      .item-title {
        cursor: pointer;
        i {
          margin-left: 2px;
        }
      }
    }
  }
  .exchange-full-box {
    display: flex;
    align-items: center;
    .top-logo {
      display: flex;
      flex-direction: column;
      margin-right: 15px;
      img {
        width: 94px;
      }
    }
    .top-text {
      text-align: center;
      position: relative;
      display: inline-block;
      font-weight: 500;
      font-size: 12px;
      line-height: 15px;
      margin-top: 3px;
    }
    .screen-box {
      .screen-box-top {
        align-items: center;
      }
      .coin-img {
        width: 20px;
        height: 20px;
        margin-right: 4px;
      }
      .screen-symbol {
        .coin-list {
          line-height: 20px;
          cursor: pointer;
          display: flex;
          align-items: center;
          i {
            font-size: 12px;
            padding-left: 2px;
          }
        }
        .coin-title {
          font-size: 15px;
        }
      }
      .screen-contrast {
        font-size: 12px;
        text-align: center;
        .contrast-text {
          cursor: pointer;
          span {
            padding-left: 4px;
          }
        }
      }
    }
    .box-line {
      width: 1px;
      height: 20px;
      margin: 0 16px;
    }
    .exchange-data-box {
      display: flex;
      align-items: center;
      .box-item {
        margin-right: 16px;
        .tip {
          line-height: 16px;
          font-size: 12px;
          margin-right: 4px;
        }
        .text {
          font-size: 12px;
          line-height: 16px;
          margin-right: 4px;
          margin-top: 4px;
          display: inline-block;
          &.border-bottom {
            border-bottom: 1px dashed;
            cursor: pointer;
          }
        }
      }
    }
  }
}
</style>
