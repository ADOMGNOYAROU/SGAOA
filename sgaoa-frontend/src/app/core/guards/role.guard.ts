import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Role } from '../models/utilisateur.model';

export const roleGuard = (allowedRoles: Role[]): CanActivateFn => {
  return (route, state) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (!authService.isLoggedIn()) {
      router.navigate(['/auth/login']);
      return false;
    }

    const user = authService.getCurrentUser();
    if (user && allowedRoles.includes(user.role)) {
      return true;
    }

    router.navigate(['/unauthorized']);
    return false;
  };
};
