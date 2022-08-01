import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-detail-item',
  templateUrl: './detail-item.component.html',
  styleUrls: ['./detail-item.component.scss'],
})
export class DetailItemComponent implements OnInit {
  public item: any;
  public selectedSize: any;
  public totalItem: number = 0;
  public selectedQuantity: number = 1;
  public isLogin: boolean = false;

  constructor(
    private activateeRoute: ActivatedRoute,
    private productService: ProductService,
    private messageService: MessageService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.fetchLogin();
    this.fetchDataByRoute();
  }

  fetchLogin() {
    this.authService.isLogin$.subscribe(_x => {
      this.isLogin = _x
    })
  }

  fetchDataByRoute() {
    this.activateeRoute.paramMap.subscribe((_param) => {
      this.productService.getProduct(_param.get('id')).subscribe((res) => {
        console.log('Checking res: ', res);
        this.item = res.data;
        this.item.totalItem = this.item.listSizes.reduce((a: any, b: any) => {
          return a + b['quantity'];
        }, 0);
      });
      // this.initMockData();
    });
  }

  addToCart(): void {
    if (this.isLogin) {
      const payload = {
        productId: this.item.id,
        size: this.selectedSize.size_id,
        quantity: this.selectedQuantity,
      };
      this.productService.addToCart(payload).subscribe((res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: res.message,
          life: 500,
        });
      }, error => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: "Add item to cart failed",
          life: 500,
        });
      });
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: "You must login first",
        life: 500,
      });
    }
  }
}
