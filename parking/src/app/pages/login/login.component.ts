import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/models/loginUser';
import { LoginService } from 'src/app/services/User/login.service'; 

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

 loginData: LoginUser = {
    userName: '',
    password: ''
  };
  errorMessage = '';

  constructor(private loginService: LoginService, private router: Router){}


  onLogin(): void {
    this.loginService.login(this.loginData).subscribe({
      next: token => {
        this.loginService.saveToken(token);
        const userRole = this.loginService.getUserRole();
        if(userRole === 'ROLE_ADMIN')
        {
          this.router.navigate(['/dashboard']);
        }
        else
        {
          this.router.navigate(['/reservas']);
        }
        
      },
      error: err => {
        this.errorMessage = 'Credenciales invalidas';
      }
    });
  }


}
