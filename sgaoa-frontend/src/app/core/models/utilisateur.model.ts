export enum Role {
  ADOPTANT = 'ADOPTANT',
  PRESIDENT_COMITE = 'PRESIDENT_COMITE',
  SECRETAIRE = 'SECRETAIRE',
  AGENT_SOCIAL = 'AGENT_SOCIAL',
  ADMINISTRATEUR = 'ADMINISTRATEUR'
}

export enum StatutCompte {
  EN_ATTENTE_VALIDATION = 'EN_ATTENTE_VALIDATION',
  ACTIF = 'ACTIF',
  SUSPENDU = 'SUSPENDU',
  INACTIF = 'INACTIF'
}

export interface Utilisateur {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone?: string;
  role: Role;
  statut: StatutCompte;
  dateCreation: string;
  dateModification?: string;
  dernierConnexion?: string;
  emailVerifie?: boolean;
}
