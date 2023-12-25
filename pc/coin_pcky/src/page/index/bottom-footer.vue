<template>
  <div class="newbottom-footer">
    <div class="footer-wrap">
      <div class="wrap-content">
        <div class="title-item" v-for="(item,index) in textData" :key="index">
          <div class="title">{{currCoinLangFun(item.title_code)}}</div>
          <div class="child-list">
            <div
              class="child-item"
              v-for="(child,i) in item.child_list"
              :key="i"
              @click="textClickFun(child)"
            >{{currCoinLangFun(child.title_code)}}</div>
          </div>
        </div>
      </div>
      <div class="wrap-logo">
        <div class="logo-img">
          <img v-show="themeName == 'theme-night'" :src="logo.night" />
          <img v-show="themeName != 'theme-night'" :src="logo.daytime" />
        </div>
        <div class="logo-lang" @click="openSetLangFun">
          <span>{{languageName}}</span>
          <!-- <span>
            <img src=""/>
          </span>-->
        </div>
      </div>
    </div>
    <div class="footer-icon-box">
      <div class="box-content">
        <div class="icon-box">
          <div
            class="icon-item"
            v-for="(item,index) in iconData"
            :key="index"
            @click="openUrlFun(item.skip_path)"
          >
            <img :src="getImgUrlFun(item.img_code,'normal')" />
            <img :src="getImgUrlFun(item.img_code,'hover')" />
          </div>
        </div>
        <div class="icon-c" @click="openUrlFun(copyright.skip_path)">{{copyright.title}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { getStrDataFunction } from '@/util/util'
import { getDataApi } from '@/api/system'
import { mapGetters, mapMutations } from 'vuex'

export default {
  props: ['openSetLangFun'],
  data() {
    return {
      textData: [],
      iconData: [],
      copyright: {},
      isSkipArticle: false,
    }
  },
  computed: {
    ...mapGetters([
      'language',
      'languageName',
      'themeName',
      'logo',
      'allImgUrl',
    ]),
  },
  mounted() {
    getDataApi('1530464853989064705', {
      config_type: 'footer',
      config_code: 'bottom_content',
    }).then((res) => {
      let data = res.data.data.records[0]
      let config_data = getStrDataFunction(data.config_data)
      this.copyright = config_data.copyright
      this.textData = config_data.list_data
      this.iconData = config_data.icon_data

      console.log('底部导航配置', config_data)
    })
  },
  methods: {
    ...mapMutations(['SET_DEAL_TYPE', 'SET_CURR_COIN', 'SET_CONTRACT_TYPE']),
    openUrlFun(url) {
      if (url) {
        window.open(url, '_blank')
      }
    },
    getImgUrlFun(code, type) {
      if (type) {
        code = code[type]
      }
      return this.allImgUrl[code] ? this.allImgUrl[code] : ''
    },
    textClickFun(data) {
      if (data.skip_type == 'article' && data.article_code) {
        if (this.isSkipArticle) {
          return false
        }
        this.isSkipArticle = true
        getDataApi('1530431184536666113', {
          article_code: data.article_code,
          article_lang: this.language,
        })
          .then((res) => {
            this.isSkipArticle = false
            let data = res.data.data.records
            if (res.data.data.records.length > 0) {
              data = data[0]
              let routeData = this.$router.resolve({
                path: `/article/main/content/${data.article_type}/${data.id}`,
              })
              window.open(routeData.href, '_blank')
            }
          })
          .catch(() => {
            this.isSkipArticle = false
          })
      } else if (data.skip_type == 'page' && data.skip_path) {
        if (data.set_store && data.fun_key) {
          this[data.fun_key](data.set_store)
        }
        let routeData = this.$router.resolve({
          path: data.skip_path,
        })
        window.open(routeData.href, '_blank')
      }
    },
    currCoinLangFun(code, type = 'footer') {
      return this.coinLangFun(type, code)
    },
  },
}
</script>

<style lang="scss" scoped>
.newbottom-footer {
  width: 100%;
  background-color: #F1F4FE;
  color: #000000;
  .footer-wrap {
    width: 1240px;
    display: flex;
    justify-content: center;
    margin: 0 auto;
    padding: 80px 20px;
    box-sizing: border-box;
  }
}
.wrap-content {
  flex: 1;
  display: flex;
  grid-column-gap: 80px;
  .title-item {
    .title {
      font-size: 18px;
    }
    .child-list {
      margin-top: 30px;
      .child-item {
        font-size: 14px;
        margin-bottom: 12px;
        cursor: pointer;
        &:hover {
          color: #0f71c4;
        }
      }
    }
  }
}
.wrap-logo {
  .logo-img {
    img {
      width: 342px;
    }
  }
  .logo-lang {
    font-size: 16px;
    border: 1px solid #0f71c4;
    border-radius: 4px;
    width: 230px;
    text-align: center;
    padding: 18px 0;
    margin: 20px auto 0;
    cursor: pointer;
  }
}
.footer-icon-box {
  border-top: 1px solid #333333;
  padding: 24px 0;
  .box-content {
    width: 1240px;
    display: flex;
    justify-content: space-between;
    margin: 0 auto;
  }
  .icon-box {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    grid-column-gap: 12px;
    .icon-item {
      cursor: pointer;
      img {
        width: 24px;
        height: 24px;
        &:nth-child(1) {
          display: block;
        }
        &:nth-child(2) {
          display: none;
        }
      }
      &:hover {
        img {
          &:nth-child(1) {
            display: none;
          }
          &:nth-child(2) {
            display: block;
          }
        }
      }
    }
  }
  .icon-c {
    cursor: pointer;
  }
}
</style>
