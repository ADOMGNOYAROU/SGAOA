import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UtilisateurService } from '../../core/services/utilisateur.service';
import { Utilisateur, Role, StatutCompte } from '../../core/models/utilisateur.model';

@Component({
  selector: 'app-utilisateurs',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './utilisateurs.component.html',
  styleUrl: './utilisateurs.component.scss'
})
export class UtilisateursComponent implements OnInit {
  utilisateurs: Utilisateur[] = [];
  filteredUtilisateurs: Utilisateur[] = [];
  searchTerm: string = '';
  roleFilter: string = 'TOUS';
  statutFilter: string = 'TOUS';
  selectedUtilisateur: Utilisateur | null = null;
  showDetailsModal: boolean = false;
  showAddModal: boolean = false;
  isLoading: boolean = true;
  error: string | null = null;

  nouvelUtilisateur = {
    nom: '',
    prenom: '',
    email: '',
    motDePasse: '',
    telephone: '',
    adresse: '',
    role: Role.AGENT_SOCIAL,
    statut: StatutCompte.ACTIF
  };

  constructor(private router: Router, private utilisateurService: UtilisateurService) {
    console.log('UtilisateursComponent initialisé avec service API');
  }

  ngOnInit() {
    this.loadUtilisateurs();
  }

  loadUtilisateurs() {
    this.isLoading = true;
    this.error = null;

    this.utilisateurService.getAllUtilisateurs().subscribe({
      next: (data) => {
        this.utilisateurs = data;
        this.filteredUtilisateurs = [...this.utilisateurs];
        this.isLoading = false;
        console.log('Utilisateurs chargés depuis API:', data.length);
      },
      error: (err) => {
        console.error('Erreur lors du chargement des utilisateurs:', err);
        this.error = 'Impossible de se connecter au backend. Veuillez vérifier que le serveur backend est démarré sur http://localhost:8081';
        this.isLoading = false;
        this.utilisateurs = [];
        this.filteredUtilisateurs = [];
      }
    });
  }

  applyFilters() {
    this.filteredUtilisateurs = this.utilisateurs.filter(utilisateur => {
      const matchesSearch = this.searchTerm === '' ||
        utilisateur.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        utilisateur.prenom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        utilisateur.email.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesRole = this.roleFilter === 'TOUS' || utilisateur.role === this.roleFilter;
      const matchesStatut = this.statutFilter === 'TOUS' || utilisateur.statut === this.statutFilter;

      return matchesSearch && matchesRole && matchesStatut;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  onRoleFilterChange() {
    this.applyFilters();
  }

  onStatutFilterChange() {
    this.applyFilters();
  }

  viewUtilisateurDetails(utilisateur: Utilisateur) {
    this.selectedUtilisateur = utilisateur;
    this.showDetailsModal = true;
  }

  closeDetailsModal() {
    this.showDetailsModal = false;
    this.selectedUtilisateur = null;
  }

  openAddModal() {
    console.log('=== openAddModal appelé ===');
    this.showAddModal = true;
    console.log('showAddModal est maintenant:', this.showAddModal);
  }

  closeAddModal() {
    this.showAddModal = false;
    // Réinitialiser le formulaire
    this.nouvelUtilisateur = {
      nom: '',
      prenom: '',
      email: '',
      motDePasse: '',
      telephone: '',
      adresse: '',
      role: Role.AGENT_SOCIAL,
      statut: StatutCompte.ACTIF
    };
  }

  saveNewUtilisateur() {
    // Créer un nouvel utilisateur avec les données du formulaire
    const utilisateurData = {
      prenom: this.nouvelUtilisateur.prenom,
      nom: this.nouvelUtilisateur.nom,
      email: this.nouvelUtilisateur.email,
      motDePasse: this.nouvelUtilisateur.motDePasse,
      telephone: this.nouvelUtilisateur.telephone,
      adresse: this.nouvelUtilisateur.adresse || '',
      role: this.nouvelUtilisateur.role,
      statut: this.nouvelUtilisateur.statut
    };

    console.log('Données envoyées au backend:', utilisateurData);

    // Appeler le service pour créer l'utilisateur
    this.utilisateurService.createUtilisateur(utilisateurData).subscribe({
      next: (newUtilisateur) => {
        console.log('Nouvel utilisateur créé via API:', newUtilisateur);

        // Ajouter directement le nouvel utilisateur en haut de la liste (plus rapide)
        this.utilisateurs.unshift(newUtilisateur);
        this.applyFilters();

        // Fermer le modal
        this.closeAddModal();
      },
      error: (err) => {
        console.error('Erreur lors de la création de l\'utilisateur:', err);
        // Afficher le message d'erreur du backend
        this.error = err || 'Impossible de créer l\'utilisateur.';
        alert('Erreur: ' + this.error);
      }
    });
  }

  exportUtilisateurs() {
    console.log('Exporter les utilisateurs - fonctionnalité à implémenter');
  }

  editUtilisateur(utilisateur: Utilisateur) {
    console.log('Modifier utilisateur:', utilisateur);
    // TODO: Implémenter le modal de modification
    alert('Fonctionnalité de modification à implémenter');
  }

  deleteUtilisateur(utilisateur: Utilisateur) {
    if (confirm(`Êtes-vous sûr de vouloir supprimer l'utilisateur ${utilisateur.prenom} ${utilisateur.nom} ?`)) {
      this.utilisateurService.deleteUtilisateur(utilisateur.id).subscribe({
        next: () => {
          // Retirer l'utilisateur de la liste
          this.utilisateurs = this.utilisateurs.filter(u => u.id !== utilisateur.id);
          this.filteredUtilisateurs = this.filteredUtilisateurs.filter(u => u.id !== utilisateur.id);
          console.log('Utilisateur supprimé avec succès');
          alert('Utilisateur supprimé avec succès');
        },
        error: (err) => {
          console.error('Erreur lors de la suppression:', err);
          alert('Erreur: ' + err);
          // Recharger la liste pour synchroniser
          this.loadUtilisateurs();
        }
      });
    }
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  // Méthodes utilitaires pour l'affichage
  getRoleColor(role: Role): string {
    switch (role) {
      case Role.ADMINISTRATEUR: return 'bg-purple-100 text-purple-800';
      case Role.PRESIDENT_COMITE: return 'bg-blue-100 text-blue-800';
      case Role.SECRETAIRE: return 'bg-green-100 text-green-800';
      case Role.AGENT_SOCIAL: return 'bg-yellow-100 text-yellow-800';
      case Role.ADOPTANT: return 'bg-gray-100 text-gray-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getRoleLabel(role: Role): string {
    switch (role) {
      case Role.ADMINISTRATEUR: return 'Administrateur';
      case Role.PRESIDENT_COMITE: return 'Président du comité';
      case Role.SECRETAIRE: return 'Secrétaire';
      case Role.AGENT_SOCIAL: return 'Agent social';
      case Role.ADOPTANT: return 'Adoptant';
      default: return role;
    }
  }

  getStatutColor(statut: string): string {
    switch (statut) {
      case 'ACTIF': return 'bg-green-100 text-green-800';
      case 'INACTIF': return 'bg-gray-100 text-gray-800';
      case 'SUSPENDU': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatutLabel(statut: string): string {
    switch (statut) {
      case 'ACTIF': return 'Actif';
      case 'INACTIF': return 'Inactif';
      case 'SUSPENDU': return 'Suspendu';
      default: return statut;
    }
  }

  formatDate(dateString: string): string {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getPermissionsLabels(permissions: string[]): string {
    return permissions.map(p => {
      switch (p) {
        case 'LECTURE': return 'Lecture';
        case 'ÉCRITURE': return 'Écriture';
        case 'SUPPRESSION': return 'Suppression';
        default: return p;
      }
    }).join(', ');
  }
}
