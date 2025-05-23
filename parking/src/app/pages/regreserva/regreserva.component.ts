import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ModelReserva } from 'src/app/models/modelReserva';
import { ReservasService } from 'src/app/services/Reserva/reservas.service';

@Component({
  selector: 'app-regreserva',
  templateUrl: './regreserva.component.html',
  styleUrls: ['./regreserva.component.css']
})
export class RegreservaComponent  implements OnInit{

  
  ReservaData: ModelReserva = {
    placa: '',
    tipo: '',
    espacio_id:0
  
  };

  

  error = '';
  success = '';

  
  constructor(private router: Router, private route: ActivatedRoute, private reservaService: ReservasService){}

  
  ngOnInit(): void {
      
    this.route.queryParams.subscribe(params => {
      this.ReservaData.espacio_id = +params['espacio'];
      this.ReservaData.tipo = params['tipo'];
    })
  }

  volver(){

    this.router.navigate(['/dashboard'])
  }

  ValidarPlaca(placa: string): boolean{

    const regex = /^[A-Z]{3}[0-9]{3}$/;
    return regex.test(placa);
  }

  register()
  {

    if(!this.ValidarPlaca(this.ReservaData.placa))
    {
      this.error = 'Placa invalida'
    }
    else
    {
      this.reservaService.crearReserva(this.ReservaData).subscribe({
        next: (res) => {
        this.success ="reserva registrado correctamente";
  
        },
        error: (err) => {
        this.error = err.error;
        }
      })
    }
    
  }
}
