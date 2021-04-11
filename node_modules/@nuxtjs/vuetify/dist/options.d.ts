import { SFCDescriptor } from 'vue-template-compiler';
import { VuetifyPreset } from 'vuetify/types/services/presets';
import { ModuleThis } from '@nuxt/types/config/module';
import { FontOptions } from './font';
import { IconPreset } from './icons';
export interface TreeShakeOptions {
    components?: string[];
    directives?: string[];
    loaderOptions?: VuetifyLoaderOptions;
    transitions?: string[];
}
export interface VuetifyLoaderOptions {
    match?(originalTag: string, context: {
        kebabTag: string;
        camelTag: string;
        path: string;
        component: SFCDescriptor;
    }): [string, string] | undefined;
}
export interface Options extends Partial<VuetifyPreset> {
    customVariables?: string[];
    defaultAssets?: {
        font?: FontOptions;
        icons?: IconPreset | false;
    } | false;
    optionsPath?: string;
    preset?: string;
    treeShake?: boolean | TreeShakeOptions;
}
export declare const defaults: {
    customVariables: never[];
    defaultAssets: {
        font: {
            family: string;
        };
        icons: "mdi" | "md" | "fa" | "fa4";
    };
    optionsPath: undefined;
    treeShake: boolean;
};
export default function initOptions(this: ModuleThis, moduleOptions?: Options): Required<Options>;
