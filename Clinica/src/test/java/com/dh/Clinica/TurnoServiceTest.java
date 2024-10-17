package com.dh.Clinica;

import com.dh.Clinica.dto.request.TurnoRequestDto;
import com.dh.Clinica.dto.response.TurnoResponseDto;
import com.dh.Clinica.entity.Domicilio;
import com.dh.Clinica.entity.Odontologo;
import com.dh.Clinica.entity.Paciente;
import com.dh.Clinica.entity.Turno;
import com.dh.Clinica.service.impl.OdontologoService;
import com.dh.Clinica.service.impl.PacienteService;
import com.dh.Clinica.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class TurnoServiceTest {
    @Autowired
    TurnoService turnoService;
    @Autowired
    PacienteService pacienteService;

    @Autowired
    OdontologoService odontologoService;
    Turno turno;
    TurnoResponseDto turnoResponseDto;

    TurnoRequestDto turnoRequestDto;

    Paciente paciente;
    Paciente pacienteDesdeDb;

    Odontologo odontologo;
    Odontologo odontologoDesdeDb;

    @BeforeEach
    void cargarDatos(){
        //   crear paciente
        Domicilio domicilio = new Domicilio(null,"Los claveles",145,"Caba","Buenos Aires");
        paciente = new Paciente();
        paciente.setApellido("Castro");
        paciente.setNombre("Maria");
        paciente.setDni("48974646");
        paciente.setFechaIngreso(LocalDate.of(2024,7,15));
        paciente.setDomicilio(domicilio);
        pacienteDesdeDb = pacienteService.guardarPaciente(paciente);
        //   crear odontologo
        odontologo = new Odontologo();
        odontologo.setApellido("Rodriguez");
        odontologo.setNombre("Rosa");
        odontologo.setNumeroDeMatricula("48974646");
        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
        //   crear turno
        turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(pacienteDesdeDb.getId());
        turnoRequestDto.setOdontologo_id(odontologoDesdeDb.getId());
        turnoRequestDto.setFecha("2024-09-30");

        turnoResponseDto = turnoService.guardarTurno(turnoRequestDto);
    }

    @Test
    @DisplayName("Testear que un turno fue cargado correctamente")
    void caso1(){
        assertNotNull(turnoResponseDto.getId());
    }

    @Test
    @DisplayName("Testear que un turno pueda ser buscado por id")
    void caso2(){
        //Dado
        Integer id = turnoResponseDto.getId();
        //cuando
        TurnoResponseDto turnoRecuperado = turnoService.buscarPorId(id).get();
        // entonces
        assertEquals(id, turnoRecuperado.getId());
    }

    @Test
    @DisplayName("Listar todos los turnos")
    void caso3(){
        //Dado
        List<TurnoResponseDto> turnos;
        // cuando
        turnos = turnoService.buscarTodos();
        // entonces
        assertFalse(turnos.isEmpty());
    }

}
