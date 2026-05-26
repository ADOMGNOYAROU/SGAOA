import {
  HttpRequest,
  HttpHandlerFn,
  HttpEvent,
  HttpInterceptorFn,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export const errorInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('HTTP Error:', error);

      // Gérer différents types d'erreurs
      let errorMessage = 'Une erreur est survenue. Veuillez réessayer plus tard.';

      if (error.error instanceof ErrorEvent) {
        // Erreur côté client
        errorMessage = `Erreur client: ${error.error.message}`;
      } else {
        // Erreur côté serveur
        // Récupérer le message d'erreur du backend si disponible
        if (error.error && error.error.message) {
          errorMessage = error.error.message;
        } else {
          switch (error.status) {
            case 400:
              errorMessage = 'Requête invalide. Veuillez vérifier les données saisies.';
              break;
            case 401:
              errorMessage = 'Non autorisé. Veuillez vous reconnecter.';
              break;
            case 403:
              errorMessage = 'Accès refusé. Permissions insuffisantes.';
              break;
            case 404:
              errorMessage = 'Ressource non trouvée.';
              break;
            case 500:
              errorMessage = 'Erreur serveur interne. Veuillez réessayer plus tard.';
              break;
            case 503:
              errorMessage = 'Service indisponible. Veuillez réessayer plus tard.';
              break;
            default:
              errorMessage = `Erreur ${error.status}: ${error.message}`;
          }
        }
      }

      // Afficher une notification à l'utilisateur (implémenter plus tard)
      if (typeof window !== 'undefined' && typeof window.alert !== 'undefined') {
        // Pour l'instant, utiliser console.log. Plus tard, utiliser un service de notification
        console.log('Notification utilisateur:', errorMessage);
      }

      return throwError(() => errorMessage);
    })
  );
};
