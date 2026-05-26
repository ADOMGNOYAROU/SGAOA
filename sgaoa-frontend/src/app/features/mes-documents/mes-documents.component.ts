import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mes-documents',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mes-documents.component.html',
  styleUrl: './mes-documents.component.scss'
})
export class MesDocumentsComponent {
  documents = signal<any[]>([]);
  isLoading = signal(true);

  constructor() {
    this.loadDocuments();
  }

  private loadDocuments(): void {
    // TODO: Charger les documents depuis l'API
    this.documents.set([]);
    this.isLoading.set(false);
  }
}
