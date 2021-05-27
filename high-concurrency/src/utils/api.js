import axios from 'axios'

let contentUrl = 'http://localhost:8083';

const content = axios.create({
    baseURL:contentUrl,
    timeout: 9000 // 请求超时时间
  })
  
  const err = (error) => {
    if (error.response) {
      console.log("------异常响应------",token)
      console.log("------异常响应------",error.response.status)
      switch (error.response.status) {
        case 403:
  
          break
        case 500:
      
          break
        case 404:
            
          break
        case 504:
          break
        case 401:
      
          break
        default:
        
          break
      }
    }
    return Promise.reject(error)
  };
  //配置请求拦截
  content.interceptors.request.use(
    config => {
      //添加请求头
      return config
    },
    err => {
      return Promise.reject(error)
    }
  )
  //配置响应拦截
  content.interceptors.response.use((response) => {
    return response.data
  }, err)
  // content.interceptors.response.use(
  //   response => {
  //     return response.data
  //   },
  //   err => {
  //     const { response } =err
  //     if(response) {
  //       switch(response.status) {
  //         case 401:
  //           break
  //         case 404:
  //           break
  //       }
  //     }else {
  //       if(!window.navigator.onLine) {
  //         //如果没有返回结果
  //         return
  //       }
  //     }
  //     return Promise.reject(err)
  //   }
  // )
  export {
    content as axios
  }