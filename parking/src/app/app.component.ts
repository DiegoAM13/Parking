import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { LoginService } from './services/User/login.service';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'parking';

  showNavbar = false;
  role: string | null = null; 
  show = false
  

  constructor( private router: Router, private loginService: LoginService){
    this.router.events
    .pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    )
    .subscribe((event:NavigationEnd) =>{
      const currenRoute = event.urlAfterRedirects;
      const dashboardReserva = currenRoute.includes('/dashboard') || currenRoute.includes('/reservas') || currenRoute.includes('/regespacio') || currenRoute.includes('/regreserva');

      this.role = this.loginService.getUserRole();
      const isAdmin = this.role !== '';

      this.showNavbar = dashboardReserva ;
    })

  }

}
