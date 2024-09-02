package com.dh.clinica.controller;

import com.dh.clinica.dto.request.TurnoModificarDto;
import com.dh.clinica.dto.request.TurnoRequestDto;
import com.dh.clinica.dto.response.TurnoResponseDto;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {

        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto){
        TurnoResponseDto turnoAGuardar = turnoService.guardarTurno(turnoRequestDto);
        if(turnoAGuardar != null){
            return ResponseEntity.ok(turnoAGuardar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontologo no fueron encontrados");
        }

    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?>  buscarPorId(@PathVariable Integer id) {
        Optional<TurnoResponseDto> turno = turnoService.buscarPorId(id);
        if(turno.isPresent()){
            return ResponseEntity.ok(turno);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("turno no encontrado");

        }

    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarTurno(@RequestBody TurnoModificarDto turnoModificarDto){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(turnoModificarDto.getId());
        if (turnoEncontrado.isPresent()){
            turnoService.modificarTurno(turnoModificarDto);
            return ResponseEntity.ok("El turno fue modificado");

        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id){
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(id);
        if (turnoEncontrado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("El turno fue eliminado");
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
}