package com.Parking.Seguridad.enums;

public enum TipoVehiculo {

    MOTO, CARRO;

    public static boolean esValido(String tipo) {
        for (TipoVehiculo t : values()) {
            if (t.name().equalsIgnoreCase(tipo)) {
                return true;
            }
        }
        return false;
    }
}
