<%@ page contentType = "text/html; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, adfmodel.portlet.RecentPortlet"%>
<%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>
<h1>QWERT RECENT</h1>
<p class="portlet-font">Welcome, this is the ${renderRequest.portletMode} mode.</p>
<div><%=renderRequest.getParameter("location") %></div>
<div><%=renderRequest.getParameter("category") %></div>
<div><%=renderRequest.getParameter("name") %></div>
<div><%=renderRequest.getParameter("author") %></div>