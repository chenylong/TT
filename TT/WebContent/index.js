
function init()
{
	
	window.setTimeout(function(){
	
		/**所属地市
		jQuery.post({
	        url:EPM_BASEPATH+"workcardQuery!queryWorkcard.do?typeId=city_code",
	        success: loadCityCodeCombo,
	        error: mainPageError
	        
	    });	
		
		*/
		
	
		
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
	    
	},200);
}
function loadCityCodeCombo(data,ioArgs){

	
	var dd =strToJson(data);
	var div = "chartContainer";
	 charts(dd,div);
	
	
	
}

function mainPageError(data,ioArgs){
	
	alert("error")
}

function charts(data,div){
	alert(div)
	FusionCharts.ready(function(){
	    var revenueChart = new FusionCharts({
	      type: "column2d",
	      renderAt:div,
	      width: "1260",
	      height: "300",
	      dataFormat: "json",
	      dataSource: {
	       "chart": {
	          "caption": "Monthly revenue for last year",
	          "subCaption": "Harry's SuperMart",
	          "xAxisName": "首位数字",
	          "yAxisName": "出现的频率",
	          "theme": "zune"
	       },
	       "data":data
	      }
	 
	  });
	  revenueChart.render(div);
	}); 
	
	
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

function loadCityCodeCombo2(data,ioArgs){

	alert(data)
	
	var dd =strToJson(data);
	var div = "chartContainer2";
	 charts(dd,div);
	
	
	
}