package com.dh.Clinica.service.impl;

import com.dh.Clinica.entity.Paciente;
import com.dh.Clinica.exception.BadRequestException;
import com.dh.Clinica.exception.ResourceNotFoundException;
import com.dh.Clinica.repository.IPacienteRepository;
import com.dh.Clinica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        if (Objects.nonNull(paciente)) {
            logger.info("Paciente registrado: " + paciente);
            return pacienteRepository.save(paciente);
        } else {
            throw new BadRequestException("Error al guardar paciente");
        }
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);
        if (pacienteEncontrado.isPresent()) {
            logger.info("Paciente encontrado por Id: " + pacienteEncontrado);
            return pacienteEncontrado;
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        logger.info("Lista de pacientes: " + pacienteRepository.findAll());
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        Optional<Paciente> pacienteEncontrado = buscarPorId(paciente.getId());
        if(pacienteEncontrado.isPresent()){
            logger.info("Paciente modificado: " + pacienteEncontrado);
            pacienteRepository.save(paciente);
        }
    }


    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = buscarPorId(id);
        if (pacienteEncontrado.isPresent()) {
            logger.info("Paciente a eliminar: " + pacienteEncontrado);
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        logger.info("Paciente encontrado por apellido y nombre: " + pacienteRepository.findByApellidoAndNombre(apellido, nombre));
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte) {
        logger.info("Paciente por apellido: " + pacienteRepository.buscarPorParteApellido(parte));
        return pacienteRepository.buscarPorParteApellido(parte);
    }

}
