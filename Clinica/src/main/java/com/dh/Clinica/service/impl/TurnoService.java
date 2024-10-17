package com.dh.Clinica.service.impl;

import com.dh.Clinica.dto.request.TurnoModifyDto;
import com.dh.Clinica.dto.request.TurnoRequestDto;
import com.dh.Clinica.dto.response.OdontologoResponseDto;
import com.dh.Clinica.dto.response.PacienteResponseDto;
import com.dh.Clinica.dto.response.TurnoResponseDto;
import com.dh.Clinica.entity.Odontologo;
import com.dh.Clinica.entity.Paciente;
import com.dh.Clinica.entity.Turno;
import com.dh.Clinica.exception.BadRequestException;
import com.dh.Clinica.exception.ResourceNotFoundException;
import com.dh.Clinica.repository.ITurnoRepository;
import com.dh.Clinica.service.IOdontologoService;
import com.dh.Clinica.service.IPacienteService;
import com.dh.Clinica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);

    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;

    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
            Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
            Turno turno = new Turno();
            Turno turnoDesdeBD = null;
            TurnoResponseDto turnoResponseDto = null;
            // el armado del turno desde el turno request dto
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            // aca obtengo el turno persistido con el id
            turnoDesdeBD = turnoRepository.save(turno);

            // armado del turno response dto desde el turno obtenido de la base de datos
            // armado a mano
            // turnoResponseDto = obtenerTurnoResponse(turnoDesdeBD);
            // armado con modelmapper
            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeBD);
            logger.info("Turno a registrar: " + turnoRequestDto);
            return turnoResponseDto;
        } catch (ResourceNotFoundException e) {
            throw new BadRequestException("Paciente u odontologo no existen en la base de datos");
        }
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);
        if (turno.isPresent()) {
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
            logger.info("Turno encontrado por Id: " + turnoRespuesta);
            return Optional.of(turnoRespuesta);
        } else {
            throw new ResourceNotFoundException("El turno no fue encontrado");
        }
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t : turnosDesdeBD) {
            // manera manual
            //turnosRespuesta.add(obtenerTurnoResponse(t));
            // model mapper
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(t);
            turnosRespuesta.add(turnoRespuesta);

        }
        logger.info("Lista de turnos: " + turnosRespuesta);
        return turnosRespuesta;
    }

    @Override
    public void modificarTurnos(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());
        if (paciente.isPresent() && odontologo.isPresent()) {
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha())
            );
            logger.info("Turno a modificar: " + turno);
            turnoRepository.save(turno);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<TurnoResponseDto> turnoEncontrado = buscarPorId(id);
        if (turnoEncontrado.isPresent()) {
            logger.info("Turno a eliminar: " + turnoEncontrado);
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

//    @Override
//    public void eliminarTurno(Integer id) {
//        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
//        if (turnoEncontrado.isPresent()) {
//            turnoRepository.deleteById(id);
//        } else {
//            throw new ResourceNotFoundException("Turno no encontrado");
//        }
////    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        Optional<Turno> turno = turnoRepository.buscarPorApellidoPaciente(pacienteApellido);
        TurnoResponseDto turnoParaResponder = null;
        if (turno.isPresent()) {
            turnoParaResponder = convertirTurnoEnResponse(turno.get());
        }
        logger.info("Turno encontrado por paciente: " + turnoParaResponder);
        return Optional.ofNullable(turnoParaResponder);
    }


    //FUNCIONES PAR AHACER LA CONVERSION A OBJETOS DTO

    private TurnoResponseDto obtenerTurnoResponse(Turno turnoDesdeBD) {
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeBD.getOdontologo().getId(), turnoDesdeBD.getOdontologo().getNumeroDeMatricula(),
                turnoDesdeBD.getOdontologo().getApellido(), turnoDesdeBD.getOdontologo().getNombre()
        );
        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeBD.getPaciente().getId(), turnoDesdeBD.getPaciente().getApellido(),
                turnoDesdeBD.getPaciente().getNombre(), turnoDesdeBD.getPaciente().getDni()
        );
        TurnoResponseDto turnoResponseDto = new TurnoResponseDto(
                turnoDesdeBD.getId(),
                pacienteResponseDto, odontologoResponseDto,
                turnoDesdeBD.getFecha().toString()
        );
        logger.info("Turno encontrado: " + turnoResponseDto);
        return turnoResponseDto;
    }

    private TurnoResponseDto convertirTurnoEnResponse(Turno turno) {
        //PAQUETE OBTENIDO DESDE MAVEN REPOSITORY
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        logger.info("Turno: " + turnoResponseDto);
        return turnoResponseDto;
    }
}
