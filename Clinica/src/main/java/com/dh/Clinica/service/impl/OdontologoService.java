package com.dh.Clinica.service.impl;

import com.dh.Clinica.entity.Odontologo;
import com.dh.Clinica.exception.BadRequestException;
import com.dh.Clinica.exception.ResourceNotFoundException;
import com.dh.Clinica.repository.IOdontologoRepository;
import com.dh.Clinica.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private final Logger logger = LoggerFactory.getLogger(OdontologoService.class);

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        if (Objects.nonNull(odontologo)) {
            return odontologoRepository.save(odontologo);
        } else {
            throw new BadRequestException("Error al guardar odontologo");
        }
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);
        if (odontologoEncontrado.isPresent()) {
            logger.info("Odontologo: " + odontologoEncontrado);
            return odontologoEncontrado;
        } else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("Odontologos: " + odontologoRepository.findAll());
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        Optional<Odontologo> odontologoEncontrado = buscarPorId(odontologo.getId());
        if(odontologoEncontrado.isPresent()){
            //para poder modificar es lo mismo q guardar ya q se sobre escribe
            odontologoRepository.save(odontologo);
        }
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        Optional<Odontologo> odontologoEncontrado = buscarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            odontologoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("odontologo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        return odontologoRepository.findByNombre(nombre);
    }

    @Override
    public List<Odontologo> ordenarPorApellido() {
        return odontologoRepository.ordenarPorApellido();
    }
}
