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

  submitted?: boolean;

  selectedImage: any;

  statuses: any[] = [];

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
      this.products = data.data.data;
      console.log('checking data', data);
    });
  }

  addAttribute() {}

  initMockData() {
    this.products = [
      {
        id: 1,
        productName: 'Áo Polo',
        description:
          'Sản phẩm được sản xuất chất liệu cotton, chất lượng, giá rẻ',
        originalPrice: 50000.0,
        originalQuantity: 20,
        discount: 0,
        imageLinks: ['link-hinh-1', 'link-hinh-2'],
        attributeAndVariants: [
          {
            attributeId: 1,
            attributeName: 'Kích Thước',
            variantNames: ['M', 'S'],
          },
          {
            attributeId: 2,
            attributeName: 'Màu Sắc',
            variantNames: ['Đỏ', 'Xanh'],
          },
        ],
        brandName: 'Nike',
        categoryName: 'Áo',
        createdDate: '2022-06-21T04:42:33.197+00:00',
        modifiedDate: '2022-06-21T04:42:33.197+00:00',
      },
    ];
  }

  openNew() {
    this.product = {};
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    let listProductToDel: any[] = [];
    this.selectedProducts.forEach(e=>{
      listProductToDel.push(e.id);
    })
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
    this.productDialog = true;
  }

  deleteProduct(product: any) {
    let listProductToDel: any[] = [];
      this.selectedProducts.forEach(e=>{
        listProductToDel.push(e.id);
      })
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

  validateProduct() {
    if (
      !this.product.productName ||
      !this.product.categoryName ||
      !this.product.originalPrice ||
      !this.product.originalQuantity ||
      !this.product.brandName ||
      !this.selectedImage
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
    this.submitted = true;
    this.product.sizes = [];

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
}
