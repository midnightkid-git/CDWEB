import { Component, OnDestroy, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { ProductsService } from '../services/products.service';


@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
})
export class ProductsComponent implements OnInit, OnDestroy {
  productDialog?: boolean;

  products: any[] = [];

  subscriptions: Subscription[] = []

  searchContent: string = ''

  product: any;

  selectedProducts: any[] = [];

  categories = [
    "Slacks",
    "Vests",
    "Accessories"
  ]

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
    private storage: AngularFireStorage,
    private authService: AuthService
  ) { }
  ngOnDestroy(): void {
    this.subscriptions.forEach(_x => {
      _x.unsubscribe();
    })
  }

  ngOnInit() {
    this.subscriptions.push(
      this.authService.token.subscribe((data) => {
        console.log(data);
      }));


    this.fetchProducts();
    this.subscriptions.push(
      this.productsService.getBrands().subscribe((data) => {
        this.brands = data.data;
      }));
  }

  fetchProducts() {
    this.subscriptions.push(
      this.productsService.getProducts().subscribe((data) => {
        this.products = data.data;
        console.log('checking data', data);
      }));
  }

  addAttribute() { }

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
    this.selectedProducts.forEach(e => {
      listProductToDel.push(e.id);
    });
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected products?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.subscriptions.push(
          this.productsService
            .deleteProduct(listProductToDel)
            .subscribe((data) => {
              this.fetchProducts();
              this.selectedProducts = [];
              this.messageService.add({
                severity: 'success',
                summary: 'Successful',
                detail: 'Deactivated Product',
                life: 500,
              });
            }));

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
    this.selectedProducts.forEach(e => {
      listProductToDel.push(e.id);
    })
    this.confirmationService.confirm({
      message: 'Are you sure you want to deactive this product ?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.subscriptions.push(
          this.productsService
            .deleteProduct(product.id)
            .subscribe((data) => {
              this.fetchProducts();
              this.product = {};
              this.messageService.add({
                severity: 'succes',
                summary: 'Successful',
                detail: 'Product Deleted'
              });
            }));

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

  handleFileInput(event: any) {
    this.selectedImage = event.target.files[0];
  }

  testUpload() {
    console.log(this.selectedImage)
    console.log("uploading", this.selectedImage?.name)
    const storageRef = this.storage.ref("/");
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
        this.subscriptions.push(
          this.productsService
            .updateProduct(this.product, this.product.id)
            .subscribe((data) => {
              console.log('update response', data);
              this.fetchProducts();
              this.product = {};
              this.productDialog = false;
              this.hideDialog();
              this.messageService.add({
                severity: 'success',
                summary: 'Successful',
                detail: 'Product Updated'
              });
            }));
      } else {
        const uploadTask = this.storage.upload(this.selectedImage?.name, this.selectedImage).then((x) => {
          x.ref.getDownloadURL().then(_x => {
            this.product.imageLinks = [_x];
            this.subscriptions.push(
              this.productsService.saveProduct(this.product).subscribe((data) => {
                this.fetchProducts();
                this.product = {};
                this.productDialog = false;
                this.messageService.add({
                  severity: 'success',
                  summary: 'Successful',
                  detail: 'Product Created'
                });
              }));
          })
        })

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

  activeProduct(product: any) {
    console.log(product.id);
    this.productsService.activeProduct(product.id).subscribe(() => {
      this.fetchProducts();
      this.messageService.add({
        severity: 'sucess',
        summary: 'Successful',
        detail: 'Product Activated'
      });
    });
  }

  removeSize(index: number): void {
    this.sizeArray.splice(index, 1);
  }
}
