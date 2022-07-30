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
  public displayOtpPopup: boolean = false;
  public userName: string = ""
  public userForm: any;
  public otpForm: any;
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
    { label: 'Administrator', icon: 'pi pi-cog', routerLink: ['/admin'] }
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
    },
    { label: 'Administrator', routerLink: ['/admin'] }
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
  public isRemember: boolean = false;

  private userId: any;

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
        this.authService.user.then((user => {
          console.log(user)
          this.userName = user.fullName;
          this.role = user.role;
        }))
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
      username: ['', Validators.required],
      password: ['', Validators.required],
      gmail: ['', Validators.required],
      fullName: ['', Validators.required]
    }
    )
  }

  openLoginPopup() {
    this.displayLoginPopup = true;
    this.displayRegisterPopup = false;
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  openOtpPopup() {
    this.otpForm = this.fb.group({
      otp: ['', Validators.required],
    })
  }

  onSubmitForm(): void {
    if (this.displayLoginPopup == true) {
      const param = {
        username: this.userForm.value.username,
        password: this.userForm.value.password,
        isRememberMe: this.isRemember
      }
      this.authService.adminLogin(param).then(() => {
        this.displayLoginPopup = false;
      });
    }
    if (this.displayRegisterPopup == true) {
      const param = {
        username: this.userForm.value.username,
        password: this.userForm.value.password,
        gmail: this.userForm.value.gmail,
        fullName: this.userForm.value.fullName,
        roleCode: 'ADMIN'
      }
      console.log(param)
      this.authService.register(param).then((_x: any) => {
        this.userId = _x.data.id
        this.displayRegisterPopup = false;
        this.displayOtpPopup = true;
        this.openOtpPopup();
      });
    }
  }

  onSubmitVerifyOtp() {
    const param = {
      userId: this.userId,
      otpCode: this.otpForm.value.otp
    }
    this.authService.verifyOtp(param).then((_x: any) => {
      console.log(_x)
      this.authService.token.next(_x.data.accessToken);
      this.displayOtpPopup = false;
    })
  }

  onClickUserIcon(): void {
    this.userIconEmit.emit(true)
  }

}
