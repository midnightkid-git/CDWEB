import { Component, Input, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss'],
})
export class CardComponent implements OnInit {
  @Input() item: any;

  public selectedSize: any;

  constructor(
    private productService: ProductService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void { }

  initMockProduct() { }

  addToCart(): void {
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
        life: 3000,
      });
    });
  }
}
