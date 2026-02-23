import { Routes } from '@angular/router';
import { Layout } from './layout/layout/layout';

export const routes: Routes = [
  // Public Routes
  {
    path: 'signup',
    loadComponent: () =>
      import('./features/auth/pages/signup/signup').then((m) => m.Signup),
  },

  // Layout
  {
    path: '',
    component: Layout,
    children: [],
  },
];
