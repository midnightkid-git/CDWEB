import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {


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
  order: any;
  id: any;
  totalPrice: any[] = [];
  totals: number = 0;
  @HostListener("window:scroll", ['$event'])
  onWindowScroll(event: any) {
    if (window.pageYOffset > this.sidebarOffset) {
      this.isSidebarSticky = true;
    } else {
      this.isSidebarSticky = false;
    }
    console.log(this.isSidebarSticky)
  }


  public steps = [
    { label: "Verifying" },
    { label: "Packaging" },
    { label: "Delivering" },
    { label: "Receiving" },
    { label: "Completed" },
  ];
  get activeIndex() {
    let result: any;
    this.steps.forEach((_x, index) => {
      if (_x.label == this.order.status) {
        result = index
      }
    })
    return result;
  };

  constructor(private productsService: ProductService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getOrder();

  }
  getOrder() {
    this.productsService.getOrder().subscribe(data => {
      this.order = data.data[this.id - 1];
      console.log("checking order ", this.order);
      this.totalPriceCal();
    })
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
