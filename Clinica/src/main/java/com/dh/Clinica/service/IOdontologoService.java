package com.dh.Clinica.service;

import com.dh.Clinica.entity.Odontologo;

import java.util.Optional;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo);

    Optional<Odontologo> buscarPorId(Integer id);
}
