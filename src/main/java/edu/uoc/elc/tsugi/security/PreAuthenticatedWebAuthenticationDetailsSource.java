package edu.uoc.elc.tsugi.security;

import edu.uoc.elc.tsugi.security.utils.RolesFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.Attributes2GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.tsugi.Launch;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Provides a {@link org.springframework.security.core.Authentication#getDetails()} object for
 * a given web request.
 *
 * Roles are mapped from the {@link org.tsugi.Launch} launch.
 *
 * @author Xavi Aracil <xaracil@uoc.edu>
 */
public class PreAuthenticatedWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> {
	private final Attributes2GrantedAuthoritiesMapper ltiUserRoles2GrantedAuthoritiesMapper = new SimpleAttributes2GrantedAuthoritiesMapper();

	private Launch launch;

	public PreAuthenticatedWebAuthenticationDetailsSource(Launch launch) {
		this.launch = launch;
	}

	private Collection<String> getUserRoles(HttpServletRequest request) {
		RolesFactory rolesFactory = new RolesFactory();
		return rolesFactory.user2RoleList(launch.getUser());
	}

	/**
	 * Called by a class when it wishes a new authentication details instance to be created.
	 *
	 * @param httpServletRequest the request object, which may be used by the authentication details object
	 * @return a fully-configured authentication details instance
	 */
	@Override
	public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {
		Collection<String> ltiUserRoles = this.getUserRoles(httpServletRequest);
		final Collection<? extends GrantedAuthority> userGas = this.ltiUserRoles2GrantedAuthoritiesMapper.getGrantedAuthorities(ltiUserRoles);

		return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(httpServletRequest, userGas);
	}
}
