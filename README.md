# tsugi-java-springboot-security
[Tsugi](https://www.tsugi.org)'s SpringBoot security implementation

Provides a library for using LTI as the authentication and authorization mechanism in your Spring Boot application.

## Roles

LTI roles are mapped to Spring Security roles in `edu.uoc.elc.tsugi.security.utils.RolesFactory` as follows:

- Authenticated users have the role `ROLE_USER`
- Learner users have the role `ROLE_LEARNER`
- Mentor users have the role `ROLE_MENTOR`
- Root Admin users have the role `ROLE_ROOT_ADMIN`
- Tenant Admin users have the role `ROLE_TENANT_ADMIN`

See https://github.com/xaviaracil/tsugi-java-springboot-security-example for an example of using these roles in a web application.

# Install

Requires Java 8 and Maven 3.

1. Clone the repo
2. `mvn install`

# Example

See https://github.com/xaviaracil/tsugi-java-springboot-security-example for an example.

