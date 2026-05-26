import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DemandeAdoptionService } from '../../core/services/demande-adoption.service';
import { AuthService } from '../../core/services/auth.service';
import { Utilisateur } from '../../core/models/utilisateur.model';

@Component({
  selector: 'app-mes-demandes',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mes-demandes.component.html',
  styleUrl: './mes-demandes.component.scss'
})
export class MesDemandesComponent {
  private demandeAdoptionService = inject(DemandeAdoptionService);
  private authService = inject(AuthService);

  currentUser = signal<Utilisateur | null>(null);
  demandes = signal<any[]>([]);
  isLoading = signal(true);

  constructor() {
    this.loadUserData();
    this.loadDemandes();
  }

  private loadUserData(): void {
    const user: Utilisateur | null = this.authService.getCurrentUser();
    this.currentUser.set(user);
  }

  private loadDemandes(): void {
    const user = this.currentUser();
    if (user?.id) {
      this.demandeAdoptionService.getDemandesAdoptant(user.id).subscribe({
        next: (demandes) => {
          this.demandes.set(demandes);
          this.isLoading.set(false);
        },
        error: (error) => {
          console.error('Erreur lors du chargement des demandes:', error);
          this.isLoading.set(false);
        }
      });
    }
  }

  getStatutClass(statut: string): string {
    switch (statut) {
      case 'EN_ATTENTE':
        return 'bg-yellow-100 text-yellow-800';
      case 'EN_INSTRUCTION':
        return 'bg-blue-100 text-blue-800';
      case 'APPROUVÉE':
        return 'bg-green-100 text-green-800';
      case 'REJETÉE':
        return 'bg-red-100 text-red-800';
      case 'COMPLÉTÉE':
        return 'bg-purple-100 text-purple-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }
}
