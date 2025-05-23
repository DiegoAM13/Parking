import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {ModelEspacio} from 'src/app/models/modelEspacio';
import { EspacioService } from 'src/app/services/Espacio/espacio.service';

@Component({
  selector: 'app-regespacio',
  templateUrl: './regespacio.component.html',
  styleUrls: ['./regespacio.component.css']
})
export class RegespacioComponent  {

  TipoEspacio: string = '';

  EspacioData: ModelEspacio = {
    tipo: '',
    disponible: true
    
  };

  error = '';
  success = '';

 constructor(private espacioService: EspacioService, private router: Router){}  



 register()
 {
   if(this.EspacioData.tipo === '')
    this.error = "Debe elegir un tipo"
  
   this.espacioService.crearEspacio(this.EspacioData).subscribe({
     next: (res) => {
      this.success ="Espacio registrado correctamente";
  
     },
     error: (err) => {
      this.error = err.error;
    }
   })
  
  }
  volver(){

    this.router.navigate(['/dashboard'])
  }

}
