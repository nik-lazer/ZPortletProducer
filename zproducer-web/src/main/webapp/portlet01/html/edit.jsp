<%@ page contentType = "text/html; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, lan.test.portlet.simple.Portlet01, lan.test.portlet.resource.Portlet01Bundle"%>
<%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects/>
<%
    PortletPreferences prefs = renderRequest.getPreferences();
    ResourceBundle res =
        portletConfig.getResourceBundle(renderRequest.getLocale());
%>

<form action="<portlet:actionURL/>" method="POST">
  <table border="0">
    <tr>
      <td width="20%">
        <p class="portlet-form-field" align="right">
          <%=  res.getString(Portlet01Bundle.PORTLETTITLE) %>
        </p>
      </td>
      <td width="80%">
        <input class="portlet-form-input-field"
               type="TEXT"
               name="<%= Portlet01.PORTLETTITLE_KEY %>"
               value="<%= prefs.getValue(Portlet01.PORTLETTITLE_KEY, res.getString("javax.portlet.title")) %>"
               size="20">
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input class="portlet-form-button" type="submit"
                                             name="<%=Portlet01.OK_ACTION%>"
                                             value="<%=res.getString(Portlet01Bundle.OK_LABEL)%>">
        <input class="portlet-form-button" type="submit"
                                             name="<%=Portlet01.APPLY_ACTION%>"
                                             value="<%=res.getString(Portlet01Bundle.APPLY_LABEL)%>">
      </td>
    </tr>
  </table>
</form>
