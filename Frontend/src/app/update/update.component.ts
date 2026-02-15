import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-update',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css'] 
})
export class UpdateComponent implements OnInit {

  accountId!: number;

  updateData = {
    emailId: '',
    phoneNum: ''
  };

  message = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  // ✅ ONLY get accountId — NOTHING ELSE
  ngOnInit(): void {
    this.accountId = Number(this.route.snapshot.paramMap.get('id'));
  }

  updateContact() {
    this.message = '';
    this.errorMessage = '';

    this.http.put(
      `http://localhost:8080/v1/accounts/${this.accountId}/contact`,
      this.updateData
    ).subscribe({
      next: () => {
        this.message = "Contact updated successfully!";
        
        // Optional: clear form after update
        this.updateData.emailId = '';
        this.updateData.phoneNum = '';
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = "Update failed. Please try again.";
      }
    });
  }
}
