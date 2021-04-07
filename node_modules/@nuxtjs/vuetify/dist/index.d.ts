import { Module } from '@nuxt/types';
import { Framework } from 'vuetify';
import { Options, TreeShakeOptions, VuetifyLoaderOptions } from './options';
declare module '@nuxt/types' {
    interface Configuration {
        vuetify?: Options;
    }
    interface Context {
        $vuetify: Framework;
    }
}
declare const vuetifyModule: Module<Options>;
export { Options, TreeShakeOptions, VuetifyLoaderOptions };
export default vuetifyModule;
