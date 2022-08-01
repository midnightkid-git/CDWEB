import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  @ViewChild('sidebar') sidebar: any;
  public cart: any
  public isSidebarSticky = false;
  public sidebarOffset = 240;
  public user: any;
  totalPrice: any[] = [];
  totals: number = 0;
  phone: string = "";
  address: string = "";
  public isDisabledOrder: boolean = false;
  private orderId: string = 'order 1'

  @HostListener("window:scroll", ['$event'])
  onWindowScroll(event: any) {
    if (window.pageYOffset > this.sidebarOffset) {
      this.isSidebarSticky = true;
    } else {
      this.isSidebarSticky = false;
    }
    console.log(this.isSidebarSticky)
  }

  constructor(private router: Router, private productsService: ProductService) { }

  ngOnInit(): void {
    // this.initMockCart();
    this.getCart();
    // this.sidebarOffset = this.sidebar.nativeElement.offsetTop
  }
  remove(e: any) {
    this.productsService.removeCartItem(e.cartId).subscribe(data => {
      this.cart.splice(e, 1)
      this.totals = this.totals - (e.quantity * e.productPrice)
    })
  }

  onOrder(): void {
    let body = {
      "phoneNumber": this.phone,
      "address": this.address,
      "status": "Verifying"
    }
    this.productsService.order(body).subscribe(res => {
      console.log(res);
      this.router.navigateByUrl(`order/${this.orderId}`)
    });
  }

  validateOrder(): void {
  }
  getCart() {
    this.productsService.getCart().subscribe(data => {
      this.cart = data.data
      console.log(this.cart);
      this.totalPriceCal()
    })
  }
  totalPriceCal() {
    if (this.cart) {
      this.cart.forEach((item: any) => {
        let total = item.quantity * item.productPrice
        this.totalPrice.push(total);
      })
      this.totals = this.totalPrice.reduce((a, b) => a + b, 0);
    }
  }


}
