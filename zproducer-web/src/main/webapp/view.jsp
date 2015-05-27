<%@ page contentType = "text/html; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, lan.test.portlet.simple.InitPortlet"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>
<h1>Test init portlet ant deploy</h1>
<p class="portlet-font">Welcome, this is the ${renderRequest.portletMode} mode.</p>
qq=<%=renderRequest.getParameter("docTypeName") %>
<br>
<% PortletURL zkrURL = renderResponse.createActionURL();zkrURL.setParameter("name", "ZKR"); %>
<% PortletURL raspURL = renderResponse.createActionURL();raspURL.setParameter("name", "RASHODNOE_RASPISANIE"); %>
<% PortletURL moneyURL = renderResponse.createActionURL();moneyURL.setParameter("name", "MONEYORDER"); %>
<a href="<%=zkrURL%>">ZKR</a><br>
<a href="<%=raspURL%>">RASHODNOE_RASPISANIE</a><br>
<a href="<%=moneyURL%>">MONEYORDER</a><br>
