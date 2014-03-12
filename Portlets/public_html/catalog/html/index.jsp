<%@ page contentType = "text/html; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, adfmodel.portlet.CatalogPortlet"%>
<%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>
<h1>QWERT CATALOG</h1>
<p class="portlet-font">Welcome, this is the ${renderRequest.portletMode} mode.</p>
qq=<%=renderRequest.getParameter("location") %>
<br>
<a href="<portlet:actionURL name="addBookAction"/>">Test action</a>
