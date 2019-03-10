//大写
function convertCurrency(money) {
  //汉字的数字
  var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
  //基本单位
  var cnIntRadice = new Array('', '拾', '佰', '仟');
  //对应整数部分扩展单位
  var cnIntUnits = new Array('', '万', '亿', '兆');
  //对应小数部分单位
  var cnDecUnits = new Array('角', '分', '毫', '厘');
  //整数金额时后面跟的字符
  var cnInteger = '整';
  //整型完以后的单位
  var cnIntLast = '元';
  //最大处理的数字
  var maxNum = 999999999999999.9999;
  //金额整数部分
  var integerNum;
  //金额小数部分
  var decimalNum;
  //输出的中文金额字符串
  var chineseStr = '';
  //分离金额后用的数组，预定义
  var parts;
  if (money == '') { return ''; }
  money = parseFloat(money);
  if (money >= maxNum) {
    //超出最大处理数字
    return '';
  }
  if (money == 0) {
    chineseStr = cnNums[0] + cnIntLast + cnInteger;
    return chineseStr;
  }
  //转换为字符串
  money = money.toString();
  if (money.indexOf('.') == -1) {
    integerNum = money;
    decimalNum = '';
  } else {
    parts = money.split('.');
    integerNum = parts[0];
    decimalNum = parts[1].substr(0, 4);
  }
  //获取整型部分转换
  if (parseInt(integerNum, 10) > 0) {
    var zeroCount = 0;
    var IntLen = integerNum.length;
    for (var i = 0; i < IntLen; i++) {
      var n = integerNum.substr(i, 1);
      var p = IntLen - i - 1;
      var q = p / 4;
      var m = p % 4;
      if (n == '0') {
        zeroCount++;
      } else {
        if (zeroCount > 0) {
          chineseStr += cnNums[0];
        }
        //归零
        zeroCount = 0;
        chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
      }
      if (m == 0 && zeroCount < 4) {
        chineseStr += cnIntUnits[q];
      }
    }
    chineseStr += cnIntLast;
  }
  //小数部分
  if (decimalNum != '') {
    var decLen = decimalNum.length;
    for (var i = 0; i < decLen; i++) {
      var n = decimalNum.substr(i, 1);
      if (n != '0') {
        chineseStr += cnNums[Number(n)] + cnDecUnits[i];
      }
    }
  }
  if (chineseStr == '') {
    chineseStr += cnNums[0] + cnIntLast + cnInteger;
  } else if (decimalNum == '') {
    chineseStr += cnInteger;
  }
  return chineseStr;
}


//求和
var sum = function(name, toaltext){
	debugger
	var trr =  $("#" + name + "_table tbody tr"); 
	//先进行解析公式
	var strs = toaltext.split(" ");
	var list=[];
	var toal=0;
  //组装后的公式
	var str ="";
   for(var i = 0 ; i < trr.length-1 ; i++) {
  	 var ii = 0;
	     var k =  0;
	     if(trr[i].id=="first"){
	    	 k=0;
	     }else{
	    	ii = trr[i].id.indexOf(name+"id");
			k =  trr[i].id.substring(ii+2+name.length);
	     }
  	 for ( var x in strs) {
  			if(strs[x].trim()=="")continue;
  			if(/[\+\-\*\/]/.test(strs[x].trim())){
  				str +=strs[x].trim();
  			}
				if(!/[\+\-\*\/]/.test(strs[x].trim())){
  				str +=$("input[name='"+name+"["+k+"]["+strs[x].trim()+"]']").val();
  			}
  		}
  	 //得值
  	var netoal= eval(str);
  	list.push(netoal);
		$("input[name='"+name+"["+k+"][toaltext"+name+k+"]']").val(toDecimal2(netoal));
		str="";
   }
   for ( var x in list) {
  	 toal+=list[x];
	}
   $("input[name='toaltext"+name+"']").val(toDecimal2(toal));
   $("input[name='toaltext"+name+"uppercase']").val(convertCurrency(toal.toString()));
}
function fomatFloat(src,pos){   
	return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
} 

function toDecimal2(x) {       
	var f = parseFloat(x);      
	if (isNaN(f)) {         return false;       }       
	var f = Math.round(x*100)/100;       
	var s = f.toString();       
	var rs = s.indexOf('.');       
	if (rs < 0) {         rs = s.length;         s += '.';       }       
	while (s.length <= rs + 2) {         s += '0';       }       
	return s;     
	} 