!function(e){function t(r){if(n[r])return n[r].exports;var i=n[r]={exports:{},id:r,loaded:!1};return e[r].call(i.exports,i,i.exports,t),i.loaded=!0,i.exports}var n={};return t.m=e,t.c=n,t.p="",t(0)}([function(e,t,n){"use strict";function r(e){return e&&e.__esModule?e:{"default":e}}var i=n(1),s=r(i),o=n(2),u=r(o);!function(){$.fn.timer=function(e){return e=e||"start",this.each(function(){$.data(this,u["default"].PLUGIN_NAME)instanceof s["default"]||$.data(this,u["default"].PLUGIN_NAME,new s["default"](this,e));var t=$.data(this,u["default"].PLUGIN_NAME);"string"==typeof e?"function"==typeof t[e]&&t[e]():t.start()})}}()},function(e,t,n){"use strict";function r(e){return e&&e.__esModule?e:{"default":e}}function i(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}Object.defineProperty(t,"__esModule",{value:!0});var s=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),o=n(2),u=r(o),a=n(3),f=r(a),l=function(){function e(t,n){if(i(this,e),this.element=t,this.originalConfig=$.extend({},n),this.totalSeconds=0,this.intervalId=null,this.html="html","INPUT"!==t.tagName&&"TEXTAREA"!==t.tagName||(this.html="val"),this.config=f["default"].getDefaultConfig(),n.duration&&(n.duration=f["default"].durationTimeToSeconds(n.duration)),"string"!=typeof n&&(this.config=$.extend(this.config,n)),this.config.seconds&&(this.totalSeconds=this.config.seconds),this.config.editable&&f["default"].makeEditable(this),this.startTime=f["default"].unixSeconds()-this.totalSeconds,this.config.duration&&this.config.repeat&&this.config.updateFrequency<1e3&&(this.config.updateFrequency=1e3),this.config.countdown){if(!this.config.duration)throw new Error("Countdown option set without duration!");if(this.config.editable)throw new Error("Cannot set editable on a countdown timer!");this.config.startTime=f["default"].unixSeconds()-this.config.duration,this.totalSeconds=this.config.duration}}return s(e,[{key:"start",value:function(){this.state!==u["default"].TIMER_RUNNING&&(f["default"].setState(this,u["default"].TIMER_RUNNING),this.render(),this.intervalId=setInterval(f["default"].intervalHandler.bind(null,this),this.config.updateFrequency))}},{key:"pause",value:function(){this.state===u["default"].TIMER_RUNNING&&(f["default"].setState(this,u["default"].TIMER_PAUSED),clearInterval(this.intervalId))}},{key:"resume",value:function(){this.state===u["default"].TIMER_PAUSED&&(f["default"].setState(this,u["default"].TIMER_RUNNING),this.config.countdown?this.startTime=f["default"].unixSeconds()-this.config.duration+this.totalSeconds:this.startTime=f["default"].unixSeconds()-this.totalSeconds,this.intervalId=setInterval(f["default"].intervalHandler.bind(null,this),this.config.updateFrequency))}},{key:"remove",value:function(){clearInterval(this.intervalId),$(this.element).data(u["default"].PLUGIN_NAME,null)}},{key:"reset",value:function(){var e=this.element,t=this.originalConfig;this.remove(),$(e).timer(t)}},{key:"render",value:function(){this.config.format?$(this.element)[this.html](f["default"].secondsToFormattedTime(this.totalSeconds,this.config.format)):$(this.element)[this.html](f["default"].secondsToPrettyTime(this.totalSeconds)),$(this.element).data("seconds",this.totalSeconds)}}]),e}();t["default"]=l},function(e,t){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n={PLUGIN_NAME:"timer",TIMER_RUNNING:"running",TIMER_PAUSED:"paused",DAYINSECONDS:86400,THIRTYSIXHUNDRED:3600,SIXTY:60,TEN:10};t["default"]=n},function(e,t,n){"use strict";function r(e){return e&&e.__esModule?e:{"default":e}}Object.defineProperty(t,"__esModule",{value:!0});var i=n(2),s=r(i),o=function(){var e=arguments.length<=0||void 0===arguments[0]?0:arguments[0],t=0,n=0,r=Math.floor(e/s["default"].SIXTY),i=r,o=void 0;return e>=s["default"].DAYINSECONDS&&(t=Math.floor(e/s["default"].DAYINSECONDS)),e>=s["default"].THIRTYSIXHUNDRED&&(n=Math.floor(e%s["default"].DAYINSECONDS/s["default"].THIRTYSIXHUNDRED)),e>=s["default"].SIXTY&&(i=Math.floor(e%s["default"].THIRTYSIXHUNDRED/s["default"].SIXTY)),o=e%s["default"].SIXTY,{days:t,hours:n,minutes:i,totalMinutes:r,seconds:o,totalSeconds:e}},u=function(e){return e=parseInt(e,10),e<10?"0"+e:e},a=function(){return{seconds:0,editable:!1,duration:null,callback:function(){console.log("Time up!")},repeat:!1,countdown:!1,format:null,updateFrequency:500}},f=function(){return Math.round(Date.now()/1e3)},l=function(e){var t=o(e);if(t.days)return t.days+":"+u(t.hours)+":"+u(t.minutes)+":"+u(t.seconds);if(t.hours)return t.hours+":"+u(t.minutes)+":"+u(t.seconds);var n="";return n=t.minutes?t.minutes+":"+u(t.seconds)+" min":t.seconds+" sec"},c=function(e,t){var n=o(e),r=[{identifier:"%d",value:n.days},{identifier:"%h",value:n.hours},{identifier:"%m",value:n.minutes},{identifier:"%s",value:n.seconds},{identifier:"%g",value:n.totalMinutes},{identifier:"%t",value:n.totalSeconds},{identifier:"%D",value:u(n.days)},{identifier:"%H",value:u(n.hours)},{identifier:"%M",value:u(n.minutes)},{identifier:"%S",value:u(n.seconds)},{identifier:"%G",value:u(n.totalMinutes)},{identifier:"%T",value:u(n.totalSeconds)}];return r.forEach(function(e){t=t.replace(e.identifier,e.value)}),t},h=function(e){if(!e)throw new Error("durationTimeToSeconds expects a string argument!");if(!isNaN(Number(e)))return e;e=e.toLowerCase();var t=e.match(/\d{1,2}d/),n=e.match(/\d{1,2}h/),r=e.match(/\d{1,2}m/),i=e.match(/\d{1,2}s/);if(!(t||n||r||i))throw new Error("Invalid string passed in durationTimeToSeconds!");var o=0;return t&&(o+=Number(t[0].replace("d","")*s["default"].DAYINSECONDS)),n&&(o+=Number(n[0].replace("h","")*s["default"].THIRTYSIXHUNDRED)),r&&(o+=Number(r[0].replace("m",""))*s["default"].SIXTY),i&&(o+=Number(i[0].replace("s",""))),o},p=function(e){var t=void 0,n=void 0;return e.indexOf("sec")>0?n=Number(e.replace(/\ssec/g,"")):e.indexOf("min")>0?(e=e.replace(/\smin/g,""),t=e.split(":"),n=Number(t[0]*s["default"].SIXTY)+Number(t[1])):e.match(/\d{1,2}:\d{2}:\d{2}:\d{2}/)?(t=e.split(":"),n=Number(t[0]*s["default"].DAYINSECONDS)+Number(t[1]*s["default"].THIRTYSIXHUNDRED)+Number(t[2]*s["default"].SIXTY)+Number(t[3])):e.match(/\d{1,2}:\d{2}:\d{2}/)&&(t=e.split(":"),n=Number(t[0]*s["default"].THIRTYSIXHUNDRED)+Number(t[1]*s["default"].SIXTY)+Number(t[2])),n},d=function(e,t){e.state=t,$(e.element).data("state",t)},v=function(e){$(e.element).on("focus",function(){e.pause()}),$(e.element).on("blur",function(){e.totalSeconds=p($(e.element)[e.html]()),e.resume()})},m=function(e){return e.totalSeconds=f()-e.startTime,e.config.countdown?(e.totalSeconds=e.config.duration-e.totalSeconds,0===e.totalSeconds&&(clearInterval(e.intervalId),d(e,s["default"].TIMER_STOPPED),e.config.callback(),$(e.element).data("seconds")),void e.render()):(e.render(),void (e.config.duration&&e.totalSeconds>0&&e.totalSeconds%e.config.duration===0&&(e.config.callback&&e.config.callback(),e.config.repeat||(clearInterval(e.intervalId),d(e,s["default"].TIMER_STOPPED),e.config.duration=null))))};t["default"]={getDefaultConfig:a,unixSeconds:f,secondsToPrettyTime:l,secondsToFormattedTime:c,durationTimeToSeconds:h,prettyTimeToSeconds:p,setState:d,makeEditable:v,intervalHandler:m}}])