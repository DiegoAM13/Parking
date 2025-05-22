package com.Parking.Seguridad.services;

import com.Parking.Seguridad.dtos.ReservaDTO;
import com.Parking.Seguridad.dtos.SalidaDTO;
import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.entities.Reserva;
import com.Parking.Seguridad.enums.TipoVehiculo;
import com.Parking.Seguridad.repositories.EspacioRepository;
import com.Parking.Seguridad.repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva guardarReserva(ReservaDTO reservaDTO)
    {

        if (reservaDTO.getPlaca() == null || reservaDTO.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía");
        }

        if (reservaDTO.getTipo() == null || reservaDTO.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo no puede estar vacío");
        }

        if (reservaDTO.getEspacio_id() == null) {
            throw new IllegalArgumentException("Debe seleccionar un espacio");
        }

        if (!TipoVehiculo.esValido(reservaDTO.getTipo())) {
            throw new IllegalArgumentException("El tipo de vehículo debe ser 'Moto' o 'Carro'");
        }


        Reserva yaReservado = reservaRepository.findByPlaca(reservaDTO.getPlaca());
        if (yaReservado != null && (yaReservado.getHoraSalida() == null || yaReservado.getValorAPagar() == null)) {
            throw new IllegalStateException("Este vehículo ya está activo y no ha sido liquidado");
        }

        Espacio espacio = espacioRepository.findById(reservaDTO.espacio_id)
                .orElseThrow(() -> new IllegalStateException("Espacio no encontrado"));


        if (!espacio.getDisponible())
        {
            throw  new IllegalStateException("El espacio esta ocupado");
        }

        if (!espacio.getTipo().equalsIgnoreCase(reservaDTO.getTipo())) {
            throw new IllegalStateException("El espacio no es del tipo adecuado para el vehículo");
        }


        Reserva reserva = new Reserva();
        reserva.setPlaca(reservaDTO.getPlaca());
        reserva.setTipo(reservaDTO.getTipo());
        reserva.setHoraLlegada(LocalDateTime.now());
        reserva.setEspacio(espacio);

        espacio.setDisponible(false);
        espacioRepository.save(espacio);

        return reservaRepository.save(reserva);

    }

    public Reserva actualizarReserva(String placa, ReservaDTO reservaDTO)
    {
        Reserva reserva = reservaRepository.findByPlaca(placa);
        if( reserva == null) {
            throw new IllegalStateException("Este vehiculo no existe");
        }

        Espacio espacioActual = reserva.getEspacio();
        boolean cambioEspacio = false;


        if(reservaDTO.getPlaca() != null && !reservaDTO.getPlaca().isEmpty() && !reservaDTO.getPlaca().equals(reserva.getPlaca()))
        {
            if(reservaRepository.existsByPlaca(reservaDTO.getPlaca()))
            {
                throw new IllegalStateException("Ya existe una reserva activa con la nueva placa");
            }
            reserva.setPlaca(reservaDTO.getPlaca());
        }

        if (reservaDTO.getTipo() != null && !reservaDTO.getTipo().isEmpty() && !reservaDTO.getTipo().equalsIgnoreCase(reserva.getTipo()))
        {
            reserva.setTipo(reservaDTO.getTipo());

            if(reservaDTO.getEspacio_id() == null)
            {
                Espacio espacioCompatible = espacioRepository.findFirstByTipoAndDisponibleTrue(reservaDTO.getTipo())
                        .orElseThrow(() -> new IllegalStateException("No hay espacios disponibles para el tipo " + reservaDTO.getTipo()));


                espacioActual.setDisponible(true);
                espacioRepository.save(espacioActual);

                espacioCompatible.setDisponible(false);
                espacioRepository.save(espacioCompatible);

                reserva.setEspacio(espacioCompatible);
                cambioEspacio = true;
            }
        }

        if (reservaDTO.getEspacio_id() != null)
        {
            if (!cambioEspacio){

                Espacio nuevoEspacio = espacioRepository.findById(reservaDTO.getEspacio_id())
                        .orElseThrow(() -> new IllegalStateException("Nuevo espacio no encontrado"));

                if(!nuevoEspacio.getTipo().equalsIgnoreCase(reserva.getTipo()))
                {
                    throw new IllegalStateException("El nuevo espacio no corresponde al tipo de la reserva");
                }

                if (!nuevoEspacio.getDisponible())
                {
                    throw new IllegalStateException("El nuevo espacio no está disponible");
                }

                espacioActual.setDisponible(true);
                espacioRepository.save(espacioActual);

                nuevoEspacio.setDisponible(false);
                espacioRepository.save(nuevoEspacio);

                reserva.setEspacio(nuevoEspacio);
            }
        }
        return reservaRepository.save(reserva);
    }

    public SalidaDTO sacar( String placa) {
        Reserva reserva = reservaRepository.findReservaActivaByPlaca(placa);

        if (reserva == null) {
            throw new IllegalStateException("No se encontró una reserva activa con la placa " + placa);
        }

        LocalDateTime horaSalida = LocalDateTime.now();
        reserva.setHoraSalida(LocalDateTime.now());

      /*  LocalDateTime horaLlegada = reserva.getHoraLlegada();
        long minutos = Duration.between(horaLlegada, horaSalida).toMinutes();
        long horas = minutos / 60;
        if (minutos % 60 != 0) {
            horas++;
        }*/

        long horas = ChronoUnit.HOURS.between(reserva.getHoraLlegada(), horaSalida);
        if (horas == 0) {
            horas = 1; // mínimo 1 hora
        }

        BigDecimal tarifaPorHora;
        System.out.println("Tipo de vehículo: " + reserva.getTipo());
        switch (reserva.getTipo().toLowerCase()) {
            case "moto":
                tarifaPorHora = new BigDecimal("2000");
                break;
            case "carro":
                tarifaPorHora = new BigDecimal("5000");
                break;
            default:
                throw new IllegalStateException("Tipo de vehículo no reconocido");
        }

        BigDecimal valorTotal = tarifaPorHora.multiply(BigDecimal.valueOf(horas));
        reserva.setValorAPagar(valorTotal);

        reservaRepository.save(reserva);

        Espacio espacio = reserva.getEspacio();
        espacio.setDisponible(true);
        espacioRepository.save(espacio);

        //return  reservaRepository.save(reserva);
        return new SalidaDTO(reserva.getPlaca(), horaSalida, valorTotal);

    }

    public void eliminarReserva(String placa) {
        Reserva reserva = reservaRepository.findByPlaca(placa);
        if( reserva == null) {
            throw new IllegalStateException("Este vehiculo no existe");
        }

        Espacio espacio = reserva.getEspacio();
        espacio.setDisponible(true);
        espacioRepository.save(espacio);

        reservaRepository.delete(reserva);

    }

   public Reserva porPlaca(String placa)
   {
       List<Reserva> reservas =reservaRepository.findAll();

       for (Reserva reserva : reservas)
       {
           if (reserva.getPlaca().equalsIgnoreCase(placa))
               return  reserva;
       }
       throw new IllegalStateException("No se encontró este vehiculo" + placa);
   }

    public List<Reserva> obtenerTodos()
    {
        return reservaRepository.findAll();
    }

    public List<Reserva> obtenerTipo(String tipo)
    {
        boolean existe = reservaRepository.existsByTipo(tipo);

        if (!existe){
            throw new IllegalStateException("No existe ese tipo de vehiculo");
        }

        return reservaRepository.findByTipo(tipo);

    }




}
