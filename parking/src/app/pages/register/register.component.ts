import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/models/loginUser';
import { RegisterService } from 'src/app/services/User/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent  {

  loginData = {
    userName: '',
    password: '', 
    confirmPassword: ''
  };

  error = '';
  success = '';

  constructor(private registerService: RegisterService, private router: Router){}

  
  register() {

    if(this.loginData.userName.length > 15 || this.loginData.password.length > 15)
    {
      this.error = 'El usuario y la contraseña no deben tener más de 15 caracteres.';
      this.success = '';
      return;
    }
    if (this.loginData.password !== this.loginData.confirmPassword) {
      this.error = 'Las contraseñas no coinciden.';
      this.success = '';
      return;
    }

    const registerData = {

      userName: this.loginData.userName,
      password: this.loginData.password
    };




    this.registerService.register(registerData).subscribe({
      next: (res) => {
        this.success = 'Usuario registrado correctamente';
        this.error = '';
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.error = err.error;
        this.success =  '';
      }
    });
  }
}
