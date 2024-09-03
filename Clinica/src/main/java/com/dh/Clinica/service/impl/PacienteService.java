package com.dh.Clinica.service.impl;

import com.dh.Clinica.entity.Paciente;
import com.dh.Clinica.exception.BadRequestException;
import com.dh.Clinica.exception.ResourceNotFoundException;
import com.dh.Clinica.repository.IPacienteRepository;
import com.dh.Clinica.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {

//        try {
//            if(paciente.){
//
//            }
            return pacienteRepository.save(paciente);
//        } catch (Exception e) {
//            throw new BadRequestException("Error al guardar paciente");
//        }

    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }


    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = buscarPorId(id);
        if(pacienteEncontrado.isPresent()){
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
    public List<Paciente> buscarPorUnaParteApellido(String parte){
        return pacienteRepository.buscarPorParteApellido(parte);
    }

}
