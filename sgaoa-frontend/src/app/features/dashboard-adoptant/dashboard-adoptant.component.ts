import { Component, inject, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { DemandeAdoptionService } from '../../core/services/demande-adoption.service';
import { OrphelinService } from '../../core/services/orphelin.service';
import { Utilisateur, Role } from '../../core/models/utilisateur.model';
import { Orphelin } from '../../core/models/orphelin.model';

@Component({
  selector: 'app-dashboard-adoptant',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './dashboard-adoptant.component.html',
  styleUrl: './dashboard-adoptant.component.scss'
})
export class DashboardAdoptantComponent {
  private router = inject(Router);
  private authService = inject(AuthService);
  private demandeAdoptionService = inject(DemandeAdoptionService);
  private orphelinService = inject(OrphelinService);

  currentUser = signal<Utilisateur | null>(null);
  isLoading = signal(true);
  isSidebarCollapsed = signal(false);
  orphelinsDisponibles = signal<Orphelin[]>([]);
  orphelinSelectionne = signal<number | null>(null);

  constructor() {
    this.loadUserData();
    this.loadOrphelinsDisponibles();
  }

  private loadUserData(): void {
    const user: Utilisateur | null = this.authService.getCurrentUser();
    this.currentUser.set(user);
    this.isLoading.set(false);

    // Vérifier que l'utilisateur est bien un adoptant
    if (user?.role !== Role.ADOPTANT) {
      this.router.navigate(['/auth/login']);
    }
  }

  private loadOrphelinsDisponibles(): void {
    this.orphelinService.getOrphelinsDisponibles().subscribe({
      next: (orphelins) => {
        this.orphelinsDisponibles.set(orphelins);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des orphelins:', error);
      }
    });
  }

  getCurrentDate(): string {
    return new Date().toLocaleDateString('fr-FR', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  getInitials(): string {
    const user = this.currentUser();
    if (!user) return '??';
    const firstInitial = user.prenom?.charAt(0).toUpperCase() || '';
    const lastInitial = user.nom?.charAt(0).toUpperCase() || '';
    return `${firstInitial}${lastInitial}`;
  }

  toggleSidebar(): void {
    this.isSidebarCollapsed.update(collapsed => !collapsed);
  }

  creerDemande(): void {
    const user = this.currentUser();
    if (!user) {
      alert('Vous devez être connecté pour créer une demande');
      return;
    }

    const orphelinId = this.orphelinSelectionne();
    if (!orphelinId) {
      alert('Veuillez sélectionner un orphelin pour créer une demande');
      return;
    }

    const token = this.authService.getAccessToken();
    console.log('Token JWT:', token ? 'Présent' : 'Absent');
    console.log('User ID:', user.id);
    console.log('User Role:', user.role);
    console.log('Orphelin ID sélectionné:', orphelinId);

    this.demandeAdoptionService.createDemande({
      adoptantId: user.id,
      orphelinId: orphelinId
    }).subscribe({
      next: (response) => {
        alert('Demande créée avec succès ! Référence : ' + response.reference);
        // Recharger les données
        this.loadUserData();
        this.orphelinSelectionne.set(null);
      },
      error: (error) => {
        console.error('Erreur lors de la création de la demande:', error);
        console.error('Status:', error.status);
        console.error('Message:', error.message);
        console.error('Error details:', error.error);
        alert('Erreur lors de la création de la demande: ' + (error.message || 'Erreur inconnue'));
      }
    });
  }
}
