package com.dh.Clinica.repository;

import com.dh.Clinica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    List<Odontologo> findByNombre(String nombre);

    @Query("Select o from Odontologo o ORDER BY o.apellido ASC")
    List<Odontologo> ordenarPorApellido();
}
