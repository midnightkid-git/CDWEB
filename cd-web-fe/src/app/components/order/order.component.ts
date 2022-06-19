import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  public cart: any
  public isSidebarSticky = false;
  public sidebarOffset = 175;
  public user: any;
  public detailInfo: any;
  public isDisabledOrder: boolean = false;
  public orderId: string = 'safa13-3-ras'

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
    { label: 'Packaging' },
    { label: 'Delivering' },
    { label: 'Completed' }
  ];
  public activeIndex = 0;

  constructor() { }

  ngOnInit(): void {
    this.fetchStep();
    this.initMockCart();
  }

  fetchStep(): void {
    this.activeIndex = 1;
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
      phoneNumber: "213154712"
    }
    this.detailInfo = [
      {
        id: 'orderId',
        header: 'Order number',
        info: this.orderId
      },
      {
        id: 'name',
        header: 'Name',
        info: this.user.username
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
