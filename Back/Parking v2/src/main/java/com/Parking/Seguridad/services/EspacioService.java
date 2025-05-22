package com.Parking.Seguridad.services;

import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.enums.TipoVehiculo;
import com.Parking.Seguridad.repositories.EspacioRepository;
import com.Parking.Seguridad.repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspacioService {

    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public void guardarEspacio(Espacio espacio){
        if (!TipoVehiculo.esValido(espacio.getTipo())) {
            throw new IllegalArgumentException("El tipo de vehículo debe ser 'Moto' o 'Carro'");
        }
        espacioRepository.save(espacio);
    }

    public Espacio actualizarEspacio(Long id, Espacio espacioActualizar)
    {
        Espacio espacioExistente = espacioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Espacio con ID: " + id + " no encontrado"));

        if (espacioActualizar.getTipo() != null && !espacioActualizar.getTipo().isBlank())
        {
            espacioExistente.setTipo(espacioActualizar.getTipo());
        }
        if (espacioActualizar.getDisponible() !=null )
        {
                espacioExistente.setDisponible(espacioActualizar.getDisponible());
        }
       return espacioRepository.save(espacioExistente);
    }

    public Espacio eliminarEspacio(Long id)
    {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Espacio con ID: " + id + " no encontrado"));

        if (!espacio.getDisponible()) {
            throw new IllegalStateException("No se puede eliminar un espacio que no está disponible");
        }

        espacioRepository.delete(espacio);
        return espacio;
    }

    public List<Espacio> obtenerTodos()
    {
        return espacioRepository.findAll();
    }

    public List<Espacio> obtenerTipo(String tipo)
    {
       boolean existe = espacioRepository.existsByTipo(tipo);

       if (!existe){
           throw new IllegalStateException("No existe ese tipo de vehiculo");
       }

        return espacioRepository.findByTipo(tipo);

    }

    public Espacio obtenerEspacioPorId(Long id) {
        return espacioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Espacio con ID " + id + " no encontrado"));
    }

    public List<Espacio> ObtenerDisponibles()
    {
        return espacioRepository.findByDisponible(true);
    }

    public List<Espacio> ObtenerOcupados()
    {
        return  espacioRepository.findByDisponible(false);
    }
}
