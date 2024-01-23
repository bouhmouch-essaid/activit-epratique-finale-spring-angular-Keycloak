import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username = 'user';
  password = 'password';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.login(this.username, this.password).subscribe(
      (success: any) => {
        if (success) {
          this.router.navigate(['/products']);
        } else {
          this.errorMessage = 'Invalid username or password. Please try again.';
        }
      },
      (error: any) => {
        this.errorMessage = 'An unexpected error occurred. Please try again later.';
      }
    );
  }
}
