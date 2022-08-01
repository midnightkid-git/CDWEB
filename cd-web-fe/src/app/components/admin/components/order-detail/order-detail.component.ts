import { Component, HostListener, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {

  @Input() order: any;


  STATUS = [
    "Verifying",
    "Packaging",
    "Delivering",
    "Receiving",
    "Completed",
    "Failed"
  ]
  public cart: any
  public isSidebarSticky = false;
  public sidebarOffset = 175;
  public user: any;
  public detailInfo: any;
  public isDisabledOrder: boolean = false;
  public orderId: string = 'safa13-3-ras'
  id: any;
  totalPrice: any[] = [];
  totals: number = 0;
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.totalPriceCal();

  }
  totalPriceCal() {
    if (this.order.listItem) {
      this.order.listItem.forEach((item: any) => {
        let total = item.quantity * item.price
        this.totalPrice.push(total);
      })
      this.totals = this.totalPrice.reduce((a, b) => a + b, 0);
    }
  }
}
