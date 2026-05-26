export enum StatutAdoptant {
  ACTIF = 'ACTIF',
  INACTIF = 'INACTIF',
  SUSPENDU = 'SUSPENDU'
}

export interface Adoptant {
  id: number;
  reference: string;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  dateNaissance: string;
  age: number;
  adresse: string;
  ville: string;
  pays: string;
  profession: string;
  revenuMensuel: number;
  statut: StatutAdoptant;
  dateInscription: string;
  dernierDossier: string;
  dossierCount: number;
  documents: number;
  verified: boolean;
}
