import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Utilisateur, StatutCompte } from '../models/utilisateur.model';

@Injectable({
  providedIn: 'root'
})
export class CompteService {
  private apiUrl = 'http://localhost:8081/api/comptes';

  constructor(private http: HttpClient) {}

  getAllComptes(): Observable<Utilisateur[]> {
    return this.http.get<Utilisateur[]>(this.apiUrl);
  }

  getCompteById(id: number): Observable<Utilisateur> {
    return this.http.get<Utilisateur>(`${this.apiUrl}/${id}`);
  }

  createCompte(compte: Partial<Utilisateur>): Observable<Utilisateur> {
    return this.http.post<Utilisateur>(this.apiUrl, compte);
  }

  updateCompte(id: number, compte: Partial<Utilisateur>): Observable<Utilisateur> {
    return this.http.put<Utilisateur>(`${this.apiUrl}/${id}`, compte);
  }

  deleteCompte(id: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/${id}`);
  }

  changeStatut(id: number, statut: StatutCompte): Observable<string> {
    return this.http.patch<string>(`${this.apiUrl}/${id}/statut?statut=${statut}`, {});
  }
}
