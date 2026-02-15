import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {

  accountId!: number;
  activeTab: string = 'deposit';
  message = '';
  error = '';

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router 
  ) {}

  ngOnInit(): void {
    this.accountId = Number(this.route.snapshot.paramMap.get('id'));
  }

  depositForm = this.fb.group({
    amount: ['', Validators.required]
  });

  withdrawForm = this.fb.group({
    amount: ['', Validators.required]
  });

  transferForm = this.fb.group({
    toAccountId: ['', Validators.required],
    amount: ['', Validators.required]
  });

deposit() {
  this.http.put('http://localhost:8080/v1/transactions/deposit', {
    accountId: this.accountId,
    amount: this.depositForm.value.amount
  }).subscribe({
    next: () => {
      this.success('Deposit successful');

      setTimeout(() => {
        this.router.navigate(['/dashboard']);
      }, 1500);
    },
    error: () => {
      this.fail('Deposit failed');
    }
  });
}


withdraw() {
  this.http.put<string>('http://localhost:8080/v1/transactions/withdraw', {
    accountId: this.accountId,
    amount: this.withdrawForm.value.amount
  }).subscribe({
    next: (res) => {

      if (res === 'PENDING') {
        this.success('Withdrawal request submitted. Awaiting manager approval.');
      } else {
        this.success('Withdraw successful');
      }

      setTimeout(() => {
        this.router.navigate(['/dashboard']);
      }, 1500);

    },
    error: (err) => {

      if (err.error?.message) {
        this.fail(err.error.message);
      } else {
        this.fail('Withdraw failed');
      }

    }
  });
}



transfer() {
  this.http.put('http://localhost:8080/v1/transactions/transfer', {
    fromAccountId: this.accountId,
    toAccountId: this.transferForm.value.toAccountId,
    amount: this.transferForm.value.amount
  }).subscribe({
    next: () => {
      this.success('Transfer successful');

      setTimeout(() => {
        this.router.navigate(['/dashboard']);
      }, 1500);
    },
    error: () => {
      this.fail('Transfer failed');
    }
  });
}


  success(msg: string) {
    this.message = msg;
    this.error = '';
  }

  fail(msg: string) {
    this.error = msg;
    this.message = '';
  }
}
