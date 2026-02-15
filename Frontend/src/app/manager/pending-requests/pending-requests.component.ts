import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pending-requests',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pending-requests.component.html',
  styleUrls: ['./pending-requests.component.css']
})
export class PendingRequestsComponent implements OnInit {

  pendingTransactions: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadPending();
  }

  loadPending() {
    this.http.get<any[]>(
      'http://localhost:8080/v1/transactions/pending'
    ).subscribe(res => {
      this.pendingTransactions = res;
    });
  }

  approve(id: number) {
    this.http.put(
      `http://localhost:8080/v1/transactions/approve/${id}`, {}
    ).subscribe({
      next: () => {
        alert('Approved successfully');
        this.loadPending();
      },
      error: () => alert('Approval failed')
    });
  }
}
