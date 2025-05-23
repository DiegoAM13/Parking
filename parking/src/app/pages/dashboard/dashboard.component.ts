import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/User/login.service'; 
import { Router } from '@angular/router';
import { EspacioService } from 'src/app/services/Espacio/espacio.service';
import { forkJoin } from 'rxjs';
declare var bootstrap: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
 

  filtroTipo: string = '';
  filtroEstado: string = '';

  espacioSeleccionado: any = {};
  idEliminar: number | null = null;

  espacios: any[] = [];
  constructor(private loginSservice: LoginService, private router: Router,private espacioService: EspacioService){}
 
  
  ngOnInit(): void {

    this.filtrarEspacios();
   
  }

  filtrarEspacios()
  {
   
    if(this.filtroTipo !== '' && this.filtroEstado === '')
    {
      this.espacioService.ObtenerTipo(this.filtroTipo).subscribe({
        next:(data) => this.espacios = data,
        error: (err) => console.error('Error cargando espacios:', err)
      })
    } 
    else if(this.filtroTipo !== '' && this.filtroEstado === 'disponibles')
    {
      this.espacioService.ObtenerTipo(this.filtroTipo).subscribe({
        next:(data =>{ this.espacios = data.filter(e => e.disponible === true); 

        }),
        error: (err) => console.error('Error cargando espacios:', err)
      })
    }
    else if(this.filtroTipo !== '' && this.filtroEstado === 'ocupados')
    {
      this.espacioService.ObtenerTipo(this.filtroTipo).subscribe({
        next:(data =>{ this.espacios = data.filter(e => e.disponible === false); 

        }),
        error: (err) => console.error('Error cargando espacios:', err)
      })
    }
    else if(this.filtroTipo === '' && this.filtroEstado ==='disponibles')
    {
      this.espacioService.EspaciosDisponibles().subscribe({
        next:(data) => this.espacios = data,
        error: (err) => console.error('Error cargando espacios:', err)
      })
    }
    else if(this.filtroTipo === '' && this.filtroEstado ==='ocupados')
    {
      this.espacioService.EspaciosOcupados().subscribe({
        next:(data) => this.espacios = data,
        error: (err) => console.error('Error cargando espacios:', err)
      })
    }
    else
    {
        this.espacioService.getEspacios().subscribe({
        next:(data) => this.espacios = data,
        error: (err) => console.error('Error cargando espacios:', err)
      });
    
    }

    
    
  }


  

  regEspacio(){
    this.router.navigate(['/regespacio'])
  }

  ActualizarEspacio(espacio:number){

    alert('Espacio: ' + espacio)

  }
  abrirModal(espacio: any) {
  // Clonar el objeto para no afectar directamente el array
   this.espacioSeleccionado = { ...espacio };
}

  guardarCambios()
  {

    
    this.espacioService.ActualizarEspacio(this.espacioSeleccionado).subscribe (() =>{
      this.filtrarEspacios();
    });

    const modalElement = document.getElementById('editarModal');
    const modalBootstrap = bootstrap.Modal.getInstance(modalElement!);
    modalBootstrap?.hide();
    

  }

  Eliminar(id: number){

    this.idEliminar = id;

  }

  ConfirmarEliminar()
  {
    if (this.idEliminar !== null) {
      
      this.espacioService.EliminarEspacio(this.idEliminar).subscribe({
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

  EsAdmin():boolean {
       
    return this.loginSservice.getUserRole() === 'ROLE_ADMIN';
  }

  CrearEspacio(espacio: number,tipo: string ){

    this.router.navigate(['/regreserva'],{
      queryParams: {
        espacio: espacio,
        tipo: tipo
      }
    });

  }

  


}
