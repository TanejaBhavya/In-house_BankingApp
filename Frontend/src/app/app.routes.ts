import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
    {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  { path: 'dashboard', component: DashboardComponent },
{
  path: 'add-account',
  loadComponent: () =>
    import('./manager/add-account/add-account.component')
      .then(m => m.AddAccountComponent)
},
{
  path: 'pending-requests',
  loadComponent: () =>
    import('./manager/pending-requests/pending-requests.component')
      .then(m => m.PendingRequestsComponent)
},
{
  path: 'delete-account',
  loadComponent: () =>
    import('./manager/delete-account/delete-account.component')
      .then(m => m.DeleteAccountComponent)
},
{
  path: 'transactions/:id',
  loadComponent: () =>
    import('./transactions/transactions.component')
      .then(m => m.TransactionsComponent)
},
{
  path: 'summary/:id',
  loadComponent: () =>
    import('./summary/summary.component')
      .then(m => m.SummaryComponent)
},
{
  path: 'update/:id',
  loadComponent: () =>
    import('./update/update.component')
      .then(m => m.UpdateComponent)
}


];
