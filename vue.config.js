module.exports = {
    devServer: {
        host: 'localhost',
        // 可以用域名访问 
        disableHostCheck: true,
        //修改成你要的端口      
        port: 8091,
        https: false,
    open: true,
    },
    outputDir: './dist',
  assetsDir: './assets/',
  publicPath: './',
  indexPath: './index.html',
  transpileDependencies: ['vuetify']
}