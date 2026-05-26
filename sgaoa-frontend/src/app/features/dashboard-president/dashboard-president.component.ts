import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-president',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-president.component.html',
  styleUrl: './dashboard-president.component.scss'
})
export class DashboardPresidentComponent {
  // Statistiques Président
  dossiersAValider = signal(7);
  adoptionsEnCours = signal(15);
  reunionsPlanifiees = signal(3);
  decisionsEnAttente = signal(4);

  // Dossiers en attente de validation
  dossiersEnAttente = signal([
    { id: 1, orphelin: 'Amadou Diallo', adoptant: 'Famille Martin', type: 'Adoption plénière', date: '25/05/2026', priorite: 'haute' },
    { id: 2, orphelin: 'Fatou Traoré', adoptant: 'Famille Dupont', type: 'Adoption simple', date: '24/05/2026', priorite: 'normale' },
    { id: 3, orphelin: 'Ibrahim Sanogo', adoptant: 'Famille Bernard', type: 'Adoption plénière', date: '23/05/2026', priorite: 'haute' }
  ]);

  // Prochaines réunions
  prochainReunions = signal([
    { id: 1, titre: 'Comité d\'adoption', date: '28/05/2026', heure: '14:00', participants: 5 },
    { id: 2, titre: 'Revue des dossiers', date: '30/05/2026', heure: '10:00', participants: 3 },
    { id: 3, titre: 'Assemblée générale', date: '05/06/2026', heure: '09:00', participants: 12 }
  ]);

  constructor(private router: Router) {}

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  validerDossier(id: number) {
    console.log('Valider dossier:', id);
  }

  rejeterDossier(id: number) {
    console.log('Rejeter dossier:', id);
  }
}
