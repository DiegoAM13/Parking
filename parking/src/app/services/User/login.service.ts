import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginUser } from 'src/app/models/loginUser'; 
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  login(loginData: LoginUser): Observable<string>
  {
    return this.http.post(this.apiUrl + '/login', loginData, {responseType: 'text'});
  }

  saveToken(token: string): void {
    localStorage.setItem('token',token);
  }

  getToken(): string |null{
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean{
    return !!localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if(!token) return null;
    try {
      const decoded: any = jwtDecode(token);
      return decoded.role || null;
    }catch {
      return null;
    }
  }
}
