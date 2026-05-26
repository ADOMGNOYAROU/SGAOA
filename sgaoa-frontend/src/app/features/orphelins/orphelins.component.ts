import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OrphelinService } from '../../core/services/orphelin.service';
import { Orphelin, StatutOrphelin } from '../../core/models/orphelin.model';

@Component({
  selector: 'app-orphelins',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './orphelins.component.html',
  styleUrl: './orphelins.component.scss'
})
export class OrphelinsComponent implements OnInit {
  orphelins: Orphelin[] = [];
  filteredOrphelins: Orphelin[] = [];
  searchTerm: string = '';
  statutFilter: string = 'TOUS';
  sexeFilter: string = 'TOUS';
  santeFilter: string = 'TOUS';
  selectedOrphelin: Orphelin | null = null;
  showDetailsModal: boolean = false;
  showAddModal: boolean = false;
  isLoading: boolean = true;
  error: string | null = null;

  nouveauOrphelin = {
    prenom: '',
    nom: '',
    dateNaissance: '',
    sexe: 'M',
    lieuNaissance: '',
    etatSante: 'BON',
    historiqueMedical: '',
    situationFamiliale: '',
    statut: StatutOrphelin.DISPONIBLE
  };

  constructor(private router: Router, private orphelinService: OrphelinService) {
    console.log('OrphelinsComponent initialisé avec service API');
  }

  ngOnInit() {
    this.loadOrphelins();
  }

  loadOrphelins() {
    this.isLoading = true;
    this.error = null;

    this.orphelinService.getAllOrphelins().subscribe({
      next: (data) => {
        this.orphelins = data;
        this.filteredOrphelins = [...this.orphelins];
        this.isLoading = false;
        console.log('Orphelins chargés depuis API:', data.length);
      },
      error: (err) => {
        console.error('Erreur lors du chargement des orphelins:', err);
        this.error = 'Impossible de se connecter au backend. Veuillez vérifier que le serveur backend est démarré sur http://localhost:8081';
        this.isLoading = false;
        this.orphelins = [];
        this.filteredOrphelins = [];
      }
    });
  }

  applyFilters() {
    this.filteredOrphelins = this.orphelins.filter(orphelin => {
      const matchesSearch = this.searchTerm === '' ||
        orphelin.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        orphelin.prenom.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesStatut = this.statutFilter === 'TOUS' || orphelin.statut === this.statutFilter;
      const matchesSexe = this.sexeFilter === 'TOUS' || orphelin.sexe === this.sexeFilter;
      const matchesSante = this.santeFilter === 'TOUS' || orphelin.etatSante === this.santeFilter;

      return matchesSearch && matchesStatut && matchesSexe && matchesSante;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  onStatutFilterChange() {
    this.applyFilters();
  }

  onSexeFilterChange() {
    this.applyFilters();
  }

  onSanteFilterChange() {
    this.applyFilters();
  }

  viewOrphelinDetails(orphelin: Orphelin) {
    this.selectedOrphelin = orphelin;
    this.showDetailsModal = true;
  }

  closeDetailsModal() {
    this.showDetailsModal = false;
    this.selectedOrphelin = null;
  }

  openAddModal() {
    this.showAddModal = true;
  }

  closeAddModal() {
    this.showAddModal = false;
    // Réinitialiser le formulaire
    this.nouveauOrphelin = {
      prenom: '',
      nom: '',
      dateNaissance: '',
      sexe: 'M',
      lieuNaissance: '',
      etatSante: 'BON',
      historiqueMedical: '',
      situationFamiliale: '',
      statut: StatutOrphelin.DISPONIBLE
    };
  }

  saveNewOrphelin() {
    // Créer un nouvel orphelin avec les données du formulaire
    const orphelinData = {
      nom: this.nouveauOrphelin.nom,
      prenom: this.nouveauOrphelin.prenom,
      dateNaissance: this.nouveauOrphelin.dateNaissance || new Date().toISOString().split('T')[0],
      sexe: this.nouveauOrphelin.sexe,
      lieuNaissance: this.nouveauOrphelin.lieuNaissance || 'Non spécifié',
      etatSante: this.nouveauOrphelin.etatSante,
      historiqueMedical: this.nouveauOrphelin.historiqueMedical || 'Aucun',
      situationFamiliale: this.nouveauOrphelin.situationFamiliale || 'Non spécifié',
      statut: this.nouveauOrphelin.statut
    };

    // Appeler le service pour créer l'orphelin
    this.orphelinService.createOrphelin(orphelinData).subscribe({
      next: (newOrphelin) => {
        // Ajouter le nouvel orphelin à la liste
        this.orphelins.unshift(newOrphelin);
        this.filteredOrphelins = [...this.orphelins];

        // Fermer le modal
        this.closeAddModal();

        console.log('Nouvel orphelin créé via API:', newOrphelin);
      },
      error: (err) => {
        console.error('Erreur lors de la création de l\'orphelin:', err);
        this.error = 'Impossible de créer l\'orphelin. Veuillez vérifier la connexion avec le backend.';
        // Ne pas ajouter localement - afficher seulement l'erreur
      }
    });
  }

  private calculateAge(dateNaissance: string): number {
    const birth = new Date(dateNaissance);
    const today = new Date();
    return Math.floor((today.getTime() - birth.getTime()) / (365.25 * 24 * 60 * 60 * 1000));
  }

  exportOrphelins() {
    console.log('Exporter les orphelins - fonctionnalité à implémenter');
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  // Méthodes utilitaires pour l'affichage
  getStatutColor(statut: StatutOrphelin): string {
    switch (statut) {
      case StatutOrphelin.DISPONIBLE: return 'bg-green-100 text-green-800';
      case StatutOrphelin.EN_ADOPTION: return 'bg-blue-100 text-blue-800';
      case StatutOrphelin.ADOPTÉ: return 'bg-purple-100 text-purple-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatutLabel(statut: StatutOrphelin): string {
    switch (statut) {
      case StatutOrphelin.DISPONIBLE: return 'Disponible';
      case StatutOrphelin.EN_ADOPTION: return 'En adoption';
      case StatutOrphelin.ADOPTÉ: return 'Adopté';
      default: return statut;
    }
  }

  getSanteColor(etatSante: string): string {
    switch (etatSante) {
      case 'BON': return 'bg-green-100 text-green-800';
      case 'MOYEN': return 'bg-yellow-100 text-yellow-800';
      case 'MAUVAIS': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }
}
