//防止多次点击重复请求
export default {
    install (Vue) {
      // 防止重复点击
      Vue.directive('preventReClick', {
        inserted (el, binding) {
           console.log("binding-7",binding)
           el.addEventListener('click', () => {
             if (!el.disabled) {
               el.disabled = true
               setTimeout(() => {
                 el.disabled = false
               },binding.value || 5000)
             }
           })
         }
       })
    }
  }