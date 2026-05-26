import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { Utilisateur } from '../../core/models/utilisateur.model';

@Component({
  selector: 'app-profil-adoptant',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profil-adoptant.component.html',
  styleUrl: './profil-adoptant.component.scss'
})
export class ProfilAdoptantComponent {
  private authService = inject(AuthService);

  currentUser = signal<Utilisateur | null>(null);
  isEditing = signal(false);
  isLoading = signal(true);

  constructor() {
    this.loadUserData();
  }

  private loadUserData(): void {
    const user: Utilisateur | null = this.authService.getCurrentUser();
    this.currentUser.set(user);
    this.isLoading.set(false);
  }

  toggleEdit(): void {
    this.isEditing.update(editing => !editing);
  }

  saveProfile(): void {
    // TODO: Implémenter la sauvegarde du profil
    alert('Profil sauvegardé avec succès !');
    this.isEditing.set(false);
  }

  cancelEdit(): void {
    this.isEditing.set(false);
  }
}
