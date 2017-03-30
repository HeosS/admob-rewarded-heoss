var cordova = require('cordova');

function InMobi() {
	var self = this;

	self.init = function() {
		cordova.exec(function(){}, function(){}, "inMobiPlugin", "init", []);
	};

	self.showAd = function() {
		cordova.exec(function(){}, function(){}, "inMobiPlugin", "showAd", []);
	};
}

module.exports = new InMobi();
