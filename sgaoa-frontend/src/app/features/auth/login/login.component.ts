import { Component, inject, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Role } from '../../../core/models/utilisateur.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  showPassword = signal(false);
  isLoading = signal(false);
  errorMessage = signal('');

  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  constructor() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      rememberMe: [false]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.markFormGroupTouched(this.loginForm);
      return;
    }

    this.isLoading.set(true);
    this.errorMessage.set('');

    const { email, password } = this.loginForm.value;

    this.authService.login({ email, motDePasse: password }).subscribe({
      next: () => {
        this.isLoading.set(false);
        const user = this.authService.getCurrentUser();

        // Rediriger selon le rôle de l'utilisateur
        switch (user?.role) {
          case Role.ADMINISTRATEUR:
            this.router.navigate(['/dashboard']);
            break;
          case Role.PRESIDENT_COMITE:
            this.router.navigate(['/dashboard/president']);
            break;
          case Role.SECRETAIRE:
            this.router.navigate(['/dashboard/secretaire']);
            break;
          case Role.AGENT_SOCIAL:
            this.router.navigate(['/dashboard/agent']);
            break;
          case Role.ADOPTANT:
            this.router.navigate(['/dashboard/adoptant']);
            break;
          default:
            this.router.navigate(['/dashboard']);
        }
      },
      error: (err) => {
        this.isLoading.set(false);
        this.errorMessage.set(err.error?.message || 'Email ou mot de passe incorrect');
      }
    });
  }

  togglePassword(): void {
    this.showPassword.update(value => !value);
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
