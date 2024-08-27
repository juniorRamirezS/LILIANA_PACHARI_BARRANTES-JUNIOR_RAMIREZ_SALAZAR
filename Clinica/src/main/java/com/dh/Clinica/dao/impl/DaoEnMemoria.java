package com.dh.Clinica.dao.impl;

import com.dh.Clinica.dao.IDao;
import com.dh.Clinica.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DaoEnMemoria implements IDao<Odontologo> {
    public static final Logger logger = LoggerFactory.getLogger(DaoEnMemoria.class);
    private List<Odontologo> odontologos = new ArrayList<>();
    @Override
    public Odontologo guardar(Odontologo odontologo) {
        odontologo.setId(odontologos.size()+1);
        odontologos.add(odontologo);
        logger.info("Odontologo guardado en memoria " +odontologo);
        return odontologo;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Odontologo odontologoARetornar = null;
        for(Odontologo odontologo : odontologos){
            if(odontologo.getId()== id){
                odontologoARetornar = odontologo;
            }
        }
        if(odontologoARetornar!=null){
            logger.info("Odontologo encontrado "+ odontologoARetornar);
        }else logger.info("Odontologo no encontrado");
        return odontologoARetornar;
    }

    @Override
    public List<Odontologo> listaTodos() {
        return null;
    }
}
