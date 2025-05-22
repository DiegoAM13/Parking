package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Reserva findByPlaca(String placa);

    boolean existsByPlaca(String placa);

    List<Reserva> findByTipo(String tipo);

    boolean existsByTipo(String tipo);

    @Query("SELECT r FROM Reserva r WHERE r.placa = :placa AND r.horaSalida IS NULL")
    Reserva findReservaActivaByPlaca(@Param("placa") String placa);

}
