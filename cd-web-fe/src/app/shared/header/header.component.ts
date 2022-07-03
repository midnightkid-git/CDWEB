import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as _ from 'lodash';
import { MenuItem } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  @Output() userIconEmit = new EventEmitter()

  public role: string = '';
  public displayRegisterPopup: boolean = false;
  public displayLoginPopup: boolean = false;
  public userName: string = ""
  public userForm: any;
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
        this.menuItems = this.nonUserItems;
        this.authService.logout();
      }
    },
    { separator: true },
    { label: 'Administrator', icon: 'pi pi-cog', routerLink: ['/admin'], disabled: this.role != "Admin" }
  ];
  public nonUserItems: MenuItem[] = [
    {
      label: 'Login',
      command: () => {
        this.openLoginPopup();
      }
    },
    {
      label: 'Register',
      command: () => this.openRegisterPopup()
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

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.menuItems = this.nonUserItems;
    this.checkLogin();
  }

  checkLogin(): void {
    this.authService.token.subscribe(_token => {
      if (_token !== '') {
        this.userName = this.authService.user.fullName;
        this.role = this.authService.user.role;
        this.menuItems = this.userItems;
      } else {
        this.userName = '';
        this.role = '';
        this.menuItems = this.nonUserItems;
      }
    })
  }

  openRegisterPopup() {
    this.displayRegisterPopup = true;
    this.displayLoginPopup = false;
    this.userForm = this.fb.group({
      userName: ['', Validators.required],
      passWord: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      fullName: ['', Validators.required]
    }
    )
  }

  openLoginPopup() {
    this.displayLoginPopup = true;
    this.displayRegisterPopup = false;
    this.userForm = this.fb.group({
      userName: ['', Validators.required],
      passWord: ['', Validators.required]
    })
  }

  onSubmitForm(): void {
    if (this.displayLoginPopup == true) {
      const param = {
        username: this.userForm.value.userName,
        password: this.userForm.value.passWord
      }
      this.authService.login(param).then(() => {
        this.displayLoginPopup = false;
      });
    }
    if (this.displayRegisterPopup == true) {
      const param = {
        username: this.userForm.value.userName,
        password: this.userForm.value.passWord,
        phoneNumber: this.userForm.value.phoneNumber,
        fullName: this.userForm.value.fullName
      }
      console.log(param)
      this.authService.register(param).then((_x: any) => {
        console.log(_x)
        this.displayLoginPopup = true;
        this.displayRegisterPopup = false;
      });
    }
  }

  onClickUserIcon(): void {
    this.userIconEmit.emit(true)
  }

}
