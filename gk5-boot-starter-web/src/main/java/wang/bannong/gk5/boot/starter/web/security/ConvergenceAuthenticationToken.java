package wang.bannong.gk5.boot.starter.web.security;

import java.util.Collection;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import wang.bannong.gk5.boot.starter.web.gateway.SubjectClient;

/**
 * 融合的登录验证方式：
 * 1. 密码
 * 2. 验证码
 */
public class ConvergenceAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -5569119450494448642L;

    // ~ Instance fields
	// ================================================================================================
    private final Object principal;
    private Object credentials;
    private AuthenticationType authenticationType;
    private SubjectClient subjectClient;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
	 * will return <code>false</code>.
	 *
	 */
	public ConvergenceAuthenticationToken(Object principal,
                                          Object credentials,
                                          AuthenticationType authenticationType,
                                          SubjectClient subjectClient) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.authenticationType = authenticationType;
        this.subjectClient = subjectClient;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by <code>AuthenticationManager</code> or
	 * <code>AuthenticationProvider</code> implementations that are satisfied with
	 * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
	 * authentication token.
	 *
	 * @param principal
	 * @param authorities
	 */
	public ConvergenceAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true); // must use super, as we override
	}

	// ~ Methods
	// ========================================================================================================

	public Object getCredentials() {
		return this.credentials;
	}

	public Object getPrincipal() {
		return this.principal;
	}

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public SubjectClient getSubjectClient() {
        return subjectClient;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

    @Override
    public String toString() {
        return "ConvergenceAuthenticationToken{" +
                "principal=" + principal +
                ", credentials=" + credentials +
                ", authenticationType=" + authenticationType +
                ", subjectClient=" + subjectClient +
                "} " + super.toString();
    }
}
