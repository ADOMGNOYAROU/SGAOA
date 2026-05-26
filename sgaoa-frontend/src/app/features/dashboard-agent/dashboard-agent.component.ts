import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-agent',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-agent.component.html',
  styleUrl: './dashboard-agent.component.scss'
})
export class DashboardAgentComponent {
  // Statistiques Agent Social
  orphelinsAssignes = signal(12);
  visitesPlanifiees = signal(5);
  rapportsEnAttente = signal(3);
  dossiersActifs = signal(8);

  // Activités récentes
  recentActivities = signal([
    { id: 1, type: 'visite', description: 'Visite programmée - Orphelin #45', date: 'Aujourd\'hui', status: 'pending' },
    { id: 2, type: 'rapport', description: 'Rapport de suivi soumis', date: 'Hier', status: 'completed' },
    { id: 3, type: 'dossier', description: 'Nouveau dossier assigné', date: 'Il y a 2 jours', status: 'new' }
  ]);

  // Prochaines visites
  prochainVisites = signal([
    { id: 1, orphelin: 'Amadou Diallo', lieu: 'Famille Koné', date: '28/05/2026', heure: '10:00' },
    { id: 2, orphelin: 'Fatou Traoré', lieu: 'Centre d\'accueil', date: '29/05/2026', heure: '14:30' },
    { id: 3, orphelin: 'Ibrahim Sanogo', lieu: 'Famille Coulibaly', date: '30/05/2026', heure: '09:00' }
  ]);

  constructor(private router: Router) {}

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}
