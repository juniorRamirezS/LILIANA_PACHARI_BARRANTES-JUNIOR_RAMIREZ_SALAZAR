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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
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
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
//        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
//        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Turno turnoAPersistir = new Turno();
        Turno turnoDesdeBD = null;
        TurnoResponseDto turnoResponseDto = null;
        //aqui armamos el "turno", ya que recibimos el tipo "turnoRequestDto"
        if(paciente.isPresent() && odontologo.isPresent()){
            // el .get() nos da el objeto paciente u odontologo sin el tipo de optional
            turnoAPersistir.setPaciente(paciente.get());
            turnoAPersistir.setOdontologo(odontologo.get());
            turnoAPersistir.setFecha(LocalDate.parse(turnoRequestDto.getFecha())); //para convertir un string en fecha
            turnoDesdeBD = turnoRepository.save(turnoAPersistir);
            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeBD);
            return turnoResponseDto;
        } else {
            throw new BadRequestException("Turno no guardado");
        }

    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);
        TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
        return Optional.of(turnoRespuesta);
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for(Turno t: turnosDesdeBD){
            // manera manual
            //turnosRespuesta.add(obtenerTurnoResponse(t));
            // model mapper
            turnosRespuesta.add(convertirTurnoEnResponse(t));
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurnos(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());
        if(paciente.isPresent() && odontologo.isPresent()){
            Turno turno = new Turno(
                    turnoModifyDto.getId(),
                    paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha())
            );
            turnoRepository.save(turno);
        }
    }
//    @Override
//    public void eliminarTurno(Integer id){
//        Optional<TurnoResponseDto> turnoEncontrado = buscarPorId(id);
//        if(turnoEncontrado.isPresent()) {
//            turnoRepository.deleteById(id);
//        } else {
//            throw new ResourceNotFoundException("Turno no encontrado");
//        }
//    }

    @Override
    public void eliminarTurno(Integer id){
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        if(turnoEncontrado.isPresent()){
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }

    }


    //FUNCIONES PAR AHACER LA CONVERSION A OBJETOS DTO

    private TurnoResponseDto obtenerTurnoResponse(Turno turnoDesdeBD){
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
        return turnoResponseDto;
    }

    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        //PAQUETE OBTENIDO DESDE MAVEN REPOSITORY
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        return turnoResponseDto;
    }
}
