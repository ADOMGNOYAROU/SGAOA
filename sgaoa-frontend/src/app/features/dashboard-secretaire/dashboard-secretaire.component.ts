import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-secretaire',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-secretaire.component.html',
  styleUrl: './dashboard-secretaire.component.scss'
})
export class DashboardSecretaireComponent {
  // Statistiques Secrétaire
  dossiersATraiter = signal(9);
  documentsEnAttente = signal(12);
  courriersRecus = signal(5);
  archivesRecentes = signal(23);

  // Documents récents
  documentsRecents = signal([
    { id: 1, nom: 'Acte de naissance - Amadou D.', type: 'Acte', date: '26/05/2026', status: 'nouveau' },
    { id: 2, nom: 'Certificat médical - Fatou T.', type: 'Certificat', date: '25/05/2026', status: 'traite' },
    { id: 3, nom: 'Jugement d\'adoption - Ibrahim S.', type: 'Jugement', date: '24/05/2026', status: 'archive' },
    { id: 4, nom: 'Enquête sociale - Marie K.', type: 'Rapport', date: '23/05/2026', status: 'nouveau' }
  ]);

  // Tâches du jour
  tachesDuJour = signal([
    { id: 1, description: 'Enregistrer 3 nouveaux dossiers', priorite: 'haute', fait: false },
    { id: 2, description: 'Scanner les documents reçus', priorite: 'normale', fait: true },
    { id: 3, description: 'Mettre à jour le registre', priorite: 'normale', fait: false },
    { id: 4, description: 'Préparer le compte-rendu de réunion', priorite: 'haute', fait: false }
  ]);

  constructor(private router: Router) {}

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  toggleTache(id: number) {
    const taches = this.tachesDuJour();
    const index = taches.findIndex(t => t.id === id);
    if (index !== -1) {
      taches[index].fait = !taches[index].fait;
      this.tachesDuJour.set([...taches]);
    }
  }
}
