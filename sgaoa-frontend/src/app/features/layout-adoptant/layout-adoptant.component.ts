import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet, RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { Utilisateur } from '../../core/models/utilisateur.model';

@Component({
  selector: 'app-layout-adoptant',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule],
  templateUrl: './layout-adoptant.component.html',
  styleUrl: './layout-adoptant.component.scss'
})
export class LayoutAdoptantComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  isSidebarCollapsed = signal(false);
  currentUser = signal<Utilisateur | null>(null);

  constructor() {
    this.loadUserData();
  }

  private loadUserData(): void {
    const user: Utilisateur | null = this.authService.getCurrentUser();
    this.currentUser.set(user);
  }

  toggleSidebar(): void {
    this.isSidebarCollapsed.update(collapsed => !collapsed);
  }

  getInitials(): string {
    const user = this.currentUser();
    if (!user) return 'U';
    return `${user.prenom?.charAt(0) || ''}${user.nom?.charAt(0) || ''}`.toUpperCase();
  }

  getCurrentDate(): string {
    const options: Intl.DateTimeFormatOptions = {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    };
    return new Date().toLocaleDateString('fr-FR', options);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
