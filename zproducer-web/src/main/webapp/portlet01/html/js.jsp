<%@ page contentType = "text/javascript; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, lan.test.portlet.simple.Portlet01, lan.test.portlet.resource.Portlet01Bundle"%>
<%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>

var url = '';
function lan.test.test(val) {
    url = val;
}

lan.test.test('<%=response.encodeURL(request.getContextPath()+"/portlet01/html/lan.test.html")%>');
jQuery(function() {
    $("#target").load(url, function() {
        $("#result").html("Success");
    });
});
