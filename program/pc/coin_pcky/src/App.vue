<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import Vue from "vue";
import { mapGetters, mapMutations } from "vuex";
import { getStrDataFunction } from "@/util/util";
import { getDataApi } from "@/api/system";

export default {
  computed: {
    ...mapGetters(["languageData", "language", "defaultLang"]),
  },
  created() {
    Vue.prototype.coinLangFun = (page, code, def = "zh_en") => {
      let codeText = page + "_" + code;
      if (
        this.languageData &&
        this.languageData[codeText] &&
        this.languageData[codeText][this.language]
      ) {
        return this.languageData[codeText][this.language];
      } else if (this.languageData && this.languageData[codeText]) {
        return this.languageData[codeText][
          this.defaultLang ? this.defaultLang : def
        ];
      } else {
        return "";
      }
    };
    Vue.prototype.dataCoinLangFun = (code, data, def = "zh_en") => {
      if (data) {
        if (code) {
          if (typeof data[code] == "string") {
            try {
              data[code] = getStrDataFunction(data[code]);
            } catch (error) {
              data = {};
            }
          }
          return data[code]
            ? data[code][this.language]
              ? data[code][this.language]
              : data[code][this.defaultLang ? this.defaultLang : def]
            : "";
        } else {
          return data[this.language]
            ? data[this.language]
            : data[this.defaultLang ? this.defaultLang : def];
        }
      }
    };
    Vue.prototype.reloadViewFun = () => {
      this.SET_IS_ROUTER_ALIVE(false);
      this.$nextTick(() => {
        this.SET_IS_ROUTER_ALIVE(true);
      });
    };
    getDataApi("1530464853989064705", {
      config_code: "empty_lang",
    }).then((res) => {
      let data = res.data.data.records[0];
      let defaultLang = "";
      try {
        defaultLang = getStrDataFunction(data.config_data);
      } catch (error) {
        defaultLang = "zh_en";
      }
      this.SET_DEFAULT_LANG(defaultLang);
    });
  },
  methods: {
    ...mapMutations(["SET_DEFAULT_LANG", "SET_IS_ROUTER_ALIVE"]),
  },
};
</script>
<style lang="scss">
@font-face {
  font-family: "DINPro";
  src: url("/fonts/DINPro-Medium.otf");
}
#app {
  font-family: DINPro, "SF Pro SC", "SF Pro Text", "Helvetica Neue", "Helvetica",
    "Arial", "PingFang SC", "Microsoft YaHei", "微软雅黑", sans-serif;
}
.theme-night {
  #app {
    color: #dde3e6;
  }
}
.theme-daytime {
  #app {
    color: #10171f;
  }
}
</style>
