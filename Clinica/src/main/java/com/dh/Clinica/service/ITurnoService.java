package com.dh.Clinica.service;

import com.dh.Clinica.dto.request.TurnoModifyDto;
import com.dh.Clinica.dto.request.TurnoRequestDto;
import com.dh.Clinica.dto.response.TurnoResponseDto;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);

    Optional<TurnoResponseDto> buscarPorId(Integer id);
    List<TurnoResponseDto> buscarTodos();

    void modificarTurnos(TurnoModifyDto turnoModifyDto);

    void eliminarTurno(Integer id);
    Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido);
}
