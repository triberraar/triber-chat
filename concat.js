'use strict';

/*eslint-disable*/
var glob = require('glob');
var UglifyJS = require('uglify-js');
var fs = require('fs');


console.log('begining concat');
/*eslint-enable*/

var inputGlobs = ['src/main/resources/static/js/triber-chat/**/!(*_spec).js', 'src/main/resources/static/js/common/!(*_spec).js'];
var toMinifyFiles = [];

// glob files
inputGlobs.forEach(function(element) {
    toMinifyFiles = toMinifyFiles.concat(glob.sync(element, {}));
});

// minify files
var result = UglifyJS.minify(toMinifyFiles, {outSourceMap: 'triber-chat.min.js.map', mangle: false, compress: false});

// save result
if(!fs.existsSync('src/main/resources/static/js/dst')) {
    fs.mkdirSync('src/main/resources/static/js/dst');
}
fs.writeFile('src/main/resources/static/js/dst/triber-chat.min.js', result.code);
fs.writeFile('src/main/resources/static/js/dst/triber-chat.min.js.map', result.map.replace(/src\/main\/resources\/static/g, '../..'));

/*eslint-disable*/
console.log('done concat');
/*eslint-enable*/