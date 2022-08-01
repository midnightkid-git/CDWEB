import { Component, Input, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
  @Input() item: any;

  public selectedSize: any;

  public isLogin = false;

  constructor(
    private productService: ProductService,
    private messageService: MessageService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.fetchIsLogin()
  }


  addToCart(): void {
    if (this.isLogin) {
      console.log(this.isLogin)
      const payload = {
        productId: this.item.productId,
        size: this.selectedSize.size_id,
        quantity: 1,
      };
      this.productService.addToCart(payload).subscribe((res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: res.message,
          life: 500,
        });
      });
    } else {
      console.log("fail")
      this.messageService.add({
        severity: 'error',
        summary: 'Failed',
        detail: "You must log in first",
        life: 500,
      });
    }

  }

  fetchIsLogin() {
    this.authService.isLogin$.subscribe(_x => {
      this.isLogin = _x
    })
  }
}
