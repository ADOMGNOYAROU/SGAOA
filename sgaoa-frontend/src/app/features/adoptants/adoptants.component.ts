import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdoptantService } from '../../core/services/adoptant.service';
import { Adoptant, StatutAdoptant } from '../../core/models/adoptant.model';

@Component({
  selector: 'app-adoptants',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './adoptants.component.html',
  styleUrl: './adoptants.component.scss'
})
export class AdoptantsComponent implements OnInit {
  adoptants: Adoptant[] = [];
  filteredAdoptants: Adoptant[] = [];
  searchTerm: string = '';
  statutFilter: string = 'TOUS';
  verificationFilter: string = 'TOUS';
  selectedAdoptant: Adoptant | null = null;
  showDetailsModal: boolean = false;
  showAddModal: boolean = false;
  isLoading: boolean = true;
  error: string | null = null;

  nouvelAdoptant = {
    nom: '',
    prenom: '',
    email: '',
    telephone: '',
    dateNaissance: '',
    adresse: '',
    ville: '',
    pays: '',
    profession: '',
    revenuMensuel: 0,
    statut: StatutAdoptant.ACTIF
  };

  constructor(private router: Router, private adoptantService: AdoptantService) {
    console.log('AdoptantsComponent initialisé avec service API');
  }

  ngOnInit() {
    this.loadAdoptants();
  }

  loadAdoptants() {
    this.isLoading = true;
    this.error = null;

    this.adoptantService.getAllAdoptants().subscribe({
      next: (data) => {
        this.adoptants = data;
        this.filteredAdoptants = [...this.adoptants];
        this.isLoading = false;
        console.log('Adoptants chargés depuis API:', data.length);
      },
      error: (err) => {
        console.error('Erreur lors du chargement des adoptants:', err);
        this.error = 'Impossible de se connecter au backend. Veuillez vérifier que le serveur backend est démarré sur http://localhost:8081';
        this.isLoading = false;
        this.adoptants = [];
        this.filteredAdoptants = [];
      }
    });
  }

  applyFilters() {
    this.filteredAdoptants = this.adoptants.filter(adoptant => {
      const matchesSearch = this.searchTerm === '' ||
        adoptant.reference.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        adoptant.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        adoptant.prenom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        adoptant.email.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        adoptant.ville.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatut = this.statutFilter === 'TOUS' || adoptant.statut === this.statutFilter;
      const matchesVerification = this.verificationFilter === 'TOUS' ||
        (this.verificationFilter === 'VERIFIED' && adoptant.verified) ||
        (this.verificationFilter === 'UNVERIFIED' && !adoptant.verified);

      return matchesSearch && matchesStatut && matchesVerification;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  onStatutFilterChange() {
    this.applyFilters();
  }

  onVerificationFilterChange() {
    this.applyFilters();
  }

  viewAdoptantDetails(adoptant: Adoptant) {
    this.selectedAdoptant = adoptant;
    this.showDetailsModal = true;
  }

  closeModal() {
    this.showDetailsModal = false;
    this.selectedAdoptant = null;
  }

  openAddModal() {
    this.showAddModal = true;
  }

  closeAddModal() {
    this.showAddModal = false;
    // Réinitialiser le formulaire
    this.nouvelAdoptant = {
      nom: '',
      prenom: '',
      email: '',
      telephone: '',
      dateNaissance: '',
      adresse: '',
      ville: '',
      pays: '',
      profession: '',
      revenuMensuel: 0,
      statut: StatutAdoptant.ACTIF
    };
  }

  saveNewAdoptant() {
    // Créer un nouvel adoptant avec les données du formulaire
    const adoptantData = {
      ...this.nouvelAdoptant,
      dateInscription: new Date().toISOString(),
      dernierDossier: new Date().toISOString(),
      dossierCount: 0,
      documents: 0,
      verified: false,
      age: this.calculateAge(this.nouvelAdoptant.dateNaissance),
      reference: `ADO-${String(this.adoptants.length + 1).padStart(4, '0')}`
    };

    // Appeler le service pour créer l'adoptant
    this.adoptantService.createAdoptant(adoptantData).subscribe({
      next: (newAdoptant) => {
        // Ajouter le nouvel adoptant à la liste
        this.adoptants.unshift(newAdoptant);
        this.filteredAdoptants = [...this.adoptants];

        // Fermer le modal
        this.closeAddModal();

        console.log('Nouvel adoptant créé via API:', newAdoptant);
      },
      error: (err) => {
        console.error('Erreur lors de la création de l\'adoptant:', err);
        this.error = 'Impossible de créer l\'adoptant. Veuillez vérifier la connexion avec le backend.';
        // Ne pas ajouter localement - afficher seulement l'erreur
      }
    });
  }

  private calculateAge(dateNaissance: string): number {
    const birth = new Date(dateNaissance);
    const today = new Date();
    return Math.floor((today.getTime() - birth.getTime()) / (365.25 * 24 * 60 * 60 * 1000));
  }

  exportAdoptants() {
    console.log('Exporter les adoptants - fonctionnalité à implémenter');
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  // Méthodes utilitaires pour l'affichage
  getStatutColor(statut: StatutAdoptant): string {
    switch (statut) {
      case StatutAdoptant.ACTIF: return 'bg-green-100 text-green-800';
      case StatutAdoptant.INACTIF: return 'bg-gray-100 text-gray-800';
      case StatutAdoptant.SUSPENDU: return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatutLabel(statut: StatutAdoptant): string {
    switch (statut) {
      case StatutAdoptant.ACTIF: return 'Actif';
      case StatutAdoptant.INACTIF: return 'Inactif';
      case StatutAdoptant.SUSPENDU: return 'Suspendu';
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
}
