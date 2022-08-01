import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss'],
})
export class ShopComponent implements OnInit {
  public categories: any[] = [];
  public listOfSize: any[] = [];
  public listProducts: any[] = [];
  public maxPrice: number = 1000;
  public rangeValues: any[] = [0, this.maxPrice];
  public isDisabledRange: boolean = false;
  constructor(
    private activatedRoute: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    // this.initMockData();
    this.getAllCategories();
    this.fetchParams();
  }

  onFilterByPrice(): void {
    console.log(this.rangeValues);
  }

  fetchParams(): void {
    this.activatedRoute.paramMap.subscribe((_param) => {
      console.log(_param.get('category'));
      const param = _param.get('category');
      if (param) {
        // Filter by category
        this.getAllProducts();
      } else {
        // Get all item
        this.getAllProducts();
      }
    });
  }

  getAllCategories(): void {
    this.productService.getCategories().subscribe((res) => {
      if (res && res.data) {
        this.categories = res.data.map((category: any) => {
          return { categoryId: category.code, categoryLabel: category.name };
        });
      }
    });
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
      }
    });
  }
}
