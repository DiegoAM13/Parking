<<<<<<< HEAD
package com.Parking.Seguridad.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime horaLlegada;

    @Column(nullable = true)
    private LocalDateTime horaSalida;

    @ManyToOne
    @JoinColumn(name = "espacio_id", nullable = false)
    private Espacio espacio;
}
=======
package com.Parking.Seguridad.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime horaLlegada;

    @Column(nullable = true)
    private LocalDateTime horaSalida;

    @ManyToOne
    @JoinColumn(name = "espacio_id", nullable = false)
    private Espacio espacio;
}
>>>>>>> e093025465f59ccaef8ce613cba2b5951f0e2cb8
