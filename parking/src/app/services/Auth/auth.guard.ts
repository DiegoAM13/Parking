
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../User/login.service';  
import { Inject, inject } from '@angular/core';


export const AuthGuard: CanActivateFn = () => {
    const loginService = inject(LoginService);
    const router = inject(Router);

    if(loginService.isLoggedIn()){
        return true;
    }else{
        router.navigate(['/login']);
        return false;
    }
};