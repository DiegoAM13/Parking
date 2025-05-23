package com.Parking.Seguridad.Config;

import com.Parking.Seguridad.entities.Role;
import com.Parking.Seguridad.enums.RoleList;
import com.Parking.Seguridad.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(RoleList.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(null, RoleList.ROLE_USER));
        }
        if (roleRepository.findByName(RoleList.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(null, RoleList.ROLE_ADMIN));
        }
    }
}
