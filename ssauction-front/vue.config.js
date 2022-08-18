const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  publicPath: process.env.NODE_ENV === 'production'
    ? '/production-sub-path/'
    : '/',
  // devServer: {
  //   port: 8083,
  //   proxy: "http://localhost:8080",
  // },
  transpileDependencies: true,
  // lintOnSave: false,
});
