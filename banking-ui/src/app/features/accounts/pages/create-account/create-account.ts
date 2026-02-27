import { Component, inject } from '@angular/core';
import {
  FormGroup,
  ReactiveFormsModule,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { Account } from '../../services/account';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountCreationRequest } from '../../models/account';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-account.html',
  styleUrl: './create-account.css',
})
export class CreateAccount {
  errorMessage = '';
  successMessage = '';

  private fb = inject(FormBuilder);
  private accountService = inject(Account);
  private router = inject(Router);

  form = this.fb.group({
    accountType: ['', Validators.required],
    currency: ['', Validators.required],
  });

  onSubmit() {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const data = this.form.getRawValue() as AccountCreationRequest;

    this.accountService.createAccount(data).subscribe({
      next: () => {
        this.successMessage = 'Account created successfully.';
        setTimeout(() => {
          this.router.navigate(['/accounts']);
        }, 1200);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to create account.';
      },
    });
  }
}
