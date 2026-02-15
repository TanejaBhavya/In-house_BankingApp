import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-account',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent {

  successMessage = '';
  errorMessage = '';

  accountForm = this.fb.group({
  userName: [
    '',
    [
      Validators.required,
      Validators.pattern('^[a-zA-Z ]+$')   // Only alphabets + space
    ]
  ],
  emailId: [
    '',
    [
      Validators.required,
      Validators.email
    ]
  ],
  phoneNum: [
    '',
    [
      Validators.required,
      Validators.pattern('^[0-9]+$')  // Only numbers
    ]
  ],
  balance: [
    0,
    [
      Validators.required,
      Validators.pattern('^[0-9]+$')  // Only numbers
    ]
  ]
});


  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}


  onSubmit() {
    console.log("Button clicked");
    if (this.accountForm.invalid) {
  this.accountForm.markAllAsTouched();
  return;
}
    // if (this.accountForm.invalid) return;

  console.log("Form valid. Sending request...");

    this.http.post('http://localhost:8080/v1/accounts', this.accountForm.value)
      .subscribe({
        next: () => {
          this.successMessage = 'Account created successfully!';
          this.errorMessage = '';

          setTimeout(() => {
            this.router.navigate(['/dashboard']);
          }, 1500);
        },
        error: () => {
          this.errorMessage = 'Failed to create account';
          this.successMessage = '';
        }
      });
  }
}
