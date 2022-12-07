package app.utility;

import app.rest.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.pass-phrase}")
    private String passPhrase;

    //path prefixes that will be protected by the authentication filter
    private static final Set<String> SECURED_PATHS =
            Set.of("/scooters","/users");

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {

        String servletPath = httpServletRequest.getServletPath();

        //OPTIONS requests and non-secured area should pass through without check
        if (HttpMethod.OPTIONS.matches(httpServletRequest.getMethod()) ||
                SECURED_PATHS.stream().noneMatch(servletPath::startsWith)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        JWTToken jwtToken = null;

        //get the encrypted token string from the authorization request header
        String encryptedToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        //block the request if no token was found
        if (encryptedToken != null) {
            //remove the "bearer " token prefix, if used
            encryptedToken = encryptedToken.replace("Bearer ", "");

            jwtToken = JWTToken.decode(encryptedToken, this.passPhrase);
        }

        //validate token
        if (jwtToken == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"You need to login first.");
            //its one or the other, using the exception will throw a 500 instead of a 401
//            throw new UnAuthorizedException("You need to login first.");
        }

        //pass-on the token info as an attribute for the request
        httpServletRequest.setAttribute("token", jwtToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
