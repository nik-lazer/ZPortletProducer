
package lan.test.portlet.simple;

import java.io.IOException;

import java.util.StringTokenizer;

import javax.portlet.*;

public class Portlet01 extends GenericPortlet {
    protected String getTitle(RenderRequest request) {
        // Get the customized title. This defaults to the declared title.
        PortletPreferences prefs = request.getPreferences();
        return prefs.getValue(PORTLETTITLE_KEY, super.getTitle(request));
    }

    protected void doDispatch(RenderRequest request,
                              RenderResponse response) throws PortletException,
                                                              IOException {
        // Do nothing if window state is minimized.
        WindowState state = request.getWindowState();
        if (state.equals(WindowState.MINIMIZED)) {
            super.doDispatch(request, response);
            return;
        }

        // Get the content type for the response.
        String contentType = request.getResponseContentType();

        // Get the requested portlet mode.
        PortletMode mode = request.getPortletMode();

        // Reference a request dispatcher for dispatching to web resources
        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = null;

        // Dispatch based on content type and portlet mode.
        PortletURL portletURL = response.createActionURL();
        String s = portletURL.toString();
        if (contentType.equals("text/html")) {
            if (mode.equals(PortletMode.VIEW)) {
                rd = context.getRequestDispatcher("/portlet01/html/view.jsp");
            } else if (mode.equals(PortletMode.EDIT)) {
                rd = context.getRequestDispatcher("/portlet01/html/edit.jsp");
            } else {
                super.doDispatch(request,response);
            }
        } else {
            super.doDispatch(request,response);
        }
        if (rd != null) {
            rd.include(request, response);
        } else {
            super.doDispatch(request, response);
        }
    }
    public static final String APPLY_ACTION = "apply_action";
    public static final String OK_ACTION = "ok_action";
    public static final String PORTLETTITLE_KEY = "portletTitle";

    private String[] buildValueArray(String values) {
        if (values.indexOf(',') < 0) {
            return new String[] {values};
        }
        StringTokenizer st = new StringTokenizer(values, ",");
        String[] valueArray = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            valueArray[i] = st.nextToken().trim();
            i++;
        }
        return valueArray;
    }

    public void processAction(ActionRequest request,
                              ActionResponse response) throws PortletException,
                                                              IOException {
        String okAction = request.getParameter(OK_ACTION);
        String applyAction = request.getParameter(APPLY_ACTION);

        if (okAction != null || applyAction != null) {
            // Save the preferences.
            PortletPreferences prefs = request.getPreferences();
            String param = request.getParameter(PORTLETTITLE_KEY);
            prefs.setValues(PORTLETTITLE_KEY, buildValueArray(param));
            prefs.store();
            if (okAction != null) {
                response.setPortletMode(PortletMode.VIEW);
                response.setWindowState(WindowState.NORMAL);
            }
        }
    }

    public void processEvent(EventRequest request, EventResponse response)
            throws PortletException, IOException {
        int i = 0;
        super.processEvent(request, response);
    }
/*
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
            throws PortletException, IOException {

        String resourceID = request.getResourceID();
        Locale locale = request.getLocale();
        if (resourceID.equals("invoice")) {
            String invoice = request.getParameter("invoice");
            if (invoice == null) {
                throw new PortletException(
                        "Required parameter, invoice, is missing.");
            } else {
                String path = "/html/" + invoice + ".html";
                String content = getContents(path, locale, true);
                PrintWriter writer = response.getWriter();
                writer.print(content);
            }
        } else if (resourceID.equals("js")) {
            String content = getContents(jsPage, locale, false);
            PrintWriter writer = response.getWriter();
            writer.print(content);
        }
    }
    */
}
