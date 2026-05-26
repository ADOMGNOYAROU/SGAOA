import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

interface Parametre {
  id: number;
  categorie: string;
  nom: string;
  description: string;
  valeur: string | number | boolean;
  type: 'text' | 'number' | 'boolean' | 'select' | 'email';
  options?: string[];
}

interface BackupInfo {
  id: number;
  nom: string;
  date: string;
  taille: number;
  type: 'AUTOMATIQUE' | 'MANUEL';
  statut: 'COMPLET' | 'PARTIEL' | 'ERREUR';
}

@Component({
  selector: 'app-parametres',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parametres.component.html',
  styleUrl: './parametres.component.scss'
})
export class ParametresComponent implements OnInit {
  parametresGeneraux: Parametre[] = [];
  parametresSysteme: Parametre[] = [];
  parametresSecurite: Parametre[] = [];
  backups: BackupInfo[] = [];

  activeTab: string = 'general';
  isLoading: boolean = true;
  showNotificationFlag: boolean = false;
  notificationMessage: string = '';
  notificationType: 'success' | 'error' | 'warning' = 'success';

  constructor(private router: Router) { }

  ngOnInit() {
    this.loadParametres();
    this.loadBackups();
  }

  loadParametres() {
    setTimeout(() => {
      this.parametresGeneraux = [
        {
          id: 1,
          categorie: 'Général',
          nom: 'nomOrganisation',
          description: 'Nom de l\'organisation',
          valeur: 'SGAOA',
          type: 'text'
        },
        {
          id: 2,
          categorie: 'Général',
          nom: 'emailContact',
          description: 'Email de contact principal',
          valeur: 'contact@sgaoa.ci',
          type: 'email'
        },
        {
          id: 3,
          categorie: 'Général',
          nom: 'telephoneContact',
          description: 'Téléphone de contact',
          valeur: '+225 27 20 30 40 50',
          type: 'text'
        },
        {
          id: 4,
          categorie: 'Général',
          nom: 'adresse',
          description: 'Adresse physique',
          valeur: 'Abidjan, Cocody, Rue des Jardins',
          type: 'text'
        },
        {
          id: 5,
          categorie: 'Général',
          nom: 'devise',
          description: 'Devise par défaut',
          valeur: 'XOF',
          type: 'select',
          options: ['XOF', 'EUR', 'USD']
        }
      ];

      this.parametresSysteme = [
        {
          id: 6,
          categorie: 'Système',
          nom: 'nbItemsParPage',
          description: 'Nombre d\'éléments par page',
          valeur: 20,
          type: 'number'
        },
        {
          id: 7,
          categorie: 'Système',
          nom: 'delaiSession',
          description: 'Délai d\'expiration de session (minutes)',
          valeur: 30,
          type: 'number'
        },
        {
          id: 8,
          categorie: 'Système',
          nom: 'modeMaintenance',
          description: 'Activer le mode maintenance',
          valeur: false,
          type: 'boolean'
        },
        {
          id: 9,
          categorie: 'Système',
          nom: 'notificationsEmail',
          description: 'Activer les notifications par email',
          valeur: true,
          type: 'boolean'
        },
        {
          id: 10,
          categorie: 'Système',
          nom: 'fuseauHoraire',
          description: 'Fuseau horaire par défaut',
          valeur: 'Africa/Abidjan',
          type: 'select',
          options: ['Africa/Abidjan', 'Africa/Paris', 'UTC', 'GMT']
        }
      ];

      this.parametresSecurite = [
        {
          id: 11,
          categorie: 'Sécurité',
          nom: 'longueurMinMotDePasse',
          description: 'Longueur minimale du mot de passe',
          valeur: 8,
          type: 'number'
        },
        {
          id: 12,
          categorie: 'Sécurité',
          nom: 'delaiVerrouillage',
          description: 'Délai avant verrouillage (minutes)',
          valeur: 15,
          type: 'number'
        },
        {
          id: 13,
          categorie: 'Sécurité',
          nom: 'doubleAuthentification',
          description: 'Exiger l\'authentification à deux facteurs',
          valeur: false,
          type: 'boolean'
        },
        {
          id: 14,
          categorie: 'Sécurité',
          nom: 'historiqueConnexions',
          description: 'Conserver l\'historique des connexions',
          valeur: true,
          type: 'boolean'
        },
        {
          id: 15,
          categorie: 'Sécurité',
          nom: 'alertesSuspicious',
          description: 'Alertes pour activités suspectes',
          valeur: true,
          type: 'boolean'
        }
      ];

      this.isLoading = false;
    }, 1000);
  }

  loadBackups() {
    setTimeout(() => {
      this.backups = [
        {
          id: 1,
          nom: 'backup_auto_20240124',
          date: '2024-01-24T02:00:00',
          taille: 52428800,
          type: 'AUTOMATIQUE',
          statut: 'COMPLET'
        },
        {
          id: 2,
          nom: 'backup_manual_20240123',
          date: '2024-01-23T15:30:00',
          taille: 49807360,
          type: 'MANUEL',
          statut: 'COMPLET'
        },
        {
          id: 3,
          nom: 'backup_auto_20240122',
          date: '2024-01-22T02:00:00',
          taille: 51238912,
          type: 'AUTOMATIQUE',
          statut: 'COMPLET'
        },
        {
          id: 4,
          nom: 'backup_auto_20240121',
          date: '2024-01-21T02:00:00',
          taille: 0,
          type: 'AUTOMATIQUE',
          statut: 'ERREUR'
        }
      ];
    }, 1200);
  }

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  updateParametre(parametre: Parametre) {
    console.log('Mise à jour du paramètre:', parametre);
    this.showNotification('Paramètre mis à jour avec succès', 'success');
  }

  saveAllParametres() {
    console.log('Sauvegarde de tous les paramètres');
    this.showNotification('Tous les paramètres ont été sauvegardés', 'success');
  }

  resetParametres() {
    console.log('Réinitialisation des paramètres');
    this.showNotification('Paramètres réinitialisés aux valeurs par défaut', 'warning');
  }

  createBackup() {
    console.log('Création d\'un nouveau backup');
    this.showNotification('Backup en cours de création...', 'success');
    setTimeout(() => {
      this.loadBackups();
    }, 2000);
  }

  restoreBackup(backup: BackupInfo) {
    console.log('Restauration du backup:', backup.nom);
    this.showNotification(`Restauration du backup ${backup.nom} en cours...`, 'success');
  }

  deleteBackup(backup: BackupInfo) {
    console.log('Suppression du backup:', backup.nom);
    this.backups = this.backups.filter(b => b.id !== backup.id);
    this.showNotification('Backup supprimé avec succès', 'success');
  }

  showNotification(message: string, type: 'success' | 'error' | 'warning') {
    this.notificationMessage = message;
    this.notificationType = type;
    this.showNotificationFlag = true;
    setTimeout(() => {
      this.showNotificationFlag = false;
    }, 3000);
  }

  formatTaille(taille: number): string {
    if (taille === 0) return '0 octets';
    const k = 1024;
    const sizes = ['octets', 'Ko', 'Mo', 'Go'];
    const i = Math.floor(Math.log(taille) / Math.log(k));
    return parseFloat((taille / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }

  getStatutColor(statut: string): string {
    switch (statut) {
      case 'COMPLET': return 'bg-green-100 text-green-800';
      case 'PARTIEL': return 'bg-yellow-100 text-yellow-800';
      case 'ERREUR': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getTypeColor(type: string): string {
    switch (type) {
      case 'AUTOMATIQUE': return 'bg-blue-100 text-blue-800';
      case 'MANUEL': return 'bg-purple-100 text-purple-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}
