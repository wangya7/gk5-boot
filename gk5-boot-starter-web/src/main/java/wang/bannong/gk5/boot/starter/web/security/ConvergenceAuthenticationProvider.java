package wang.bannong.gk5.boot.starter.web.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import wang.bannong.gk5.boot.starter.web.gateway.SubjectClient;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;


public class ConvergenceAuthenticationProvider implements AuthenticationProvider {

    private final SubjectService subjectService;

    public ConvergenceAuthenticationProvider(
            SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        ConvergenceAuthenticationToken auth = (ConvergenceAuthenticationToken) authentication;
        AuthenticationType authenticationType = auth.getAuthenticationType();
        String principal = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        SubjectClient subjectClient = auth.getSubjectClient();
        Subject subject = subjectService.querySubject(principal, password, authenticationType, subjectClient);
        return buildResult(auth, subject);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ConvergenceAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    private Authentication buildResult(ConvergenceAuthenticationToken auth,
                                       UserDetails userDetails) {
        ConvergenceAuthenticationToken result = new ConvergenceAuthenticationToken(userDetails, userDetails.getAuthorities());
        result.setDetails(userDetails);
        return result;
    }
}
