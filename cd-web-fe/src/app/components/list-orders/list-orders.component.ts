import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list-orders',
  templateUrl: './list-orders.component.html',
  styleUrls: ['./list-orders.component.scss']
})
export class ListOrdersComponent implements OnInit {

  public listOrders: any[] = []

  constructor() { }

  ngOnInit(): void {
    this.initMockListOrders();
  }

  initMockListOrders(): void {
    this.listOrders = [
      {
        orderId: "1125482",
        status: "Delivering",
        address: "TP.HCM",
        phoneNumber: "0123456789",
        totalPrice: 70
      },
      {
        orderId: "51387221",
        status: "Packaging",
        address: "Ha Noi",
        phoneNumber: "032158721",
        totalPrice: 70
      },
      {
        orderId: "123564815",
        status: "Completed",
        address: "Dong Hoa - Di An - Binh Duong",
        phoneNumber: "25405784",
        totalPrice: 70
      },
      {
        orderId: "123224815",
        status: "Failed",
        address: "Binh Duong",
        phoneNumber: "3631636",
        totalPrice: 12
      }
    ]
  }

}
