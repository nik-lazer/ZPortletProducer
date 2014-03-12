<%@ page contentType = "text/javascript; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, adfmodel.portlet.Portlet01, adfmodel.portlet.resource.Portlet01Bundle"%>
<%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>

var url = '';
function test(val) {
    url = val;
}

test('<%=response.encodeURL(request.getContextPath()+"/portlet01/html/test.html")%>');
jQuery(function() {
    $("#target").load(url, function() {
        $("#result").html("Success");
    });
});
