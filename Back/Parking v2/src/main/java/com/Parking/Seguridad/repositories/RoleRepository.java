package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.Role;
import com.Parking.Seguridad.enums.RoleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleList name);
}
