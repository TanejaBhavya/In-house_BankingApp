import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-summary',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  accountId!: number;
  account: any;
  transactions: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
  this.accountId = Number(this.route.snapshot.paramMap.get('id'));

  this.http.get<any>(
    `http://localhost:8080/v1/accounts/summary/${this.accountId}`
  ).subscribe({
    next: (res) => {
      console.log(res); 
      this.account = res;
      this.transactions = res.transactions || [];
    },
    error: (err) => console.error(err)
  });
}

}
