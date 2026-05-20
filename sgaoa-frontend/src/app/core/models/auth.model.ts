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
}

export interface AuthResponse {
  token: string;
  utilisateur: {
    id: number;
    nom: string;
    prenom: string;
    email: string;
    role: string;
    statut: string;
  };
}
