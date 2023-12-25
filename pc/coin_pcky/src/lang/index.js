import Vue from 'vue'
import VueI18n from 'vue-i18n'
import elementEnLocale from 'element-ui/lib/locale/lang/en' // element-ui lang
import elementZhCnLocale from 'element-ui/lib/locale/lang/zh-CN'// element-ui lang
import elementZhTwLocale from 'element-ui/lib/locale/lang/zh-TW'//
import { getStore } from '@/util/store'
Vue.use(VueI18n)
const Avue = window.AVUE;

//key 需要与后台数据字典的值对应上  coin_lang
const messages = {
  en: {
    ...elementEnLocale,
    ...Avue.locale.en,
  },
  zh_cn: {
    ...elementZhCnLocale,
    ...Avue.locale.zh,
  },
  zh_tw:{
    ...elementZhTwLocale,
    ...Avue.locale.tw,
  }
}

const i18n = new VueI18n({
  locale: getStore({ name: 'language' }) || 'zh',
  messages,
  silentTranslationWarn: true,
})

export default i18n