import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { BrandsService } from '../services/brands.service';

@Component({
  selector: 'app-brands',
  templateUrl: './brands.component.html',
  styleUrls: ['./brands.component.scss']
})
export class BrandsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = []

  brands: any[] = [];

  brandDialog: boolean = false;

  submitted: boolean = false;

  selectedBrands: any[] = [];

  searchContent: any;

  brand: any = {};
  constructor(
    private brandsService: BrandsService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
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


    this.fetchBrands();
  }

  fetchBrands() {
    this.subscriptions.push(
      this.brandsService.getBrands
        ().subscribe((data: any) => {
          this.brands = data.data;
          console.log('checking data', data);
        }));
  }

  openNew() {
    this.brandDialog = true;

  }

  editBrand(brand: any) {
    this.brand = brand;
    this.brandDialog = true;
  }

  hideDialog() {
    this.brandDialog = false;
    this.submitted = false;
  }

  saveBrand() {
    this.submitted = true;
    if (!this.brand.id) {
      this.subscriptions.push(
        this.brandsService.postBrands(this.brand).subscribe(_x => {
          this.brand = {};
          this.hideDialog();
          this.fetchBrands();
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Create brand successfully',
            life: 500,
          });
        })
      )
    } else {
      this.subscriptions.push(
        this.brandsService.putBrands(this.brand, this.brand.id).subscribe(_x => {
          this.brand = {};
          this.hideDialog();
          this.fetchBrands();
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Update brand successfully', life: 500
          });
        })
      )
    }
  }


}
