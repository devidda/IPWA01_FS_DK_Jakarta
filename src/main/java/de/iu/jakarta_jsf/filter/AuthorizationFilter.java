package de.iu.jakarta_jsf.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static de.iu.jakarta_jsf.jsf.LoginBean.*;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter  implements Filter {

    // URL to resource directory, containing css-files etc
    public static final String JAKARTA_FACES_RESOURCE = "jakarta.faces.resource";
    public static final String USER_IS_LOGGED_IN = "user_is_logged_in";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

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

    private void redirectToStart(HttpServletResponse servletResponse) throws IOException {
        HttpServletResponse response = servletResponse;
        response.sendRedirect(INDEX_XHTML_URL);
    }

    private boolean userIsLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(USER_IS_LOGGED_IN) != null && session.getAttribute(USER_IS_LOGGED_IN).equals("true");
    }

    private boolean isPrivatePage(String url) {
        return !(url.contains(INDEX_XHTML_URL) || url.contains(LOGIN_XHTML_URL) || url.contains(JAKARTA_FACES_RESOURCE));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
