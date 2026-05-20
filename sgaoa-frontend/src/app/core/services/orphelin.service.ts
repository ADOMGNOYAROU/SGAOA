import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Orphelin, StatutOrphelin } from '../models/orphelin.model';

@Injectable({
  providedIn: 'root'
})
export class OrphelinService {
  private apiUrl = 'http://localhost:8081/api/orphelins';

  constructor(private http: HttpClient) {}

  getAllOrphelins(): Observable<Orphelin[]> {
    return this.http.get<Orphelin[]>(this.apiUrl);
  }

  getOrphelinById(id: number): Observable<Orphelin> {
    return this.http.get<Orphelin>(`${this.apiUrl}/${id}`);
  }

  getOrphelinsDisponibles(): Observable<Orphelin[]> {
    return this.http.get<Orphelin[]>(`${this.apiUrl}/disponibles`);
  }

  createOrphelin(orphelin: Partial<Orphelin>): Observable<Orphelin> {
    return this.http.post<Orphelin>(this.apiUrl, orphelin);
  }

  updateOrphelin(id: number, orphelin: Partial<Orphelin>): Observable<Orphelin> {
    return this.http.put<Orphelin>(`${this.apiUrl}/${id}`, orphelin);
  }

  deleteOrphelin(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  changeStatut(id: number, statut: StatutOrphelin): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/statut`, { statut });
  }

  searchOrphelins(query: string): Observable<Orphelin[]> {
    return this.http.get<Orphelin[]>(`${this.apiUrl}/search?q=${query}`);
  }

  getOrphelinsByStatut(statut: StatutOrphelin): Observable<Orphelin[]> {
    return this.http.get<Orphelin[]>(`${this.apiUrl}/statut/${statut}`);
  }

  getOrphelinsBySexe(sexe: string): Observable<Orphelin[]> {
    return this.http.get<Orphelin[]>(`${this.apiUrl}/sexe/${sexe}`);
  }

  getStatistiques(): Observable<any> {
    return this.http.get(`${this.apiUrl}/statistiques`);
  }
}
