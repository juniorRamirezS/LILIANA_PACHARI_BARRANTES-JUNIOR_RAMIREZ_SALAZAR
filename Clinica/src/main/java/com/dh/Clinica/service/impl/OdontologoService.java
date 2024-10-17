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
            logger.info("Odontologo registrado: " + odontologo);
            return odontologoRepository.save(odontologo);
        } else {
            throw new BadRequestException("Error al guardar odontologo");
        }
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);
        if (odontologoEncontrado.isPresent()) {
            logger.info("Odontologo encontrado por Id: " + odontologoEncontrado);
            return odontologoEncontrado;
        } else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        logger.info("Lista de odontologos: " + odontologoRepository.findAll());
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        Optional<Odontologo> odontologoEncontrado = buscarPorId(odontologo.getId());
        if(odontologoEncontrado.isPresent()){
            //para poder modificar es lo mismo q guardar ya q se sobre escribe
            logger.info("Odontologo modificado: " + odontologoEncontrado);
            odontologoRepository.save(odontologo);
        }
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        Optional<Odontologo> odontologoEncontrado = buscarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            logger.info("Odontologo a eliminar: " + odontologoEncontrado);
            odontologoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("odontologo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        logger.info("Odontologo encontrado por nombre: " + odontologoRepository.findByNombre(nombre));
        return odontologoRepository.findByNombre(nombre);
    }

    @Override
    public List<Odontologo> ordenarPorApellido() {
        logger.info("Odontologos ordenados: " + odontologoRepository.ordenarPorApellido());
        return odontologoRepository.ordenarPorApellido();
    }
}
