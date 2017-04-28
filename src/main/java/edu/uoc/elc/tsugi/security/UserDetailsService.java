package edu.uoc.elc.tsugi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.tsugi.Launch;

import java.util.Collection;

/**
 * 	Interface that allows for retrieving a UserDetails object based on an <tt>Authentication</tt> object.
 *  It grabs the {@link org.tsugi.Launch} launch from the Authentication object.
 *
 *  @author Xavi Aracil <xaracil@uoc.edu>
 */
public class UserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T> {

	/**
	 * @param authentication The pre-authenticated authentication token
	 * @return UserDetails for the given authentication token, never null.
	 * @throws UsernameNotFoundException if no user details can be found for the given authentication
	 *                                   token
	 */
	@Override
	public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
		if (authentication.getCredentials() instanceof Launch) {
			Launch launch = (Launch) authentication.getCredentials();

			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			if (authentication.getDetails() instanceof PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails) {
				PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails details = (PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails) authentication.getDetails();
				authorities = details.getGrantedAuthorities();
			}

			// create user details
			return new UserDetails(launch, authorities);

		}
		return null;
	}
}
