module.exports = {
  devServer: {
    host: "localhost",
    port: '8080',
    proxy: {
      '/': {
        target: 'http://localhost:8082', //API服务器的地址
        changeOrigin: true, // 虚拟的站点需要更管origin
        ws: false,  //代理websockets
        pathRewrite: {   //重写路径 比如'/api/aaa/ccc'重写为'/aaa/ccc'
          '^/': ''
        }
      }
      // ,
      // '/ws/*':{
      //   target: 'ws://127.0.0.1:8082',
      // }
    }

  },
  // 设置路径
  publicPath: '/',
  assetsDir: 'static'
}
