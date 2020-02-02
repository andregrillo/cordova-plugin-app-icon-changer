cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "com.outsystems.iconchanger.AppIconChanger",
    "file": "plugins/com.outsystems.iconchanger/www/AppIconChanger.js",
    "pluginId": "com.outsystems.iconchanger",
    "clobbers": [
      "AppIconChanger"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.4",
  "cordova-custom-config": "5.1.0",
  "cordova-plugin-settings-hook": "0.2.7",
  "com.outsystems.iconchanger": "2.0.0"
};
// BOTTOM OF METADATA
});