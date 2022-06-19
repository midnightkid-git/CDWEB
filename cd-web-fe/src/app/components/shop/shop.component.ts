import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {

  public categories: any[] = [];
  public listOfSize: any[] = [];
  public listProducts: any[] = [];
  public maxPrice: number = 1000;
  public rangeValues: any[] = [0, this.maxPrice];
  public isDisabledRange: boolean = false;
  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.initMockData();
    this.fetchParams();
  }

  onFilterByPrice(): void {
    console.log(this.rangeValues)
  }

  fetchParams(): void {
    this.activatedRoute.paramMap.subscribe(_param => {
      console.log(_param.get("category"))
      const param = _param.get("category")
      if (param) {
        // Filter by category
      } else {
        // Get all item
      }
    })
  }

  initMockData() {
    this.categories = [
      {
        categoryId: "category1",
        categoryLabel: "Vest"
      },
      {
        categoryId: "category2",
        categoryLabel: "Slacks"
      },
      {
        categoryId: "category3",
        categoryLabel: "Acessories"
      }
    ]
    this.listOfSize = [
      "XL",
      "L",
      "M",
      "S"
    ]

    this.listProducts = [
      {
        productId: "asbas",
        productLabel: "Tommy Shelby's Jacket",
        price: 75,
        size: [
          {
            sizeType: "XL",
            totalItem: 1
          },
          {
            sizeType: "M",
            totalItem: 0
          },
          {
            sizeType: "L",
            totalItem: 5
          }
        ],
        categoryId: "asgas",
        categoryLabel: "Vest",
        thumbnails: [
          "https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"
        ]
      },
      {
        productId: "asbas",
        productLabel: "Tommy Shelby's Jacket",
        price: 75,
        size: [
          {
            sizeType: "XL",
            totalItem: 1
          },
          {
            sizeType: "M",
            totalItem: 0
          },
          {
            sizeType: "L",
            totalItem: 5
          }
        ],
        categoryId: "asgas",
        categoryLabel: "Vest",
        thumbnails: [
          "https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"
        ]
      },
      {
        productId: "asbdas",
        productLabel: "Tommy Shelby's Vest",
        price: 75,
        size: [
          {
            sizeType: "XL",
            totalItem: 1
          },
          {
            sizeType: "M",
            totalItem: 2
          },
          {
            sizeType: "L",
            totalItem: 5
          }
        ],
        categoryId: "asgaasds",
        categoryLabel: "Slacks",
        thumbnails: [
          "https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"
        ]
      },
      {
        productId: "asbdas",
        productLabel: "Tommy Shelby's Vest",
        price: 75,
        size: [
          {
            sizeType: "XL",
            totalItem: 1
          },
          {
            sizeType: "M",
            totalItem: 2
          },
          {
            sizeType: "L",
            totalItem: 5
          }
        ],
        categoryId: "asgaasds",
        categoryLabel: "Slacks",
        thumbnails: [
          "https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"
        ]
      }
    ]
  }

}
