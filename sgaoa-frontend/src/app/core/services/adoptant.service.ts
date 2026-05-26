import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Adoptant } from '../models/adoptant.model';

@Injectable({
  providedIn: 'root'
})
export class AdoptantService {
  private apiUrl = 'http://localhost:8081/api/adoptants';

  constructor(private http: HttpClient) {}

  getAllAdoptants(): Observable<Adoptant[]> {
    return this.http.get<Adoptant[]>(this.apiUrl);
  }

  getAdoptantById(id: number): Observable<Adoptant> {
    return this.http.get<Adoptant>(`${this.apiUrl}/${id}`);
  }

  createAdoptant(adoptant: Partial<Adoptant>): Observable<Adoptant> {
    return this.http.post<Adoptant>(this.apiUrl, adoptant);
  }

  updateAdoptant(id: number, adoptant: Partial<Adoptant>): Observable<Adoptant> {
    return this.http.put<Adoptant>(`${this.apiUrl}/${id}`, adoptant);
  }

  deleteAdoptant(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchAdoptants(query: string): Observable<Adoptant[]> {
    return this.http.get<Adoptant[]>(`${this.apiUrl}/search?q=${query}`);
  }

  getAdoptantsByStatut(statut: string): Observable<Adoptant[]> {
    return this.http.get<Adoptant[]>(`${this.apiUrl}/statut/${statut}`);
  }

  getStatistiques(): Observable<any> {
    return this.http.get(`${this.apiUrl}/statistiques`);
  }
}
