import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  register(registerData: any) {

    return this.http.post(`${this.apiUrl}/register`, registerData, { responseType: 'text' });
  }
}
