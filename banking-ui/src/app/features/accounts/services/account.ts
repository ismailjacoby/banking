import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { AccountCreationRequest, AccountResponse } from '../models/account';

@Injectable({
  providedIn: 'root',
})
export class Account {
  private apiUrl = `${environment.apiUrl}/accounts`;

  private http = inject(HttpClient);

  createAccount(data: AccountCreationRequest) {
    return this.http.post<AccountResponse>(this.apiUrl, data);
  }

  getUserAccounts() {
    return this.http.get<AccountResponse[]>(this.apiUrl);
  }
}
