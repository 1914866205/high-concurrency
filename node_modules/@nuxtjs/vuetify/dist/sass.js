import dartSass from 'sass';
export default function setupSass(customVariables) {
    const { sass, scss } = this.options.build.loaders;
    // Use Dart Sass
    sass.implementation = scss.implementation = dartSass;
    // Ensure compatibility with Nuxt < 2.10 (i.e. before https://github.com/nuxt/nuxt.js/pull/6460)
    if (!sass.sassOptions) {
        delete sass.indentedSyntax;
        sass.sassOptions = {
            indentedSyntax: true
        };
    }
    // Custom variables
    if (customVariables && customVariables.length > 0) {
        const sassImports = customVariables.map(path => `@import '${path}'`).join('\n');
        sass.additionalData = [sass.additionalData, sassImports].join('\n');
        const scssImports = customVariables.map(path => `@import '${path}';`).join('\n');
        scss.additionalData = [scss.additionalData, scssImports].join('\n');
    }
}
//# sourceMappingURL=sass.js.map