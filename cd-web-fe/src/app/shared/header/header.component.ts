import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash';
import { MenuItem } from 'primeng/api';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Output() userIconEmit = new EventEmitter()

  public isAdmin: boolean = true;
  public userName: string = ""
  public isLoggedIn: boolean = true;
  public menuItems: MenuItem[] = []
  public userItems: MenuItem[] = [
    {
      label: 'Profile', icon: 'pi pi-user-edit'
    },
    {
      label: 'List Ordered', icon: 'pi pi-book', routerLink: '/order'
    },
    {
      label: 'Log Out', icon: 'pi pi-arrow-left', command: () => {
        this.isLoggedIn = true;
        this.userName = ''
        this.menuItems = this.nonUserItems;
      }
    },
    { separator: true },
    { label: 'Administrator', icon: 'pi pi-cog', routerLink: ['/admin'], disabled: !this.isAdmin }
  ];
  public nonUserItems: MenuItem[] = [
    {
      label: 'Login',
      command: () => {
        this.isLoggedIn = false;
        this.userName = 'Huong'
        this.menuItems = this.userItems
      }
    },
    {
      label: 'Register'
    }
  ]
  public userHeader = ''
  public displayUserPopup: boolean = true;
  public navItems = [
    {
      id: 'home',
      label: 'Home',
      routerLink: '/home'
    },
    {
      id: 'shop',
      label: 'Shop',
      routerLink: '/shop'
    },
    {
      id: 'contacts',
      label: 'Contacts',
      routerLink: '/contacts'
    }

  ]

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.menuItems = this.nonUserItems;
  }


  checkLoggedIn(): boolean {
    return true;
  }

  onClickUserIcon(): void {
    this.userIconEmit.emit(true)
  }

}
