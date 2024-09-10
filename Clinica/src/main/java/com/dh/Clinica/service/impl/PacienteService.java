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
            return pacienteRepository.save(paciente);
        } else {
            throw new BadRequestException("Error al guardar paciente");
        }
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);
        if (pacienteEncontrado.isPresent()) {
            logger.info("Paciente " + pacienteEncontrado);
            return pacienteEncontrado;
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        logger.info("Paciente " + pacienteRepository.findAll());
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }


    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = buscarPorId(id);
        if (pacienteEncontrado.isPresent()) {
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte) {
        return pacienteRepository.buscarPorParteApellido(parte);
    }

}
