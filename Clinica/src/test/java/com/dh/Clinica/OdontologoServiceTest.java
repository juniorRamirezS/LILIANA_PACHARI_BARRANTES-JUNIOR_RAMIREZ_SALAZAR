package com.dh.Clinica;

import com.dh.Clinica.entity.Odontologo;
import com.dh.Clinica.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class OdontologoServiceTest {
    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    OdontologoService odontologoService;
    Odontologo odontologo;
    Odontologo odontologoDesdeDb;

    @BeforeEach
    void cargarDatos(){
        odontologo = new Odontologo();
        odontologo.setApellido("Rodriguez");
        odontologo.setNombre("Rosa");
        odontologo.setNumeroDeMatricula("48974646");

        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
    }

    @Test
    @DisplayName("Testear que un odontologo fue cargado correctamente")
    void caso1() {
        assertNotNull(odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo pueda acceder por id")
    void caso2() {
        //Dado
        Integer id = odontologoDesdeDb.getId();
        //cuando
        Odontologo odontologoRecuperado = odontologoService.buscarPorId(id).get();
        // entonces
        assertEquals(id, odontologoRecuperado.getId());
    }

    @Test
    @DisplayName("Listar todos los odontologo")
    void caso3() {
        //Dado
        List<Odontologo> odontologos;
        // cuando
        odontologos = odontologoService.buscarTodos();
        // entonces
        assertFalse(odontologos.isEmpty());
    }

}
