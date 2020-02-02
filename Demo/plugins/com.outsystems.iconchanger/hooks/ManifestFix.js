var fs = require('fs'), path = require('path');
var parseString = require('./xml2js').parseString;
var xml2js = require('./xml2js');

module.exports = function (context) {
	
	var manifestFolder = path.join(context.opts.projectRoot, 'platforms/android/app/src/main');
	var manifestFile = path.join(manifestFolder, 'AndroidManifest.xml');

	function remove(obj, key) {
	    for (var k in obj) {
	        if (k == key) {
	            delete obj[key];
	            return true;
	        } else if (typeof obj[k] === "object") {
	            if (remove(obj[k], key)) return true;
	        }
	    }
	    return false;
	}
	
	if (fs.existsSync(manifestFile)) {

	    fs.readFile(manifestFile, 'utf8', function (err, data) {

	        if (err) {
	            throw new Error('Unable to find AndroidManifest.xml: ' + err);
	        }
	        parseString(data, function (err, result) {

			  remove(result,"category");
			  var builder = new xml2js.Builder();
			  
			  var xml = builder.buildObject(result);

			  fs.writeFile(manifestFile, xml, 'utf8', function (err) {
	            if (err) throw new Error('Unable to write into AndroidManifest.xml: ' + err);
	          });

			}); 
	    });
	} else {
	    throw new Error("Coudn't find AndroidManifest.xml ");
	}
}