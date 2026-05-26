import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

interface Rapport {
  id: number;
  titre: string;
  type: 'ADOPTIONS' | 'ORPHELINS' | 'ADOPTANTS' | 'FINANCIER' | 'ACTIVITÉ';
  periode: string;
  dateGeneration: string;
  generateur: string;
  format: 'PDF' | 'EXCEL' | 'WORD';
  taille: number;
  statut: 'GÉNÉRÉ' | 'EN_COURS' | 'ERREUR';
  description: string;
}

interface Statistique {
  label: string;
  valeur: number;
  variation: number;
  couleur: string;
}

@Component({
  selector: 'app-rapports',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './rapports.component.html',
  styleUrl: './rapports.component.scss'
})
export class RapportsComponent implements OnInit {
  rapports: Rapport[] = [];
  filteredRapports: Rapport[] = [];
  searchTerm: string = '';
  typeFilter: string = 'TOUS';
  statutFilter: string = 'TOUS';
  selectedRapport: Rapport | null = null;
  showDetailsModal: boolean = false;
  showGenerateModal: boolean = false;
  isLoading: boolean = true;

  nouveauRapport = {
    titre: '',
    type: 'ADOPTIONS' as const,
    periode: '',
    format: 'PDF' as const
  };

  statistiques: Statistique[] = [];

  constructor(private router: Router) { }

  ngOnInit() {
    // Forcer le chargement immédiat pour éviter le problème de isLoading
    this.loadStatistiques();
    this.loadRapports();
  }

  loadRapports() {
    setTimeout(() => {
      this.rapports = [
        {
          id: 1,
          titre: 'Rapport mensuel des adoptions',
          type: 'ADOPTIONS',
          periode: 'Janvier 2024',
          dateGeneration: '2024-01-31T23:59:00',
          generateur: 'Yves Konan',
          format: 'PDF',
          taille: 2048,
          statut: 'GÉNÉRÉ',
          description: 'Rapport détaillé de toutes les adoptions du mois avec statistiques et graphiques.'
        },
        {
          id: 2,
          titre: 'Analyse des orphelins disponibles',
          type: 'ORPHELINS',
          periode: 'Trimestre 1 2024',
          dateGeneration: '2024-01-15T14:30:00',
          generateur: 'Aminata Touré',
          format: 'EXCEL',
          taille: 1536,
          statut: 'GÉNÉRÉ',
          description: 'Liste complète des orphelins disponibles avec leurs caractéristiques.'
        },
        {
          id: 3,
          titre: 'Rapport financier annuel',
          type: 'FINANCIER',
          periode: '2023',
          dateGeneration: '2024-01-10T09:15:00',
          generateur: 'Yves Konan',
          format: 'PDF',
          taille: 3072,
          statut: 'GÉNÉRÉ',
          description: 'Bilan financier complet de l\'année 2023 avec revenus et dépenses.'
        },
        {
          id: 4,
          titre: 'Statistiques des adoptants',
          type: 'ADOPTANTS',
          periode: 'Février 2024',
          dateGeneration: '2024-02-01T16:45:00',
          generateur: 'Kouadio Bamba',
          format: 'WORD',
          taille: 1024,
          statut: 'EN_COURS',
          description: 'Analyse démographique des adoptants et tendances du marché.'
        },
        {
          id: 5,
          titre: 'Rapport d\'activité trimestriel',
          type: 'ACTIVITÉ',
          periode: 'T4 2023',
          dateGeneration: '2024-01-05T11:20:00',
          generateur: 'Fatoumata Soro',
          format: 'PDF',
          taille: 2560,
          statut: 'ERREUR',
          description: 'Rapport d\'activité global du dernier trimestre 2023.'
        }
      ];
      this.filteredRapports = [...this.rapports];
      this.isLoading = false;
    }, 1000);
  }

  loadStatistiques() {
    setTimeout(() => {
      this.statistiques = [
        { label: 'Total des rapports', valeur: 156, variation: 12.5, couleur: 'text-blue-600' },
        { label: 'Rapports ce mois-ci', valeur: 23, variation: 8.3, couleur: 'text-green-600' },
        { label: 'En cours de génération', valeur: 3, variation: -25.0, couleur: 'text-yellow-600' },
        { label: 'Erreurs', valeur: 2, variation: -50.0, couleur: 'text-red-600' }
      ];
    }, 500); // Charger plus rapidement
  }

  applyFilters() {
    this.filteredRapports = this.rapports.filter(rapport => {
      const matchesSearch = this.searchTerm === '' ||
        rapport.titre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        rapport.description.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        rapport.generateur.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesType = this.typeFilter === 'TOUS' || rapport.type === this.typeFilter;
      const matchesStatut = this.statutFilter === 'TOUS' || rapport.statut === this.statutFilter;

      return matchesSearch && matchesType && matchesStatut;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  onTypeFilterChange() {
    this.applyFilters();
  }

  onStatutFilterChange() {
    this.applyFilters();
  }

  viewRapportDetails(rapport: Rapport) {
    this.selectedRapport = rapport;
    this.showDetailsModal = true;
  }

  closeDetailsModal() {
    this.showDetailsModal = false;
    this.selectedRapport = null;
  }

  openGenerateModal() {
    this.showGenerateModal = true;
  }

  closeGenerateModal() {
    this.showGenerateModal = false;
    this.resetNouveauRapport();
  }

  resetNouveauRapport() {
    this.nouveauRapport = {
      titre: '',
      type: 'ADOPTIONS',
      periode: '',
      format: 'PDF'
    };
  }

  generateRapport() {
    console.log('Génération du rapport:', this.nouveauRapport);
    // Logique de génération du rapport
    this.closeGenerateModal();
  }

  downloadRapport(rapport: Rapport) {
    console.log('Téléchargement du rapport:', rapport.titre);
  }

  deleteRapport(rapport: Rapport) {
    console.log('Suppression du rapport:', rapport.titre);
  }

  getTypeColor(type: string): string {
    switch (type) {
      case 'ADOPTIONS': return 'bg-blue-100 text-blue-800';
      case 'ORPHELINS': return 'bg-green-100 text-green-800';
      case 'ADOPTANTS': return 'bg-purple-100 text-purple-800';
      case 'FINANCIER': return 'bg-yellow-100 text-yellow-800';
      case 'ACTIVITÉ': return 'bg-orange-100 text-orange-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatutColor(statut: string): string {
    switch (statut) {
      case 'GÉNÉRÉ': return 'bg-green-100 text-green-800';
      case 'EN_COURS': return 'bg-yellow-100 text-yellow-800';
      case 'ERREUR': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getFormatIcon(format: string): string {
    switch (format) {
      case 'PDF': return 'M9 14l6-6m0 0l-6 6m6-6H3m18 0a9 9 0 11-18 0 9 9 0 0118 0z';
      case 'EXCEL': return 'M9 17v1a2 2 0 002 2h6a2 2 0 002-2v-1m-6 0h6m-6 0V7a2 2 0 012-2h6a2 2 0 012 2v10m-6 0H9';
      case 'WORD': return 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z';
      default: return 'M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z';
    }
  }

  getTypeLabel(type: string): string {
    switch (type) {
      case 'ADOPTIONS': return 'Adoptions';
      case 'ORPHELINS': return 'Orphelins';
      case 'ADOPTANTS': return 'Adoptants';
      case 'FINANCIER': return 'Financier';
      case 'ACTIVITÉ': return 'Activité';
      default: return type;
    }
  }

  getStatutLabel(statut: string): string {
    switch (statut) {
      case 'GÉNÉRÉ': return 'Généré';
      case 'EN_COURS': return 'En cours';
      case 'ERREUR': return 'Erreur';
      default: return statut;
    }
  }

  getFormatLabel(format: string): string {
    switch (format) {
      case 'PDF': return 'PDF';
      case 'EXCEL': return 'Excel';
      case 'WORD': return 'Word';
      default: return format;
    }
  }

  formatTaille(taille: number): string {
    if (taille < 1024) {
      return `${taille} octets`;
    } else if (taille < 1024 * 1024) {
      return `${(taille / 1024).toFixed(1)} Ko`;
    } else {
      return `${(taille / (1024 * 1024)).toFixed(1)} Mo`;
    }
  }

  exportRapports() {
    console.log('Exporter les rapports');
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}
