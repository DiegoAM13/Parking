import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { LoginService } from '../User/login.service';  
import { Router } from '@angular/router';

export const roleGuard = (requiredRole: string): CanActivateFn =>{
    return () => {
        const loginService = inject(LoginService);
        const router = inject(Router);

        const userRole = loginService.getUserRole();
        if(userRole === requiredRole){
            return true;
        }else{
            router.navigate(['/reservas'])
            return false;
        }
    };
};