import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detail-item',
  templateUrl: './detail-item.component.html',
  styleUrls: ['./detail-item.component.scss']
})
export class DetailItemComponent implements OnInit {

  public item: any;
  public selectedSize: any
  public totalItem: number = 0;

  constructor(private activateeRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.fetchDataByRoute()
  }

  fetchDataByRoute() {
    this.activateeRoute.paramMap.subscribe(_param => {
      console.log(_param.get("id"))
      this.initMockData();
    })
  }

  initMockData() {
    this.item = {
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
      totalItem: 6,
      categoryId: "asgas",
      categoryLabel: "Vest",
      thumbnails: [
        "https://cdn.shopify.com/s/files/1/0613/8662/1177/products/Cillian-Murphy-Peaky-Blinders-Thomas-Shelby-Coat_1024x1024@2x.jpg"
      ]
    }
  }

}
