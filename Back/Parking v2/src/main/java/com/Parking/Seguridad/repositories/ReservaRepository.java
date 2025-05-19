<<<<<<< HEAD
package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Reserva findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    List<Reserva> findByTipo(String tipo);

    boolean existsByTipo(String tipo);
}
=======
package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Reserva findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    List<Reserva> findByTipo(String tipo);

    boolean existsByTipo(String tipo);
}
>>>>>>> e093025465f59ccaef8ce613cba2b5951f0e2cb8
