//>>built
define("dojox/mvc/equals",["dojo/_base/array","dojo/_base/lang","dojo/Stateful","./StatefulArray"],function(h,e,g,k){var f=function(a,b,c){c=c||f;var d=[c.getType(a),c.getType(b)];return d[0]!=d[1]?!1:c["equals"+d[0].replace(/^[a-z]/,function(a){return a.toUpperCase()})](a,b)};return e.setObject("dojox.mvc.equals",e.mixin(f,{getType:function(a){return e.isArray(a)?"array":e.isFunction((a||{}).getTime)?"date":null!=a&&("[object Object]"=={}.toString.call(a)||e.isFunction((a||{}).set)&&e.isFunction((a||
{}).watch))?"object":"value"},equalsArray:function(a,b){for(var c=0,d=Math.max(a.length,b.length);c<d;c++)if(!f(a[c],b[c]))return!1;return!0},equalsDate:function(a,b){return a.getTime()==b.getTime()},equalsObject:function(a,b){var c=e.mixin({},a,b),d;for(d in c)if(!(d in g.prototype)&&"_watchCallbacks"!=d&&!f(a[d],b[d]))return!1;return!0},equalsValue:function(a,b){return a===b}}))});