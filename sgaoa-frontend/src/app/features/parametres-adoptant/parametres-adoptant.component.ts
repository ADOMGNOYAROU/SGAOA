import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-parametres-adoptant',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parametres-adoptant.component.html',
  styleUrl: './parametres-adoptant.component.scss'
})
export class ParametresAdoptantComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  showPasswordForm = signal(false);
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  changePassword(): void {
    if (this.newPassword !== this.confirmPassword) {
      alert('Les mots de passe ne correspondent pas');
      return;
    }
    // TODO: Implémenter le changement de mot de passe
    alert('Mot de passe changé avec succès !');
    this.showPasswordForm.set(false);
    this.currentPassword = '';
    this.newPassword = '';
    this.confirmPassword = '';
  }

  togglePasswordForm(): void {
    this.showPasswordForm.update(show => !show);
  }
}
