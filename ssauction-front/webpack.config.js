module.exports = {
    module: {
      rules: [
        {
          test: /\.styl$/,      // 이 정규식에 해당하는 파일이 아래의 loader에 따라 처리된다.
          use: [
            {
              loader: "style-loader", // creates style nodes from JS strings
            },
            {
              loader: "css-loader", // translates CSS into CommonJS
            },
            {
              loader: "stylus-loader", // compiles Stylus to CSS
            },
          ],
        },
      ],
    },
  };
