import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';



import { Router } from '@angular/router';





@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  username: string = '';
  accounts: any[] = [];
  role: string = '';
  account: any;
transactions: any[] = [];

  
  constructor(private http: HttpClient, private router: Router) {}
  // constructor(private http: HttpClient) {}

 ngOnInit(): void {
  const token = localStorage.getItem('token');

  if (token) {
    const decoded: any = jwtDecode(token);

    this.username = decoded.sub;
    this.role = decoded.role || decoded.roles?.[0];
  }

  this.loadAccounts();
}


  loadAccounts() {
    this.http.get<any[]>('http://localhost:8080/v1/accounts')
      .subscribe(data => {
        this.accounts = data;
      });
  }

logout() {
  localStorage.removeItem('token');
  this.router.navigate(['']);
}
  goToAddAccount() {
  this.router.navigate(['/add-account']);
}

goToPending() {
  this.router.navigate(['/pending-requests']);
}

goToDelete() {
  this.router.navigate(['/delete-account']);
}

goToAllAccounts() {
  this.router.navigate(['/dashboard']);
}
goToTransactions(id: number) {
  this.router.navigate(['/transactions', id]);
}
goToSummary(id: number) {
  this.router.navigate(['/summary', id]);
}

goToUpdate(id: number) {
  this.router.navigate(['/update', id]);
}
}
