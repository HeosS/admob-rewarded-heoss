var cordova = require('cordova');

function InMobi() {
	var self = this;

	self.init = function() {
		cordova.exec(function(){}, function(){}, "CDVInMobi", "init", []);
	};

	self.showAd = function() {
		cordova.exec(function(){}, function(){}, "CDVInMobi", "showAd", []);
	};
}

module.exports = new InMobi();
