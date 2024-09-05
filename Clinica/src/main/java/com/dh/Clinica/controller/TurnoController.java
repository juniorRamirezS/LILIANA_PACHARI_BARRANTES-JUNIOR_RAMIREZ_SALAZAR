package com.dh.Clinica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dh.Clinica.dto.request.TurnoModifyDto;
import com.dh.Clinica.dto.request.TurnoRequestDto;
import com.dh.Clinica.dto.response.TurnoResponseDto;
import com.dh.Clinica.service.ITurnoService;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<TurnoResponseDto> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto) {
        return ResponseEntity.ok(turnoService.guardarTurno(turnoRequestDto));
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos() {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/buscarTurnoApellido/{apellido}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorApellido(@PathVariable String apellido) {
        Optional<TurnoResponseDto> turno = turnoService.buscarTurnosPorPaciente(apellido);
        return ResponseEntity.ok(turno.get());
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarTurno(@RequestBody TurnoModifyDto turnoModifyDto) {
        turnoService.modificarTurnos(turnoModifyDto);
        return ResponseEntity.ok("{\"mensaje\": \"El turno fue modificado\"}");
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoResponseDto> buscarPorId(@PathVariable Integer id) {
        Optional<TurnoResponseDto> turnoEncontrado = turnoService.buscarPorId(id);
        if (turnoEncontrado.isPresent()) {
            return ResponseEntity.ok(turnoEncontrado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);
        String jsonResponse = "{\"mensaje\": \"El turno fue eliminado\"}";
        return ResponseEntity.ok(jsonResponse);
    }
}
