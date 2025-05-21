package com.Parking.Seguridad.repositories;

import com.Parking.Seguridad.entities.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {

    List<Espacio> findByTipo(String tipo);
    List<Espacio> findByDisponible(Boolean disponible);

    Optional<Espacio> findFirstByTipoAndDisponibleTrue(String tipo);

    boolean existsByTipo(String tipo);



}
