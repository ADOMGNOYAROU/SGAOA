import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.scss'
})
export class NotificationsComponent {
  notifications = signal<any[]>([]);
  isLoading = signal(true);

  constructor() {
    this.loadNotifications();
  }

  private loadNotifications(): void {
    // TODO: Charger les notifications depuis l'API
    this.notifications.set([]);
    this.isLoading.set(false);
  }
}
