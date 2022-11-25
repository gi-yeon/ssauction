const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  publicPath: "",
  // devServer: {
  //   port: 삭제,
  //   proxy: "로컬 주소 삭제",
  // },
  transpileDependencies: true,
  lintOnSave: false,
});
