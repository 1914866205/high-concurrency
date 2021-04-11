import { ModuleThis } from '@nuxt/types/config/module';
export interface FontOptions {
    family?: string | string[];
    size?: number;
}
export default function setupFont(this: ModuleThis, options: FontOptions): void;
