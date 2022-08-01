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
  // public detailInfo: any =[
  //   {
  //   //   id: 'name',
  //   //   header: 'Name',
  //   //   info: "DatMai"
  //   // },
  //   // {
  //   //   id: 'address',
  //   //   header: 'Address',
  //   //   info: "Hoa Binh"
  //   // },
  //   // {
  //   //   id: 'phone',
  //   //   header: 'Phone',
  //   //   info: "Vip"
  //   // },
  //   // {
  //     id: 'price',
  //     header: 'Total price',
  //     info: "1"
  //   }
  // ];
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
  remove(e: any, i: any) {
    this.productsService.removeCartItem(e.cartId).subscribe(data => {
      this.cart.splice(i, 1)
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
  // initMockCart(): void {
  //   this.cart = {
  //     listProducts: [
  //       {
  //         productId: "asbsa",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "XL",
  //         totalItem: 2,
  //         itemLeft: 7,
  //         price: 15,
  //         totalPrice: 30,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       },
  //       {
  //         productId: "asbsa",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 1,
  //         itemLeft: 2,
  //         price: 15,
  //         totalPrice: 15,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       },
  //       {
  //         productId: "itemabc",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 3,
  //         itemLeft: 5,
  //         price: 25,
  //         totalPrice: 75,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       }
  //       ,
  //       {
  //         productId: "itemabc",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 1,
  //         itemLeft: 1,
  //         price: 25,
  //         totalPrice: 25
  //       }
  //       ,
  //       {
  //         productId: "itemabc",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 1,
  //         itemLeft: 2,
  //         price: 25,
  //         totalPrice: 25,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       }
  //       ,
  //       {
  //         productId: "itemabc",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 1,
  //         itemLeft: 5,
  //         price: 25,
  //         totalPrice: 25,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       }
  //       ,
  //       {
  //         productId: "itemabc",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 1,
  //         itemLeft: 2,
  //         price: 25,
  //         totalPrice: 25,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       }
  //       ,
  //       {
  //         productId: "itemabc",
  //         productLabel: "ASGGAS",
  //         thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
  //         size: "L",
  //         totalItem: 5,
  //         itemLeft: 7,
  //         price: 10,
  //         totalPrice: 50,
  //         categoryId: "asgas",
  //         categoryLabel: "Vest",
  //       }
  //     ],
  //     totalPrice: 70
  //   }
  //   this.user = {
  //     username: "huongnx2",
  //     address: "ABC",
  //     phoneNumber: "213154712",
  //     fullName: "Nguyen Xuan Huong"
  //   }

  // this.detailInfo =
  // }


}
