package edu.uoc.elc.tsugi.security.utils;

import org.tsugi.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Factory class to map LTI roles to SpringBoot Security roles
 * Can be used to restrict access default SpringBoot Security access management (@PreAuthorize annotations, etc.)
 *
 * @author Xavi Aracil <xaracil@uoc.edu>
 */
public class RolesFactory {
	public Collection<String> user2RoleList(User user) {
		ArrayList<String> roleList = new ArrayList<>();

		roleList.add("USER");

		if(user.isInstructor()) {
			roleList.add("INSTRUCTOR");
		} else {
			roleList.add("LEARNER");
		}

		if(user.isMentor()) {
			roleList.add("MENTOR");
		}
		if(user.isRootAdmin()) {
			roleList.add("ROOT_ADMIN");
		}
		if(user.isTenantAdmin()) {
			roleList.add("TENANT_ADMIN");
		}

		return roleList;

	}
}
