import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './services/Auth/auth.guard'; 
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LoginComponent } from './pages/login/login.component';

import { roleGuard } from './services/Auth/role.guard'; 
import { RegisterComponent } from './pages/register/register.component';
import { ReservasComponent } from './pages/reservas/reservas.component';
import { RegespacioComponent } from './pages/regespacio/regespacio.component';
import { RegreservaComponent } from './pages/regreserva/regreserva.component';


const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {path:'reservas', component: ReservasComponent, canActivate: [AuthGuard]},
  { path: 'regespacio', component: RegespacioComponent, canActivate: [AuthGuard, roleGuard('ROLE_ADMIN')] },
  { path: 'regreserva', component: RegreservaComponent, canActivate: [AuthGuard, roleGuard('ROLE_ADMIN')] },
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
