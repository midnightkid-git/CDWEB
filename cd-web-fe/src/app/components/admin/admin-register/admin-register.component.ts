import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-admin-register',
  templateUrl: './admin-register.component.html',
  styleUrls: ['./admin-register.component.scss']
})
export class AdminRegisterComponent implements OnInit {
  public displayOtpPopup: boolean = false;
  public userForm: any;
  public otpForm: any;
  private userId: any;
  public errorMessage: string = '';
  public otpErrorMessage: string = '';

  constructor(private router: Router, private fb: FormBuilder, private authService: AuthService) {}

  ngOnInit(): void {
    this.errorMessage = '';
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      gmail: ['', Validators.required],
      fullName: ['', Validators.required]
    });
  }

  onSubmitForm(): void {
    const param = {
      username: this.userForm.value.username,
      password: this.userForm.value.password,
      gmail: this.userForm.value.gmail,
      fullName: this.userForm.value.fullName,
      roleCode: 'ADMIN'
    };
    this.authService.register(param).then((_x: any) => {
      this.errorMessage = '';
      this.userId = _x.data.id
      this.displayOtpPopup = true;
      this.openOtpPopup();
    }).catch((err: any) => {
      this.errorMessage = err.error.message;
    });
  }

  openOtpPopup() {
    this.otpErrorMessage = '';
    this.otpForm = this.fb.group({
      otp: ['', Validators.required],
    });
  }

  onSubmitVerifyOtp() {
    const param = {
      userId: this.userId,
      otpCode: this.otpForm.value.otp
    };
    this.authService.verifyOtp(param).then((_x: any) => {
      console.log(_x)
      this.authService.token.next(_x.data.accessToken);
      this.displayOtpPopup = false;
      this.navigateToLogin();
    }).catch((err: any) => {
      this.otpErrorMessage = err.error.message;
    });
  }
  
  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }
}
