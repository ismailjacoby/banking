import { computed, inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthResponse, LoginRequest, SignupRequest } from '../models/auth';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private apiUrl = `${environment.apiUrl}/auth`;

  private http = inject(HttpClient);
  private router = inject(Router);

  private _token = signal<string | null>(localStorage.getItem('token'));
  private _username = signal<string | null>(localStorage.getItem('username'));
  private _role = signal<string | null>(localStorage.getItem('role'));

  isAuthenticated = computed(() => !!this._token());
  userRole = computed(() => this._role());
  username = computed(() => this._username());

  signup(data: SignupRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/signup`, data, {
      responseType: 'text',
    });
  }

  login(data: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, data).pipe(
      tap((response) => {
        this._token.set(response.token);
        this._username.set(response.username);
        this._role.set(response.role);

        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        localStorage.setItem('role', response.role);
      }),
    );
  }

  logout(): void {
    this._token.set(null);
    this._username.set(null);
    this._role.set(null);

    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');

    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this._token();
  }
}
