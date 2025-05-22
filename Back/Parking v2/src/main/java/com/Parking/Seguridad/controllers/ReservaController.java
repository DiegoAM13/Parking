package com.Parking.Seguridad.controllers;

import com.Parking.Seguridad.dtos.ReservaDTO;
import com.Parking.Seguridad.dtos.SalidaDTO;
import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.entities.Reserva;
import com.Parking.Seguridad.repositories.EspacioRepository;
import com.Parking.Seguridad.repositories.ReservaRepository;
import com.Parking.Seguridad.services.EspacioService;
import com.Parking.Seguridad.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @Autowired
    EspacioService espacioService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EspacioRepository espacioRepository;


    @PostMapping("/guardar")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO)
    {
        try{
            Reserva reserva = reservaService.guardarReserva(reservaDTO);
            return  ResponseEntity.status(HttpStatus.CREATED).body(reserva);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // espacio ocupado
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la reserva"  + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{placa}")
    public ResponseEntity<?> actualizarReserva(@PathVariable String placa, @RequestBody ReservaDTO reservaDTO)
    {
        try {
            Reserva reservaAActualizada = reservaService.actualizarReserva(placa, reservaDTO);
            return  ResponseEntity.ok(reservaAActualizada);
        }catch (IllegalStateException e)
        {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e)
        {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la reserva");
        }
    }

    @PutMapping("/sacar/{placa}")
    public ResponseEntity<?> sacarVehiculo(@PathVariable String placa)
    {

        try{
            SalidaDTO SalidaDTO = reservaService.sacar(placa);
            return  ResponseEntity.ok(SalidaDTO);
        }catch (Exception e)
        {
            e.printStackTrace();
             return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al sacar el vehiculo");
        }

    }
    @DeleteMapping("/eliminar/{placa}")
    public ResponseEntity<?> EliminarReserva(@PathVariable String placa)
    {
        try{
            reservaService.eliminarReserva(placa);
            return ResponseEntity.ok("Reserva eliminada correctamente");
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la reserva");
        }
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> ReservaPlaca(@PathVariable String placa)
    {
        try{
            Reserva reserva = reservaService.porPlaca(placa);
            return  ResponseEntity.ok(reserva);
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }


    @GetMapping("")
    public List<Reserva> obtenerTodos()
    {
        return reservaService.obtenerTodos();
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?>  ObtenerTipo(@PathVariable String tipo)
    {
        try
        {
            List<Reserva> reservas = reservaService.obtenerTipo(tipo);
            return ResponseEntity.ok(reservas);
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el espacio");
        }

    }
}
