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
              //  this.$message({
              //   message: "操作频繁，稍后请重试",
              //   type: "warning",
              // });
               setTimeout(() => {
                 el.disabled = false
               },binding.value || 5000)
             }
           })
         }
       })
    }
  }