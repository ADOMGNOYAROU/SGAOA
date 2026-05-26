import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DemandeAdoptionService, DemandeAdoptionResponse } from '../../core/services/demande-adoption.service';

@Component({
  selector: 'app-dossiers',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dossiers.component.html',
  styleUrl: './dossiers.component.scss'
})
export class DossiersComponent implements OnInit {
  dossiers: DemandeAdoptionResponse[] = [];
  filteredDossiers: DemandeAdoptionResponse[] = [];
  searchTerm: string = '';
  statutFilter: string = 'TOUS';
  selectedDossier: DemandeAdoptionResponse | null = null;
  showDetailsModal: boolean = false;
  showCreateModal: boolean = false;
  isLoading: boolean = true;
  error: string | null = null;

  nouveauDossier = {
    adoptantId: 0,
    orphelinId: 0
  };

  constructor(private router: Router, private demandeService: DemandeAdoptionService) {
    console.log('DossiersComponent initialisé avec service API');
  }

  ngOnInit() {
    this.loadDossiers();
  }

  loadDossiers() {
    this.isLoading = true;
    this.error = null;

    this.demandeService.getAllDemandes().subscribe({
      next: (data) => {
        this.dossiers = data;
        this.filteredDossiers = [...this.dossiers];
        this.isLoading = false;
        console.log('Dossiers chargés depuis API:', data.length);
      },
      error: (err) => {
        console.error('Erreur lors du chargement des dossiers:', err);
        this.error = 'Impossible de se connecter au backend. Veuillez vérifier que le serveur backend est démarré sur http://localhost:8081';
        this.isLoading = false;
        this.dossiers = [];
        this.filteredDossiers = [];
      }
    });
  }

  applyFilters() {
    this.filteredDossiers = this.dossiers.filter(dossier => {
      const matchesSearch = this.searchTerm === '' ||
        dossier.reference.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatut = this.statutFilter === 'TOUS' || dossier.statut === this.statutFilter;

      return matchesSearch && matchesStatut;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  onStatutFilterChange() {
    this.applyFilters();
  }

  viewDossierDetails(dossier: DemandeAdoptionResponse) {
    this.selectedDossier = dossier;
    this.showDetailsModal = true;
  }

  closeModal() {
    this.showDetailsModal = false;
    this.selectedDossier = null;
  }

  createNewDossier() {
    this.showCreateModal = true;
  }

  closeCreateModal() {
    this.showCreateModal = false;
    // Réinitialiser le formulaire
    this.nouveauDossier = {
      adoptantId: 0,
      orphelinId: 0
    };
  }

  saveNewDossier() {
    // Créer une nouvelle demande avec les données du formulaire
    const demandeData = {
      adoptantId: this.nouveauDossier.adoptantId || 1,
      orphelinId: this.nouveauOrphelinId || 1
    };

    // Appeler le service pour créer la demande
    this.demandeService.createDemande(demandeData).subscribe({
      next: (newDossier) => {
        // Ajouter le nouveau dossier à la liste
        this.dossiers.unshift(newDossier);
        this.filteredDossiers = [...this.dossiers];

        // Fermer le modal
        this.closeCreateModal();

        console.log('Nouveau dossier créé via API:', newDossier);
      },
      error: (err) => {
        console.error('Erreur lors de la création du dossier:', err);
        this.error = 'Impossible de créer le dossier. Veuillez vérifier la connexion avec le backend.';
        // Ne pas ajouter localement - afficher seulement l'erreur
      }
    });
  }

  private get nouveauOrphelinId(): number {
    return this.nouveauDossier.orphelinId;
  }

  exportDossiers() {
    console.log('Exporter les dossiers - fonctionnalité à implémenter');
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  // Méthodes utilitaires pour l'affichage
  getStatutColor(statut: string): string {
    switch (statut) {
      case 'EN_ATTENTE': return 'bg-yellow-100 text-yellow-800';
      case 'EN_COURS': return 'bg-blue-100 text-blue-800';
      case 'APPROUVE': return 'bg-green-100 text-green-800';
      case 'REJETE': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatutLabel(statut: string): string {
    switch (statut) {
      case 'EN_ATTENTE': return 'En attente';
      case 'EN_COURS': return 'En cours';
      case 'APPROUVE': return 'Approuvé';
      case 'REJETE': return 'Rejeté';
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
