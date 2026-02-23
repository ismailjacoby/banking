import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { SignupRequest } from '../models/auth';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private apiUrl = `${environment.apiUrl}/auth`;

  private http = inject(HttpClient);

  signup(data: SignupRequest) {
    return this.http.post<void>(`${this.apiUrl}/signup`, data);
  }
}
