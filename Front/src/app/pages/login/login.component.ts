import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/auth/login.service';
import { loginRequest } from 'src/app/services/auth/loginRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent  {

  loginError:string="";
  loginForm=this.formBuilder.group({
    usuario: ['', Validators.required],
    contrasena:['', Validators.required]
  })
  constructor(private formBuilder:FormBuilder, private router:Router, private loginService:LoginService) {}

get usuario()
{
  return this.loginForm.controls.usuario
}
get contrasena()
{
  return this.loginForm.controls.contrasena
}

login(){

  if(this.loginForm.valid){
    this.loginService.login(this.loginForm.value as loginRequest).subscribe({
      next: (userData) =>{
        console.log(userData);
      },
      error: (errorData) => {
        console.error(errorData);
        this.loginError=errorData;
      },
      complete: () =>{
        console.info("login completo")
        this.router.navigateByUrl("/dashboard")
        this.loginForm.reset()
      }
    })
    
  }
  else{
    this.loginForm.markAllAsTouched();
    alert("error")
  }

}


}
