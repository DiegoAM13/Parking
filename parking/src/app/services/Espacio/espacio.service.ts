import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ModelEspacio } from 'src/app/models/modelEspacio';

@Injectable({
  providedIn: 'root'
})
export class EspacioService {

  private apiUrl = 'http://localhost:8080/espacios';

  constructor(private http: HttpClient) {}

  getEspacios() {
    return this.http.get<any[]>(this.apiUrl);
  }

  ObtenerTipo(tipo: string):Observable<any[]> {
     return this.http.get<any[]>(`${this.apiUrl}/${tipo}`)
  }

  EspaciosDisponibles(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/disponibles`)
  }

  EspaciosOcupados(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/ocupados`)
  }

  crearEspacio(EspacioData: ModelEspacio){
    return this.http.post(`${this.apiUrl}/guardar`, EspacioData, { responseType: 'text' })
  }

  ActualizarEspacio( EspacioData: ModelEspacio)
  {
    return this.http.put(`${this.apiUrl}/actualizar/${EspacioData.id}`, EspacioData,{ responseType: 'text' }  )
  }

  EliminarEspacio(id:number)
  {
    return this.http.delete(`${this.apiUrl}/eliminar/${id}`,{ responseType: 'text' })
  }
  

 



}
