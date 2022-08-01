import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import * as _ from 'lodash';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss'],
})
export class ShopComponent implements OnInit {
  public categories: any[] = ["Slacks", "Vests", "Accessories"];
  public listOfSize: any[] = ["S", "M", "L", "XL", "XXL"];
  public listProducts: any[] = [];
  public maxPrice: number = 1000;
  public rangeValues: any[] = [0, this.maxPrice];
  public isDisabledRange: boolean = false;
  public filteredProducts: any[] = []
  public filteredPageProducts: any[] = [];
  public searchContent: string = ""
  public row = 9
  public start = 0;
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    // this.initMockData();
    this.getAllProducts();
    this.fetchParams();
  }

  findByName(content?: any) {
    if (content)
      this.searchContent = content;
    this.filteredProducts = this.filteredProducts.filter(_x => {
      return _x.productLabel == this.searchContent;
    })
    if (this.searchContent == "") {
      this.fetchFilteredData();
    }
    this.start = 0;
    this.filteredPageProducts = this.filteredProducts.slice(this.start, this.start + this.row)
  }

  fetchFilteredData() {
    this.filteredProducts = this.listProducts;
    const categoryParam = this.activatedRoute.snapshot.paramMap.get('category');
    const sizeParam = this.activatedRoute.snapshot.paramMap.get('size');
    if (categoryParam) {
      this.filteredProducts = this.filteredProducts.filter((_x) => {
        return _x.categoryLabel == categoryParam
      })
    }
    if (sizeParam) {
      this.filteredProducts = this.filteredProducts.filter((_x) => {
        return _x.size.some((_size: any) => {
          return _size.size_id == sizeParam
        })
      })
    }
  }

  filterBySize(size: any) {
    let url = ''
    const sizeParam = this.activatedRoute.snapshot.paramMap.get('size');
    if (!sizeParam) {
      this.activatedRoute.snapshot.url.forEach(_x => {
        url += '/' + _x.path;
      })
      url += "/size/" + size;
    } else
      if (sizeParam) {
        const a = this.activatedRoute.snapshot.url.forEach((_x, index) => {
          if (index < this.activatedRoute.snapshot.url.length - 2) {
            url += '/' + _x
          }
        })
        url += "/size/" + size;
      }
    this.router.navigateByUrl(url)
  }

  fetchParams(): void {
    this.activatedRoute.paramMap.subscribe((_param) => {
      this.start = 0;
      this.filteredProducts = this.listProducts;
      const categoryParam = _param.get('category');
      const sizeParam = _param.get('size');
      if (categoryParam) {
        this.filteredProducts = this.filteredProducts.filter((_x) => {
          return _x.categoryLabel == categoryParam
        })
      }
      if (sizeParam) {
        this.filteredProducts = this.filteredProducts.filter((_x) => {
          return _x.size.some((_size: any) => {
            return _size.size_id == sizeParam
          })
        })
      }
      this.filteredPageProducts = this.filteredProducts.slice(this.start, this.start + this.row)


    });

  }
  getProductByFilter() {

  }

  changePage(event: any) {
    this.start = event.page * this.row;
    this.filteredPageProducts = this.filteredProducts.slice(this.start, this.start + this.row)
  }

  getAllProducts(): void {
    this.productService.getProducts().subscribe((res) => {
      console.log(res);

      if (res && res.data) {
        this.listProducts = res.data.map((product: any) => {
          return {
            productId: product.id,
            productLabel: product.productName,
            price: product.originalPrice,
            size: product.listSizes,
            categoryId: product.id,
            categoryLabel: product.categoryName,
            thumbnails: product.imageLinks,
          };
        });
        this.filteredProducts = this.listProducts;
        this.filteredPageProducts = this.filteredProducts.slice(0, 9);
        this.fetchParams();
      }
    });
  }
}
