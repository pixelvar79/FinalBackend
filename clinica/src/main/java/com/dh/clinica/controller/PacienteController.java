package com.dh.clinica.controller;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?>  buscarPorId(@PathVariable Integer id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if(paciente!=null){
            return ResponseEntity.ok(paciente);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("paciente no encontrado");
            //otras formas de hacer lo mismo de la linea anterior
            // return ResponseEntity.status(HttpStatus.valueOf(404).build();
            // return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>> buscarTodos(){

        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteEncontrado = pacienteService.buscarPorId(paciente.getId());
        if (pacienteEncontrado!=null){
            pacienteService.modificarPaciente(paciente);
            return ResponseEntity.ok("El paciente fue modificado");

        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
        Paciente pacienteEncontrado = pacienteService.buscarPorId(id);
        if (pacienteEncontrado!=null){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("El paciente fue eliminado");
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

}


