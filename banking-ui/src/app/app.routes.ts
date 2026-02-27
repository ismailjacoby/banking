import { Routes } from '@angular/router';
import { Layout } from './layout/layout/layout';

export const routes: Routes = [
  // Public Routes
  {
    path: 'signup',
    loadComponent: () =>
      import('./features/auth/pages/signup/signup').then((m) => m.Signup),
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login/login').then((m) => m.Login),
  },

  // Layout
  {
    path: '',
    component: Layout,
    children: [
      {
        path: 'accounts',
        loadChildren: () =>
          import('./features/accounts/accounts.routes').then(
            (m) => m.accountRoutes,
          ),
      },
    ],
  },
];
