import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { DashboardAdminComponent } from './features/dashboard-admin/dashboard-admin.component';
import { DashboardAdoptantComponent } from './features/dashboard-adoptant/dashboard-adoptant.component';
import { DashboardAgentComponent } from './features/dashboard-agent/dashboard-agent.component';
import { DashboardPresidentComponent } from './features/dashboard-president/dashboard-president.component';
import { DashboardSecretaireComponent } from './features/dashboard-secretaire/dashboard-secretaire.component';
import { DossiersComponent } from './features/dossiers/dossiers.component';
import { OrphelinsComponent } from './features/orphelins/orphelins.component';
import { AdoptantsComponent } from './features/adoptants/adoptants.component';
import { UtilisateursComponent } from './features/utilisateurs/utilisateurs.component';
import { RapportsComponent } from './features/rapports/rapports.component';
import { ParametresComponent } from './features/parametres/parametres.component';
import { ProfilAdoptantComponent } from './features/profil-adoptant/profil-adoptant.component';
import { MesDemandesComponent } from './features/mes-demandes/mes-demandes.component';
import { MesDocumentsComponent } from './features/mes-documents/mes-documents.component';
import { NotificationsComponent } from './features/notifications/notifications.component';
import { ParametresAdoptantComponent } from './features/parametres-adoptant/parametres-adoptant.component';
import { LayoutAdoptantComponent } from './features/layout-adoptant/layout-adoptant.component';

export const routes: Routes = [
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'auth/register',
        component: RegisterComponent
    },
    {
        path: 'dashboard',
        component: LayoutComponent,
        children: [
            {
                path: '',
                component: DashboardAdminComponent
            },
            {
                path: 'agent',
                component: DashboardAgentComponent
            },
            {
                path: 'president',
                component: DashboardPresidentComponent
            },
            {
                path: 'secretaire',
                component: DashboardSecretaireComponent
            },
            {
                path: 'dossiers',
                component: DossiersComponent
            },
            {
                path: 'orphelins',
                component: OrphelinsComponent
            },
            {
                path: 'adoptants',
                component: AdoptantsComponent
            },
            {
                path: 'utilisateurs',
                component: UtilisateursComponent
            },
            {
                path: 'rapports',
                component: RapportsComponent
            },
            {
                path: 'parametres',
                component: ParametresComponent
            }
        ]
    },
    {
        path: 'dashboard/adoptant',
        component: LayoutAdoptantComponent,
        children: [
            {
                path: '',
                component: DashboardAdoptantComponent,
                pathMatch: 'full'
            },
            {
                path: 'profil',
                component: ProfilAdoptantComponent
            },
            {
                path: 'demandes',
                component: MesDemandesComponent
            },
            {
                path: 'documents',
                component: MesDocumentsComponent
            },
            {
                path: 'notifications',
                component: NotificationsComponent
            },
            {
                path: 'parametres',
                component: ParametresAdoptantComponent
            }
        ]
    },
    {
        path: '',
        redirectTo: '/auth/login',
        pathMatch: 'full'
    }
];
