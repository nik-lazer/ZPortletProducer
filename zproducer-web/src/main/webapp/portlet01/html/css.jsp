<%@ page contentType = "text/css; charset=UTF-8"
         pageEncoding = "UTF-8"
         import = "javax.portlet.*, java.util.*, lan.test.portlet.simple.Portlet01, lan.test.portlet.resource.Portlet01Bundle"%>
        <%@ taglib uri = "http://java.sun.com/portlet_2_0" prefix="portlet"%>

#target {
    background-image: url('wlp_rewrite?/portlet01/img/1.jpg/wlp_rewrite');
    background-color: red;
    margin: 0px;
}

#target1 {
    /*background-image: url(<%=response.encodeURL(request.getContextPath()+"/portlet01/img/1.jpg")%>));*/
    background-color: blue;
}
