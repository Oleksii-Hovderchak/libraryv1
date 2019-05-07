package ua.nure.library.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.filter.GenericFilterBean;
import ua.nure.library.domain.UserEntity;
import ua.nure.library.domain.UserPrincipal;
import ua.nure.library.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Oleksii Hovderchak
 */
public class HeaderAuthentificationFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderAuthentificationFilter.class);

    private static final String AUTHORIZATION_HEADER_NAME = "authorization";

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HeaderAuthentificationFilter setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        SecurityContext sec = SecurityContextHolder.getContext();
        OAuth2Request oreq = new OAuth2Request(null, "clientId", null, true, null,
                null, null, null, null);

        String authorisationToken = hreq.getHeader(AUTHORIZATION_HEADER_NAME);

        if (checkAuthToken(request, response, chain, authorisationToken)) return;

        List<String> decodedUserCredentials = getListOfCredentials(new String(Base64.decodeBase64(authorisationToken.getBytes())));

        String email = decodedUserCredentials.get(0);
        LOGGER.debug("User email received: " + email);

        final Optional<UserEntity> optionalUserEntity = userService.findByEmail(email);

        if (optionalUserEntity.isPresent()) {
            UserEntity user = optionalUserEntity.get();
            LOGGER.debug("User is obtained");
            String password = decodedUserCredentials.get(1);

            if (checkPassword(request, response, chain, user, password)) return;

            UserPrincipal principal = new UserPrincipal(user);
            principal.setAccessToken(hreq.getHeader(AUTHORIZATION_HEADER_NAME));

            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .createAuthorityList(("ROLE_" + user.getUserRole()).toUpperCase());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "N/A",
                    grantedAuthorities);
            OAuth2Authentication auth = new OAuth2Authentication(oreq, authentication);

            auth.setAuthenticated(true);
            sec.setAuthentication(auth);
        } else if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("User with email [{}] not found", email);
        }


        // we have-to proceed anyway to pass all filter chains
        // We can have a resource which allowed for anonymous users.
        chain.doFilter(request, response);
    }

    private boolean checkPassword(ServletRequest request, ServletResponse response, FilterChain chain, UserEntity user, String password) throws IOException, ServletException {
        if (!passwordEncoder.matches(password, new String(user.getPassword()))) {
            LOGGER.warn("Password is not correct");
            request.setAttribute("Error", "Incorrect password");
            chain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private boolean checkAuthToken(ServletRequest request, ServletResponse response, FilterChain chain, String authorisationToken) throws IOException, ServletException {
        if (StringUtils.isBlank(authorisationToken)) {
            LOGGER.warn("No authorization token");
            chain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private List<String> getListOfCredentials(String decodedCreds) {
        if (StringUtils.isBlank(decodedCreds)) {
            throw new IllegalArgumentException("Credentials are blank");
        }
        return Stream.of(decodedCreds.split(":"))
                .map(String::new)
                .collect(Collectors.toList());
    }
}
