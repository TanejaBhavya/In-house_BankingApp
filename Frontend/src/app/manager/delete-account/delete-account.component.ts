import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-delete-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent {

  accountId!: number;
  message = '';

  constructor(private http: HttpClient) {}

  deleteAccount() {

    if (!this.accountId) {
      this.message = 'Please enter Account ID';
      return;
    }

    if (confirm('Are you sure you want to delete this account?')) {

      this.http.delete(
        `http://localhost:8080/v1/accounts/${this.accountId}`
      ).subscribe({
        next: () => {
          this.message = 'Account deleted successfully';
          this.accountId = 0;
        },
        error: (err) => {
          console.error(err);
          this.message = 'Delete failed (Check role or ID)';
        }
      });

    }
  }
}
