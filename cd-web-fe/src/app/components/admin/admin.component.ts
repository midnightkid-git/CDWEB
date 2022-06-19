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
      label: 'Dashboard',
      icon: 'pi pi-percentage'
    },
    {
      label: 'Products',
      icon: 'pi pi-database'
    },
    {
      label: 'Orders',
      icon: 'pi pi-book'
    },
    {
      label: 'Users',
      icon: 'pi pi-users'
    }
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
