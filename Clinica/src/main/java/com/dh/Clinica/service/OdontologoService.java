package com.dh.Clinica.service;

import com.dh.Clinica.dao.IDao;
import com.dh.Clinica.model.Odontologo;

import java.util.List;

public class OdontologoService {
    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoIDao.guardar(odontologo);
    }

    public Odontologo buscarPorId(Integer id){
        return odontologoIDao.buscarPorId(id);
    }
    public List<Odontologo> buscarTodos(){
        return odontologoIDao.listaTodos();
    }
}
