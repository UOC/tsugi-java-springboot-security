package edu.uoc.elc.tsugi.security;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.tsugi.Launch;
import org.tsugi.Tsugi;
import org.tsugi.TsugiFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base class for processing filters that handle pre-authenticated authentication requests, where it is assumed
 * that the principal has already been authenticated by an external system.
 *
 * That external system is a LTI consumer
 *
 * @author Xavi Aracil <xaracil@uoc.edu>
 */
public class PreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	private HttpServletResponse response;
	private Tsugi tsugi = null;

	public Tsugi getTsugi() {
		if ( tsugi == null ) {
			tsugi = TsugiFactory.getTsugi();
		}
		return tsugi;
	}

	private Launch launch;

	/**
	 * Try to authenticate a pre-authenticated user with Spring Security if the user has not yet been authenticated.
	 * We override this method in order to access response in {@see getPreAuthenticatedPrincipal}
	 * @param request LTI request
	 * @param response the response
	 * @param chain next filter in the chain
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		this.response = (HttpServletResponse) response;
		this.launch = null;
		super.doFilter(request, response, chain);
	}

	/**
	 * Override to extract the principal information from the current request
	 *
	 * @param request LTI request
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		this.launch = getTsugi().getLaunch(request, response);
		if (launch.isComplete()) {
			this.logger.info("Valid LTI call from " + launch.getUser().getEmail());
			setAuthenticationDetailsSource(new PreAuthenticatedWebAuthenticationDetailsSource(launch));
		}
		return launch.getUser();
	}

	/**
	 * Override to extract the credentials (if applicable) from the current request. Should not return null for a valid
	 * principal, though some implementations may return a dummy value.
	 *
	 * @param request LTI request
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return this.launch;
	}
}
