import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { ProductsService } from '../services/products.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent implements OnInit {
  productDialog?: boolean;

  products: any[] = [];

  product: any;

  selectedProducts: any[] = [];

  brands: any[] = [];

  submitted?: boolean;

  selectedImage: any;

  statuses: any[] = [];

  sizes: any[] = [{ name: 'XL' }, { name: 'L' }, { name: 'S' }, { name: 'M' }];

  sizeArray: any[] = [];

  constructor(
    private productsService: ProductsService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.authService.token.subscribe((data) => {
      console.log(data);
    });

    this.productsService.getProducts().subscribe((data) => {
      data.data.map((item: any) => {
        if (item.active) {
          this.products.push(item);
        }
      });
    });

    this.productsService.getBrands().subscribe((data) => {
      this.brands = data.data;
    });
  }

  addAttribute() {}

  openNew() {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
    this.sizeArray = [
      {
        id: Date.now(),
        size_id: this.sizes[0].name,
        quantity: 0,
      },
    ];
  }

  deleteSelectedProducts() {
    let listProductToDel: any[] = [];
    this.selectedProducts.forEach((e) => {
      listProductToDel.push(e.id);
    });
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.productsService
          .deleteProduct(listProductToDel)
          .subscribe((data) => {
            console.log('delete successfully', data);
          });
        this.products = this.products.filter(
          (val) => !this.selectedProducts.includes(val)
        );
        this.selectedProducts = [];
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Products Deleted',
          life: 3000,
        });
      },
    });
  }

  editProduct(product: any) {
    this.product = { ...product };
    this.sizeArray = product.listSizes;
    console.log(product.listSizes);
    console.log(this.sizeArray);
    this.productDialog = true;
  }

  deleteProduct(product: any) {
    let listProductToDel: any[] = [];
    listProductToDel.push(product.id);
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + product.name + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.productsService
          .deleteProduct(listProductToDel)
          .subscribe((data) => {
            console.log('delete successfully', data);
          });
        this.products = this.products.filter((val) => val.id !== product.id);
        this.product = {};
        this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Product Deleted',
          life: 3000,
        });
      },
    });
  }

  validateProduct(): boolean {
    if (
      !this.product.productName ||
      !this.product.categoryName ||
      !this.product.originalPrice ||
      !this.product.originalQuantity ||
      !this.product.brandName ||
      !this.selectedImage ||
      this.sizeArray.length <= 0 ||
      this.sizeArray.some((size) => !size.quantity && size.quantity >= 0) //size quantity required
    ) {
      return false;
    } else {
      return true;
    }
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    // Validate form
    // if (!this.validateProduct()) {
    //   this.messageService.add({
    //     severity: 'error',
    //     summary: 'Error',
    //     detail: 'Form value is invalid',
    //     life: 3000,
    //   });
    //   return;
    // }

    this.submitted = true;
    this.product.sizes = this.sizeArray.map((size: any) => {
      const result = { size_id: '', quantity: 0 };
      result['size_id'] = size.size_id;
      result['quantity'] = size.quantity;
      return result;
    });

    if (this.product.productName.trim()) {
      if (this.product.id) {
        this.products[this.findIndexById(this.product.id)] = this.product;
        this.messageService.add({
          severity: 'dark',
          summary: 'Successful',
          detail: 'Product Updated',
          life: 3000,
        });
        this.productsService
          .updateProduct(this.product, this.product.id)
          .subscribe((data) => {
            console.log('update response', data);
            this.products = [...this.products];
            this.product = {};
            this.productDialog = false;
          });
      } else {
        console.log(this.product);
        // this.product.id = this.createId();
        this.product.imageLinks = [this.selectedImage];
        this.products.push(this.product);
        this.messageService.add({
          severity: 'dark',
          summary: 'Successful',
          detail: 'Product Created',
          life: 3000,
        });
        this.productsService.saveProduct(this.product).subscribe((data) => {
          console.log('response', data);
          this.products = [...this.products];
          this.product = {};
          this.productDialog = false;
        });
      }
    }
  }

  findIndexById(id: string): number {
    let index = -1;
    for (let i = 0; i < this.products.length; i++) {
      if (this.products[i].id === id) {
        index = i;
        break;
      }
    }

    return index;
  }

  createId(): string {
    let id = '';
    var chars =
      'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (var i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }

  addSize(): void {
    this.sizeArray.push({
      id: Date.now(), // <--- uniqueness hook.
      size_id: this.sizes[0].name,
      quantity: 0,
    });
  }

  removeSize(index: number): void {
    this.sizeArray.splice(index, 1);
  }
}
