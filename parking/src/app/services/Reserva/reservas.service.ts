import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ModelReserva } from 'src/app/models/modelReserva';
import { Salida } from 'src/app/models/Salida';


@Injectable({
  providedIn: 'root'
})
export class ReservasService {

 private apiUrl = 'http://localhost:8080/reservas';
 
  constructor(private http: HttpClient) {}
 
  getReservas() {
    return this.http.get<any[]>(this.apiUrl);
  }

  ObtenerTipo(tipo: string):Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}/tipo/${tipo}`)
  }

  crearReserva(ReservaData: ModelReserva){
      return this.http.post(`${this.apiUrl}/guardar`, ReservaData, { responseType: 'text' })
  }

  EliminarReserva(placa:string)
  {
    return this.http.delete(`${this.apiUrl}/eliminar/${placa}`,{ responseType: 'text' })
  }

  ActualizarReserva( placa:string, ReservaData:any)
  {
    return this.http.put(`${this.apiUrl}/actualizar/${placa}`, ReservaData,{ responseType: 'text' }  )
  }

  SacarVehiculo(placa:string): Observable<Salida> {
    return this.http.put<Salida>(`${this.apiUrl}/sacar/${placa}`, {} )
  }

  

  


}
