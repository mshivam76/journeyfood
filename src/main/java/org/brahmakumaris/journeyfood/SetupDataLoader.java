package org.brahmakumaris.journeyfood;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.brahmakumaris.journeyfood.entity.Privilege;
import org.brahmakumaris.journeyfood.entity.Role;
import org.brahmakumaris.journeyfood.entity.SpecialItem;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.repository.PrivilegeRepository;
import org.brahmakumaris.journeyfood.repository.RoleRepository;
import org.brahmakumaris.journeyfood.repository.SpecialItemRepository;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Autowired
    private SpecialItemRepository specialItemRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        final Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege, deletePrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
//      final Role userRole =  createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("admin@bkjourneyfood.org", "Admin", "ContactNoOfGuide", "nameOfCenter", "ShivBabakaBhandarahaiBharpoor", new HashSet<Role>(Arrays.asList(adminRole)));
        alreadySetup = true;
        insertSpecialItems();
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    UserEntity createUserIfNotFound(final String email, final String nameOfGuide, final String contactNoOfGuide, final String nameOfCenter, final String password, final Set<Role> roles) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            try {
				user = new UserEntity();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            user.setNameOfGuide(nameOfGuide);
            user.setNameOfCenter(nameOfCenter);
            user.setContactNoOfGuide(contactNoOfGuide);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
            user.setZone("Indore");
    		user.setSubZone("Jabalpur");
    		user.setPincode(482003);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
    
    @Transactional
    private void insertSpecialItems() {
    	List<SpecialItem> item = specialItemRepository.findAll();
    	if(item.isEmpty()) {
    		item.add(new SpecialItem("Idli"));
    		item.add(new SpecialItem("Dhokla"));
    		item.add(new SpecialItem("Tomato Chutni"));
    		item.add(new SpecialItem("Potato Chips"));
    		item.add(new SpecialItem("Curd Rice"));
    		item.add(new SpecialItem("Fried Potato"));
    		item.add(new SpecialItem("Lemon Rice"));
    		item.add(new SpecialItem("Paratha"));
    		item.add(new SpecialItem("Sandwich"));
    	}
    	for (SpecialItem specialItem : item) {
			specialItemRepository.save(specialItem);
		}
    }
}