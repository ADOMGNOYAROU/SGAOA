import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DemandeAdoptionRequest {
  adoptantId?: number;
  orphelinId?: number;
}

export interface DemandeAdoptionResponse {
  id: number;
  reference: string;
  dateDepot: string;
  statut: string;
  motifRejet?: string;
  dateDecision?: string;
  adoptantId?: number;
  orphelinId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class DemandeAdoptionService {
  private apiUrl = 'http://localhost:8081/api/demandes-adoption';

  constructor(private http: HttpClient) {}

  getAllDemandes(): Observable<DemandeAdoptionResponse[]> {
    return this.http.get<DemandeAdoptionResponse[]>(this.apiUrl);
  }

  getDemandeById(id: number): Observable<DemandeAdoptionResponse> {
    return this.http.get<DemandeAdoptionResponse>(`${this.apiUrl}/${id}`);
  }

  getDemandesAdoptant(adoptantId: number): Observable<DemandeAdoptionResponse[]> {
    return this.http.get<DemandeAdoptionResponse[]>(`${this.apiUrl}/adoptant/${adoptantId}`);
  }

  createDemande(request: DemandeAdoptionRequest): Observable<DemandeAdoptionResponse> {
    return this.http.post<DemandeAdoptionResponse>(this.apiUrl, request);
  }

  updateDemande(id: number, request: DemandeAdoptionRequest): Observable<DemandeAdoptionResponse> {
    return this.http.put<DemandeAdoptionResponse>(`${this.apiUrl}/${id}`, request);
  }

  validerDemande(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/valider`, {});
  }

  rejeterDemande(id: number, motif: string): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/rejeter`, motif);
  }
}
