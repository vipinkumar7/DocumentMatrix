package com.sentinel.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sentinel.persistence.models.Permission;
import com.sentinel.persistence.models.Role;
import com.sentinel.persistence.models.User;
import com.sentinel.persistence.repository.PermissionRepository;
import com.sentinel.persistence.repository.RoleRepository;
import com.sentinel.persistence.repository.UserRepository;

@Component
public class SetupDataLoader implements
		ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// API

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}
		System.out.println("==============================");
		// == create initial permissions
		final Permission readPermission = createPermissionIfNotFound("READ_PRIVILEGE");
		final Permission writePermission = createPermissionIfNotFound("WRITE_PRIVILEGE");

		// == create initial roles
		final List<Permission> adminPermission = Arrays.asList(readPermission,
				writePermission);
		createRoleIfNotFound("ROLE_ADMIN", adminPermission);
		createRoleIfNotFound("ROLE_USER", Arrays.asList(readPermission));

		final Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		final User user = new User();
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setEmail("admin@test.com");
		user.setRoles(Arrays.asList(adminRole));
		user.setEnabled(true);
		userRepository.save(user);

		final Role adminRole1 = roleRepository.findByName("ROLE_USER");
		final User user1 = new User();
		user1.setFirstName("user1");
		user1.setLastName("user1");
		user1.setPassword(passwordEncoder.encode("user1"));
		user1.setEmail("user1@test.com");
		user1.setRoles(Arrays.asList(adminRole1));
		user1.setEnabled(true);
		userRepository.save(user1);
		alreadySetup = true;
	}

	private final Permission createPermissionIfNotFound(final String name) {
		Permission privilege = permissionRepository.findByName(name);
		if (privilege == null) {
			privilege = new Permission(name);
			permissionRepository.save(privilege);
		}
		return privilege;
	}

	private final Role createRoleIfNotFound(final String name,
			final Collection<Permission> privileges) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}

}