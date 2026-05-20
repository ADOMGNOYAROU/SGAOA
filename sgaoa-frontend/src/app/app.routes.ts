import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { DashboardAdminComponent } from './features/dashboard-admin/dashboard-admin.component';

export const routes: Routes = [
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'dashboard',
        component: LayoutComponent,
        children: [
            {
                path: '',
                component: DashboardAdminComponent
            }
        ]
    },
    {
        path: '',
        redirectTo: '/auth/login',
        pathMatch: 'full'
    }
];
