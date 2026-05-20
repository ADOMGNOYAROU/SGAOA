export enum StatutOrphelin {
  DISPONIBLE = 'DISPONIBLE',
  EN_ADOPTION = 'EN_ADOPTION',
  ADOPTÉ = 'ADOPTÉ'
}

export interface Orphelin {
  id: number;
  nom: string;
  prenom: string;
  dateNaissance: string;
  lieuNaissance?: string;
  sexe: string;
  etatSante?: string;
  historiqueMedical?: string;
  situationFamiliale?: string;
  statut: StatutOrphelin;
  age?: number;
}
