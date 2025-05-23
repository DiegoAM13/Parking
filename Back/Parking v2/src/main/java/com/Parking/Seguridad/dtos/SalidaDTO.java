package com.Parking.Seguridad.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalidaDTO {

    private String placa;
    private LocalDateTime horaSalida;
    private BigDecimal valorAPagar;
}
