package com.dh.clinica.controller;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.service.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")

    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Odontologo odontologo = odontologoService.buscarPorId(id);
        if (odontologo != null) {
            return ResponseEntity.ok(odontologo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Odontologo no encontrado");
        }

    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> buscarTodos() {

        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo odontologoEncontrado = odontologoService.buscarPorId(odontologo.getId());
        if (odontologoEncontrado != null) {
            odontologoService.modificarOdontologo(odontologo);
            return ResponseEntity.ok("EL odontologo fue modificado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


        @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<?> eliminarOdontologo (@PathVariable Integer id){
            Odontologo odontologoEncontrado = odontologoService.buscarPorId(id);
            if (odontologoEncontrado != null) {
                odontologoService.eliminarOdontologo(id);
                return ResponseEntity.ok("El odontologo fue eliminado");
            }else{
                return  ResponseEntity.notFound().build();
            }
        }
    }

