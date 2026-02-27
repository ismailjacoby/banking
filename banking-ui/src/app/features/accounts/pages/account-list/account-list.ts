import { Component, inject, OnInit } from '@angular/core';
import { Account } from '../../services/account';
import { Router, RouterLink } from '@angular/router';
import { AccountResponse } from '../../models/account';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './account-list.html',
  styleUrl: './account-list.css',
})
export class AccountList implements OnInit {
  private accountService = inject(Account);
  private router = inject(Router);

  accounts: AccountResponse[] = [];
  loading = true;
  errorMessage = '';

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts() {
    this.accountService.getUserAccounts().subscribe({
      next: (data) => {
        this.accounts = data;
        this.errorMessage = '';
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to load accounts.';
        this.loading = false;
      },
    });
  }
}
