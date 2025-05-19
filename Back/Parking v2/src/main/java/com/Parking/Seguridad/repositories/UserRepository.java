<<<<<<< HEAD
package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
=======
package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
>>>>>>> e093025465f59ccaef8ce613cba2b5951f0e2cb8
