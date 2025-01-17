const CompressionWebpackPlugin = require('compression-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin')
let optimization = {}
let css = {}
let plugins = []
if (process.env.NODE_ENV === 'production') {
  optimization.minimize = true
  optimization.minimizer = [new TerserPlugin({
    parallel: true,
    sourceMap: true,
    terserOptions: {
      warnings: false,
      compress: {
        drop_console: true,
        drop_debugger: true,
        pure_funcs: ["console.log"]
      }
    }
  })]
  css.extract = {
    ignoreOrder: true
  }
  plugins = [
    new CompressionWebpackPlugin({
      test: /.(js|css|svg|woff|ttf|json|html)$/,
      threshold: 10240
    })
  ]
}

module.exports = {
  publicPath: "./",
  lintOnSave: false,
  productionSourceMap: false,
  chainWebpack: (config) => {
    config.externals({
      'vue': 'Vue',
      'vue-router': 'VueRouter',
      'vuex': 'Vuex',
      'axios': 'axios',
      'element-ui': 'ELEMENT',
    });
    const entry = config.entry('app');
    entry.add('babel-polyfill').end();
    entry.add('classlist-polyfill').end();
  },
  css,
  configureWebpack: {
    optimization,
    plugins: [
      ...plugins,
    ]
  },
  devServer: {
    port: 1890,
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:9851',
        ws: true,
        pathRewrite: {
          '^/api': '/'
        }
      }
    },
    disableHostCheck: true
  }
};
