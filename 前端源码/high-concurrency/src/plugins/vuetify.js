import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import 'vuetify/dist/vuetify.min.css'
import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify)

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: colors.pink.lighten2,
        secondary: colors.pink.lighten3,
        accent: colors.teal.lighten2,
        anchor: colors.red.lighten2
      },
      dark: {
        primary: colors.cyan.darken5,
        secondary: colors.cyan.darken4,
        accent: colors.cyan.lighten3
      }
    }
  }
})
