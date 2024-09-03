package com.dh.Clinica.service.impl;

import com.dh.Clinica.entity.Odontologo;
import com.dh.Clinica.repository.IOdontologoRepository;
import com.dh.Clinica.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        return odontologoRepository.findById(id);
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return odontologoRepository.findAll();
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) {
        //para poder modificar es lo mismo q guardar ya q se sobre escribe
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        odontologoRepository.deleteById(id);

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
