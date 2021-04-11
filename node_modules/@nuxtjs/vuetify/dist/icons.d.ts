import { ModuleThis } from '@nuxt/types/config/module';
declare const presetsCDN: {
    mdi: string;
    md: string;
    fa: string;
    fa4: string;
};
export declare type IconPreset = keyof typeof presetsCDN;
export default function setupIcons(this: ModuleThis, preset: IconPreset): void;
export {};
