import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss']
})
export class CartItemComponent implements OnInit {
  @Input() item: any
  @Input() isEdit: boolean = true;
  @Output() removeElement = new EventEmitter<any>();
  constructor(private productsService:ProductService) { }

  ngOnInit(): void {
  }
  // remove(id:string){
  //   this.productsService.removeCartItem(id).subscribe(data=>{console.log(data);
  //   });
  // }
  removeItem(value: any) {
    this.removeElement.emit(value);
  }
}
