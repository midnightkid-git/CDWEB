import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  public listMenu: MenuItem[] = [
    {
      label: 'Brands',
      icon: 'pi pi-bookmark-fill',
      routerLink: '/admin/brands',

    },
    {
      label: 'Products',
      icon: 'pi pi-database',
      routerLink: '/admin/products',
    },
    {
      label: 'Orders',
      icon: 'pi pi-book',
      routerLink: '/admin/orders',
    },
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
