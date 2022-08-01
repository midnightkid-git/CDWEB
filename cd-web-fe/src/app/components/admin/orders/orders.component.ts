import { Component, OnDestroy, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { OrdersService } from '../services/orders.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent implements OnInit, OnDestroy {

  showDetailDialog = false;
  STATUS = [
    "Verifying",
    "Packaging",
    "Delivering",
    "Receiving",
    "Completed",
    "Failed"
  ]

  selectedStatus: any;

  showDialog: boolean = false;

  searchContent: string = '';

  subscriptions: Subscription[] = [];

  order: any;


  orders: any[] = []

  constructor(private ordersService: OrdersService,
    private messageService: MessageService) { }
  ngOnDestroy(): void {
    this.subscriptions.forEach(_x => {
      _x.unsubscribe();
    })
  }

  ngOnInit(): void {
    this.fetchOrders()
  }

  viewOrder(order: any) {
    this.order = order;
    this.showDetailDialog = true;
  }

  updateStatus(order: any) {
    this.order = order;
    this.selectedStatus = order.status;
    this.showDialog = true;
  }

  fetchOrders() {
    this.subscriptions.push(
      this.ordersService.getAllOrders().subscribe((_x: any) => {
        this.orders = _x.data
      })
    )
  }
  hideDialog() {
    this.showDialog = false;
    this.fetchOrders();
  }
  saveStatus() {
    const param = {
      status: this.selectedStatus
    }
    this.ordersService.updateOrderStatus(this.order.orderId, param).subscribe(_x => {
      this.messageService.add({
        severity: 'success',
        summary: 'Successful',
        detail: 'Order Status Updated'
      });
      this.hideDialog();
    })

  }

}
