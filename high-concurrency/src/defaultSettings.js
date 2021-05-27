/**
 * storageOptions: {} - Vue-ls 插件配置项 (localStorage/sessionStorage)
 */
export default {
    storageOptions: {
        namespace: 'pro__', // key prefix
        name: 'ls', // name variable Vue.[ls] or this.[$ls],
        storage: 'local', // storage name session, local, memory
      }
}