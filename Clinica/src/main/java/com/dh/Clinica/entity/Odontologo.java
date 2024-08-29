package com.dh.Clinica.entity;

import com.dh.Clinica.utils.GsonProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numeroDeMatricula;
    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.REMOVE)
    //@JsonManagedReference(value = "odontologo-turno")
    @JsonIgnore
    private Set<Turno> turnoSet;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
