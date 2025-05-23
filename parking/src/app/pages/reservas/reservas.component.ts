import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Salida } from 'src/app/models/Salida';
import { ReservasService } from 'src/app/services/Reserva/reservas.service';
import { LoginService } from 'src/app/services/User/login.service';
declare var bootstrap: any;

@Component({
  selector: 'app-reservas',
  templateUrl: './reservas.component.html',
  styleUrls: ['./reservas.component.css']
})
export class ReservasComponent implements OnInit {
 
  
  reservas: any[] = [];
  filtroTipo: string = '';

  reservaseleccionada: any = {};
  placaEliminar: string = ''

  Liquidacion?: Salida
  error= '';
  success = '';


  nuevaPlaca:string = '';
  nuevoEspacio?: number; 
  nuevoTipo:String = '';

  ReservaData = {
      placa: '',
      tipo: '',
      espacio_id: Number

    
    };

  constructor( private router: Router, private reservaService: ReservasService, private loginService: LoginService){}
   
  ngOnInit(): void {

    this.filtrarEspacios();
   
  }

   ValidarPlaca(placa: string): boolean{

    const regex = /^[A-Z]{3}[0-9]{3}$/;
    return regex.test(placa);
  }


  filtrarEspacios(){

    if(this.filtroTipo !== '')
    {
      this.reservaService.ObtenerTipo(this.filtroTipo).subscribe({
        next:(data) => this.reservas = data,
        error: (err) => console.error('Error cargando espacios:', err)
      })
    }
    else{
        
      this.reservaService.getReservas().subscribe({
        next:(data) => this.reservas = data,
        error: (err) => console.error('Error cargando espacios:', err)
      });
    } 
  }
  
  

  EsAdmin():boolean {
       
    return this.loginService.getUserRole() === 'ROLE_ADMIN';
  }

  Eliminar(placa: string){

    this.placaEliminar = placa;

   

  }

  ConfirmarEliminar()
  {
    if (this.placaEliminar !== null) {
      
      this.reservaService.EliminarReserva(this.placaEliminar).subscribe({
        next: () => {
            this.filtrarEspacios();
        },
        error: (err) => console.error('Error al eliminar', err)
      });

    const modalElement = document.getElementById('confirmarEliminarModal');
    const modalBootstrap = bootstrap.Modal.getInstance(modalElement!);
    modalBootstrap?.hide();

    }

  }

  Liquidar(placa:string){

    
    this.reservaService.SacarVehiculo(placa).subscribe({
      next: (data) => {
        this.Liquidacion = data;

        setTimeout(() => {
          
          this.reservaService.EliminarReserva(data.placa).subscribe({
            next: () => {
            this.filtrarEspacios();
            },
            error: (err) => console.error('Error al eliminar', err)
          });

        }, 2000); // Espera 5 segundos
        
      },
      error:(err) => {
        this.error = 'Error al sacar el vehículo: ' + (err.error?.message || err.message);
        this.Liquidacion = undefined;
      }
    })
  }

  abrirModal(reserva: any) {
  // Clonar el objeto para no afectar directamente el array
   this.reservaseleccionada = { ...reserva };
}

  guardarCambios()
  {
    
    if( this.ReservaData.placa !== '' && !this.ValidarPlaca(this.ReservaData.placa))
    {
      this.error = 'Placa invalida'
    }
    else{
      this.reservaService.ActualizarReserva( this.reservaseleccionada.placa, this.ReservaData).subscribe({
        next: (res) => {
        this.success ="Reserva actualizada correctamente";
        this.filtrarEspacios();
  
        },
        error: (err) => {
        this.error = err.error;
        }
      })

    }
   

    
  }

  


    
  
}
