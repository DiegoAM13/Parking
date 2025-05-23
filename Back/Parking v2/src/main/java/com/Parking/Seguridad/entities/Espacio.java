package com.Parking.Seguridad.entities;

import com.Parking.Seguridad.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "espacio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

/*    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipo;*/

    @Column(nullable = false)
    private Boolean disponible;


}
