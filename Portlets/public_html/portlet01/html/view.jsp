<%@ page contentType = "text/html; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, adfmodel.portlet.Portlet01, adfmodel.portlet.resource.Portlet01Bundle"%>
<%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>
<link href="<%=renderResponse.encodeURL(renderRequest.getContextPath()+"/portlet01/html/css.jsp")%>" type="text/css" charset="UTF-8" rel="stylesheet">
<%
	request.setAttribute("oracle.portlet.server.useStatelessProxying", Boolean.TRUE);
	String jQueryUrl = response.encodeURL(request.getContextPath()+"/portlet01/js/jquery.js");
	String jsUrl = response.encodeURL(request.getContextPath()+"/portlet01/html/js.jsp");
	String imgUrl = response.encodeURL(request.getContextPath()+"/portlet01/img/1.jpg");
	String testUrl = response.encodeURL(request.getContextPath()+"/portlet01/html/test2.html");
	request.removeAttribute("oracle.portlet.server.useStatelessProxying");
%>
<script type="text/javascript" src="<%=jQueryUrl%>"></script>
<script type="text/javascript" src="<%=jsUrl%>"></script>
<style>
    .portlet-font {
        background-image: url("<%=imgUrl%>");
    }

</style>
<script type="text/javascript">
    var url2 = '';
    function test2(val) {
        url2 = val;
    }

    test2('<%=testUrl%>');
    jQuery(function() {
        $("#target2").load(url2, function() {
            $("#result2").html("Success2");
        });
    });

    var url3 = 'wsrp_rewrite?wsrp-urlType=blockingAction&wsrp-secureURL=false&wsrp-url=&wsrp-requiresRewrite=&wsrp-navigationalState=eJyLL07OL0i1zc1PSQ0GsVLU0nPykxJzbAF*5QmO&wsrp-navigationalValues=&wsrp-interactionState=&wsrp-mode=&wsrp-windowState=&wsrp-sessionID=z7TLRJTWXn0kLqZnn3RXjnKmxJcdSF1vdhDC1dhb9vzf9pNGndKn%21-1695916362%211364464502172&wsrp-extensions=oracle%253Aescape-xml%3Dfalse/wsrp_rewrite';
    console.log('url3:');
    console.log(url3);
</script>
<p class="portlet-font">Welcome, this is the ${renderRequest.portletMode} mode.</p>
<div id="target">
</div>
<div id="result"></div>
<div id="target2">
</div>
<div id="result2"></div>
<form action="<portlet:actionURL />" method="post">
    <h1>Please Login</h1>
    Login: <input type="text" name="login"><br>
    Password: <input type="password" name="password"><br>
    <input type=submit value="Login">
</form>

<portlet:actionURL var="patientProfileURL" name="editPatientProfile">
    <portlet:param name="myParam" value="myValue" />
</portlet:actionURL>
<% PortletURL q = renderResponse.createActionURL();q.setParameter("ok_action", "QWERTY"); %>
<a href="<%=q%>">QWERTY</a>
<p><a href="<%= patientProfileURL%>">Edit Profile</a></p>