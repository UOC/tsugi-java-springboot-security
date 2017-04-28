package edu.uoc.elc.tsugi.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.tsugi.Launch;

import java.util.Collection;

/**
 * Models core user information retrieved by a {@link org.springframework.security.core.userdetails.UserDetailsService}.
 * It holds the {@link org.tsugi.Launch} launch.
 *
 * TODO: check enabled, accountNonExpired, credentialsNonExpired, accountNonLocked
 *
 * @author Xavi Aracil <xaracil@uoc.edu>
 */
public class UserDetails extends User {
	private Launch launch;

	public UserDetails(Launch launch, Collection<? extends GrantedAuthority> authorities) {
		super(launch.getUser().getEmail(), "N.A", authorities);
		this.launch = launch;
	}

	public Launch getLaunch() {
		return launch;
	}
}
