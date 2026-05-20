import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AsyncPipe } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { Role } from '../../core/models/utilisateur.model';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {
  showDropdown = false;
  private authService = inject(AuthService);
  private router = inject(Router);

  get currentUser$() {
    return this.authService.currentUser$;
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
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
}
