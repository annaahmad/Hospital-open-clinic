<%@include file="/dashboards/includes/dashboard.jsp"%>

<%
	String prefix="recentlengthofstay";
%>
<table width='100%'>
	<tr>
		<td style='text-align: center'>
			<%=writeDashboardText(prefix+"text","<img height='14px' src='"+sCONTEXTPATH+"/_img/themes/default/ajax-loader.gif'/>") %><br>
		</td>
	</tr>
</table>

<script>
	var <%=prefix%>timer=window.setInterval('<%=prefix%>getValue()',1000);

	function <%=prefix%>getValue(){
  		//*****************************************
  		//Pass parameters here
  		//*****************************************
	    var params = "";
	    var url = '<c:url value="/dashboards/"/><%=prefix%>/getValue.jsp?ts='+new Date().getTime();
	    new Ajax.Request(url,{
	      	method: "GET",
	      	parameters: params,
	      	onSuccess: function(resp){
	      		//*****************************************
	      		//Do something with the obtained value here
	      		//*****************************************
	            var result = eval('('+resp.responseText+')');
	            <%=prefix%>text.innerHTML="<%=getTranNoLink("web","lastmonthlengthofstay",sWebLanguage)%>:<br/>"+result.los+" <%=getTranNoLink("web","days",sWebLanguage)%>";
	      		//*****************************************
	      		//Update value after some time
	      		//*****************************************
	      		window.clearInterval(<%=prefix%>timer);
	      	}
	    });
	}

</script>