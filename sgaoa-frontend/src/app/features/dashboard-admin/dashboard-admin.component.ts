import { Component, inject, signal, computed } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

interface ActivityItem {
  id: number;
  name: string;
  action: string;
  date: string;
  initials: string;
  avatarColor: string;
}

@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './dashboard-admin.component.html',
  styleUrl: './dashboard-admin.component.scss'
})
export class DashboardAdminComponent {
  private router = inject(Router);

  // Stats signals
  totalDossiers = signal(0);
  adoptionsEnCours = signal(0);
  adoptionsFinalisees = signal(0);
  demandesEnAttente = signal(0);
  utilisateursActifs = signal(0);
  dossiersRejetes = signal(0);

  // Date du jour
  currentDate = computed(() => {
    return new Date().toLocaleDateString('fr-FR', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  });

  // Activité récente (données fictives)
  recentActivities = signal<ActivityItem[]>([
    { id: 1, name: 'Jean Kouassi', action: 'a créé un nouveau dossier', date: 'Il y a 5 min', initials: 'JK', avatarColor: 'bg-blue-500' },
    { id: 2, name: 'Marie Aho', action: 'a finalisé une adoption', date: 'Il y a 15 min', initials: 'MA', avatarColor: 'bg-green-500' },
    { id: 3, name: 'Kofi Mensah', action: 'a soumis une demande', date: 'Il y a 30 min', initials: 'KM', avatarColor: 'bg-purple-500' },
    { id: 4, name: 'Awa Diallo', action: 'a mis à jour son profil', date: 'Il y a 1 heure', initials: 'AD', avatarColor: 'bg-orange-500' },
    { id: 5, name: 'Paul Togo', action: 'a rejeté un dossier', date: 'Il y a 2 heures', initials: 'PT', avatarColor: 'bg-red-500' }
  ]);

  // Statut système
  systemStatus = signal({
    api: true,
    database: true,
    storage: true
  });

  constructor() {
    // Simuler le chargement des stats (à remplacer par un vrai appel API)
    this.loadStats();
  }

  private loadStats(): void {
    // TODO: Remplacer par un appel API réel
    this.totalDossiers.set(156);
    this.adoptionsEnCours.set(23);
    this.adoptionsFinalisees.set(89);
    this.demandesEnAttente.set(12);
    this.utilisateursActifs.set(45);
    this.dossiersRejetes.set(8);
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
