
function init()
{
	alert("cookie")
jQuery.ajax({
		type: "POST",
		url: EPM_BASEPATH+"workcardQuery!getCookie.do",
		data: "",
		success: loadCityCodeCombo,
		dataType:""
	});
		
		
	
		
		var data =  [
			          {
				             "label": "Jan",
				             "value": "420000"
				          },
				          {
				             "label": "Feb",
				             "value": "810000"
				          },
				          {
				             "label": "Mar",
				             "value": "720000"
				          },
				          {
				             "label": "Apr",
				             "value": "550000"
				          },
				          {
				             "label": "May",
				             "value": "910000"
				          },
				          {
				             "label": "Jun",
				             "value": "510000"
				          },
				          {
				             "label": "Jul",
				             "value": "680000"
				          },
				          {
				             "label": "Aug",
				             "value": "620000"
				          },
				          {
				             "label": "Sep",
				             "value": "610000"
				          },
				          {
				             "label": "Oct",
				             "value": "490000"
				          },
				          {
				             "label": "Nov",
				             "value": "900000"
				          },
				          {
				             "label": "Dec",
				             "value": "730000"
				          }
				        ];
		
		// charts(data);
	    
	
}
function loadCityCodeCombo(data,ioArgs){

alert(data);

//document.getElementById("iframe1")

iframe1.document.open();
iframe1.document.write(data);
iframe1.document.close();
//frm.document.body.innerHTML='bbbb';
	
	
}

function mainPageError(data,ioArgs){
	
	alert("error")
}


function strToJson(str){ 
	return JSON.parse(str); 
}

function shownum(){
	
	var ids = $('#num').val();
	
	ids = 'num'+ids;
	//alert(ids);
	
	jQuery.ajax({
		type: "POST",
		url: EPM_BASEPATH+"workcardQuery!queryNumCountById.do?typeId="+ids,
		data: "",
		success: loadCityCodeCombo,
		dataType:""
	});
}


function showsql(){
	
	var sql = $("#sql").val();
	alert(sql)
	
	var jsonstr='[{"json":"'+sql+'"}]';
    var jsonarray = eval('('+jsonstr+')');
	
	jQuery.ajax({
		type: "POST",
		url: EPM_BASEPATH+"workcardQuery!queryDomeSql.do?json="+sql,
		data: "",
		success: loadCityCodeCombo2,
		dataType:""
	});
	
}

