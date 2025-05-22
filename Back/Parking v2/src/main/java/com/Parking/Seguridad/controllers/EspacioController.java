package com.Parking.Seguridad.controllers;

import com.Parking.Seguridad.entities.Espacio;
import com.Parking.Seguridad.repositories.EspacioRepository;
import com.Parking.Seguridad.services.EspacioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/espacios")
@CrossOrigin(origins = "http://localhost:4200")
public class EspacioController {

    @Autowired
    EspacioService espacioService;

    @Autowired
    private EspacioRepository espacioRepository;

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarEspacio(@Valid @RequestBody Espacio espacio, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise los campos");
        }
        try {
             espacioRepository.save(espacio);
            return  ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
        }catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEspacio(@PathVariable Long id, @RequestBody Espacio espacioActualizar)
    {
        try {
            Espacio espacioActualizado = espacioService.actualizarEspacio(id,espacioActualizar);
            return  ResponseEntity.ok(espacioActualizado);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el espacio" + e.getMessage());
        }


    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEspacio(@PathVariable Long id)
    {
        try{
              Espacio espacio = espacioService.eliminarEspacio(id);
              return  ResponseEntity.ok().body("Espacio eliminado con exito: " + espacio.getId());

        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el espacio");
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerEspacio(@PathVariable Long id)
    {
        try{
            Espacio espacio = espacioService.obtenerEspacioPorId(id);
            return  ResponseEntity.ok(espacio);
        }catch (EntityNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/disponibles")
    public List<Espacio> EspaciosDisponibles()
    {
        return espacioService.ObtenerDisponibles();
    }
    @GetMapping("/ocupados")
    public List<Espacio> EspaciosOcupados()
    {
        return espacioService.ObtenerOcupados();
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<?>  ObtenerTipo(@PathVariable String tipo)
    {
        try
        {
            List<Espacio> espacios = espacioService.obtenerTipo(tipo);
            return ResponseEntity.ok(espacios);
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el espacio");
        }

    }

    @GetMapping("")
    public List<Espacio> obtenerTodos()
    {
        return espacioService.obtenerTodos();
    }









}
