import { Component, inject, signal } from '@angular/core';
import { Router, RouterOutlet, RouterModule } from '@angular/router';
import { AsyncPipe, CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { Role } from '../../core/models/utilisateur.model';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, RouterModule, CommonModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
  showDropdown = false;
  sidebarCollapsed = signal(false);
  private authService = inject(AuthService);
  private router = inject(Router);

  // Exposer Role pour le template
  Role = Role;

  get currentUser$() {
    return this.authService.currentUser$;
  }

  get currentUserRole(): Role | null {
    return this.authService.getCurrentUser()?.role || null;
  }

  // Vérifier si l'utilisateur peut voir Utilisateurs (Admin seulement)
  canSeeUtilisateurs(): boolean {
    return this.currentUserRole === Role.ADMINISTRATEUR;
  }

  // Vérifier si l'utilisateur peut voir Rapports (Admin, Président, Agent)
  canSeeRapports(): boolean {
    const allowedRoles = [Role.ADMINISTRATEUR, Role.PRESIDENT_COMITE, Role.AGENT_SOCIAL];
    return this.currentUserRole !== null && allowedRoles.includes(this.currentUserRole);
  }

  // Vérifier si l'utilisateur peut voir Paramètres (Admin seulement)
  canSeeParametres(): boolean {
    return this.currentUserRole === Role.ADMINISTRATEUR;
  }

  // Obtenir le lien du dashboard selon le rôle
  getDashboardLink(): string {
    switch (this.currentUserRole) {
      case Role.ADMINISTRATEUR:
        return '/dashboard';
      case Role.PRESIDENT_COMITE:
        return '/dashboard/president';
      case Role.SECRETAIRE:
        return '/dashboard/secretaire';
      case Role.AGENT_SOCIAL:
        return '/dashboard/agent';
      default:
        return '/dashboard';
    }
  }

  // Obtenir le titre du dashboard selon le rôle
  getDashboardTitle(): string {
    switch (this.currentUserRole) {
      case Role.ADMINISTRATEUR:
        return 'Dashboard Administration';
      case Role.PRESIDENT_COMITE:
        return 'Dashboard Président';
      case Role.SECRETAIRE:
        return 'Dashboard Secrétaire';
      case Role.AGENT_SOCIAL:
        return 'Dashboard Agent Social';
      default:
        return 'Dashboard';
    }
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  toggleSidebar(): void {
    this.sidebarCollapsed.update(value => !value);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  getRoleLabel(role: Role): string {
    const labels: Record<Role, string> = {
      [Role.ADMINISTRATEUR]: 'Administrateur',
      [Role.PRESIDENT_COMITE]: 'Président',
      [Role.SECRETAIRE]: 'Secrétaire',
      [Role.AGENT_SOCIAL]: 'Agent Social',
      [Role.ADOPTANT]: 'Adoptant'
    };
    return labels[role] || role;
  }

  getInitials(): string {
    const user = this.authService.getCurrentUser();
    if (!user) return 'U';
    const firstInitial = user.prenom?.charAt(0)?.toUpperCase() || '';
    const lastInitial = user.nom?.charAt(0)?.toUpperCase() || '';
    return firstInitial + lastInitial || 'U';
  }

  currentDate(): string {
    const now = new Date();
    const options: Intl.DateTimeFormatOptions = {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    };
    return now.toLocaleDateString('fr-FR', options);
  }
}
