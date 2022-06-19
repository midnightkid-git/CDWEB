import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

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
  public detailInfo: any;
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

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.initMockCart();
    // this.sidebarOffset = this.sidebar.nativeElement.offsetTop
  }


  onOrder(): void {
    this.router.navigateByUrl(`order/${this.orderId}`)
  }

  validateOrder(): void {
  }

  initMockCart(): void {
    this.cart = {
      listProducts: [
        {
          productId: "asbsa",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "XL",
          totalItem: 2,
          itemLeft: 7,
          price: 15,
          totalPrice: 30,
          categoryId: "asgas",
          categoryLabel: "Vest",
        },
        {
          productId: "asbsa",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 1,
          itemLeft: 2,
          price: 15,
          totalPrice: 15,
          categoryId: "asgas",
          categoryLabel: "Vest",
        },
        {
          productId: "itemabc",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 3,
          itemLeft: 5,
          price: 25,
          totalPrice: 75,
          categoryId: "asgas",
          categoryLabel: "Vest",
        }
        ,
        {
          productId: "itemabc",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 1,
          itemLeft: 1,
          price: 25,
          totalPrice: 25
        }
        ,
        {
          productId: "itemabc",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 1,
          itemLeft: 2,
          price: 25,
          totalPrice: 25,
          categoryId: "asgas",
          categoryLabel: "Vest",
        }
        ,
        {
          productId: "itemabc",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 1,
          itemLeft: 5,
          price: 25,
          totalPrice: 25,
          categoryId: "asgas",
          categoryLabel: "Vest",
        }
        ,
        {
          productId: "itemabc",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 1,
          itemLeft: 2,
          price: 25,
          totalPrice: 25,
          categoryId: "asgas",
          categoryLabel: "Vest",
        }
        ,
        {
          productId: "itemabc",
          productLabel: "ASGGAS",
          thumbnails: ["https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"],
          size: "L",
          totalItem: 5,
          itemLeft: 7,
          price: 10,
          totalPrice: 50,
          categoryId: "asgas",
          categoryLabel: "Vest",
        }
      ],
      totalPrice: 70
    }
    this.user = {
      username: "huongnx2",
      address: "ABC",
      phoneNumber: "213154712",
      fullName: "Nguyen Xuan Huong"
    }
    this.detailInfo = [
      {
        id: 'name',
        header: 'Name',
        info: this.user.fullName
      },
      {
        id: 'address',
        header: 'Address',
        info: this.user.address
      },
      {
        id: 'phone',
        header: 'Phone',
        info: this.user.phoneNumber
      },
      {
        id: 'price',
        header: 'Total price',
        info: this.cart.totalPrice
      }
    ]
  }


}
