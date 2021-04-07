<template>
  <div class="container">
    <div class="popup">
      <header class="popup-header">
        <slot name="header">
          this is default header
          <button
          type="button"
          class="btn-close"
          @click="close">x</button>
        </slot>
      </header>
      <section class="popup-body">
        <slot name="body">
          I am the default body
        </slot>
      </section>
      <footer class="popup-footer">
        <slot name="footer">
          I am the default footer
          <button
          type="button"
          class="btn-green"
          @click="close">close me!
          </button>
        </slot>
      </footer>
    </div>
  </div>
</template>

<script>
export default {
  name: 'popup',
  methods:{
    close (){
      this.$emit('close')
    }
  }

}
</script>

<style>
  .container {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .popup {
    background: #FFFFFF;
    box-shadow: 2px 2px 20px 1px;
    overflow-x: auto;
    display: flex;
    flex-direction: column;
  }
  .popup-header, .popup-footer{
    padding: 15px;
    display: flex;
  }
  .popup-header {
    border-bottom: 1px solid #eeeeee;
    color: #4AAE9B;
    justify-content: space-between;
  }
  .popup-footer {
    border-top: 1px solid #eeeeee;
    justify-content: flex-end;
  }
  .popup-body {
    position: relative;
    padding: 20px 10px;
  }
  .btn-close {
    border: none;
    font-size: 20px;
    padding: 20px;
    cursor: pointer;
    font-weight: bold;
    color: #4AAE9B;
    background: transparent;
  }
  .btn-green {
    color: white;
    background: #4AAE9B;
    border: 1px solid #4AAE9B;
    border-radius: 2px;
  }
</style>