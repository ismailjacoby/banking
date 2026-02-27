import { Routes } from '@angular/router';
import { CreateAccount } from './pages/create-account/create-account';
import { AccountList } from './pages/account-list/account-list';

export const accountRoutes: Routes = [
  { path: '', component: AccountList },
  { path: 'create', component: CreateAccount },
];
