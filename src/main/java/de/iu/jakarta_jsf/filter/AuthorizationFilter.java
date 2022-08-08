package de.iu.jakarta_jsf.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static de.iu.jakarta_jsf.jsf.LoginBean.*;

/**
 * Use this filter for all requests that contain the .xhtml file extension.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter  implements Filter {

    // URL to resource directory, containing css-files etc
    public static final String JAKARTA_FACES_RESOURCE = "jakarta.faces.resource";
    public static final String USER_IS_LOGGED_IN = "user_is_logged_in";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * This method is executed for each HttpRequest that matches the @WebFilter(urlPatterns = {"*.xhtml"}).
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String url = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);

        if (isPrivatePage(url) && !userIsLoggedIn(session)) {
            redirectToStart((HttpServletResponse) servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Redirects to the main page.
     */
    private void redirectToStart(HttpServletResponse servletResponse) throws IOException {
        HttpServletResponse response = servletResponse;
        response.sendRedirect(DATA_XHTML_URL);
    }

    /**
     * Check if the HttpSession has the USER_IS_LOGGED_IN attribute.
     */
    private boolean userIsLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(USER_IS_LOGGED_IN) != null && session.getAttribute(USER_IS_LOGGED_IN).equals("true");
    }

    /**
     * Check if the requested page isn't a public page or a resource.
     */
    private boolean isPrivatePage(String url) {
        return !(url.contains(DATA_XHTML_URL) || url.contains(LOGIN_XHTML_URL) || url.contains(JAKARTA_FACES_RESOURCE));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
