package com.dh.clinica.controller;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.service.impl.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
       Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if(paciente.isPresent()){
            return ResponseEntity.ok(paciente.get());
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
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(paciente.getId());
        if (pacienteEncontrado.isPresent()){
            pacienteService.modificarPaciente(pacienteEncontrado.get());
            String jsonResponse = "{\"mensaje\": \"El paciente fue modificado\"}";
            return ResponseEntity.ok(jsonResponse);

        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);
        if (pacienteEncontrado.isPresent()){
            pacienteService.eliminarPaciente(id);
            String jsonResponse = "{\"mensaje\": \"El paciente fue eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

}


