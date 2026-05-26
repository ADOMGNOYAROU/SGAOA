export interface LoginRequest {
  email: string;
  motDePasse: string;
}

export interface InscriptionRequest {
  nom: string;
  prenom: string;
  email: string;
  motDePasse: string;
  telephone?: string;
  adresse?: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  utilisateur: {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    telephone?: string;
    role: string;
    statut: string;
    emailVerifie?: boolean;
    dateCreation?: string;
    dernierConnexion?: string;
  };
}
