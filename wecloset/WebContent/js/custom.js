/**
 * 
 */

jQuery.fn.serializeObject = function() {
	var obj = null;
	try {
		if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			var arr = this.serializeArray();
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}
		}
	} catch (e) {
		alert(e.message);
	} finally { }
	return obj;
}

// url 에서 parameter 추출
function getParam(sname) {
	var params = location.search.substr(location.search.indexOf("?") + 1);
	var sval = "";
	params = params.split("&");
	for (var i = 0; i < params.length; i++) {
		temp = params[i].split("=");
		if ([temp[0]] == sname) { sval = temp[1]; }
	}
	return sval;
}

// url 에서 parameter json 
function getUrlParams() {
	var params = {};
	window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
	return params;
}


/* 업로드 체크 */
function fileSizeCheck(upFile){
    if(document.getElementById(upFile).value!=""){	
	    var fileSize = document.getElementById(upFile).files[0].size;
	    var maxSize =10 * 1024 * 1024;//2MB
	 
	    if(fileSize > maxSize){
	       alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다. ");
	       return;
	    }    
	}
}


