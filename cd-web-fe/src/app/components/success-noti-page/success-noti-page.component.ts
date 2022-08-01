import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-success-noti-page',
  templateUrl: './success-noti-page.component.html',
  styleUrls: ['./success-noti-page.component.scss']
})
export class SuccessNotiPageComponent implements OnInit {

  public steps = [
    { label: 'Packaging' },
    { label: 'Delivering' },
    { label: 'Completed' }
  ];
  public activeIndex = 0;
  constructor() { }

  ngOnInit(): void {
    this.fetchStep();
  }
  fetchStep(): void {
    this.activeIndex = 2;
  }
}
