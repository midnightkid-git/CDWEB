import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.scss']
})
export class AdminLoginComponent implements OnInit {
  public userForm: any;
  public isRemember: boolean = false;
  public errorMessage: string = '';

  constructor(private router: Router, private fb: FormBuilder, private authService: AuthService) {}

  ngOnInit(): void {
    this.errorMessage = '';
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  onSubmitForm(): void {
    const param = {
      username: this.userForm.value.username,
      password: this.userForm.value.password,
      isRememberMe: this.isRemember
    };
    this.authService.adminLogin(param).then(() => {
      this.router.navigate(['/admin']);
    }).catch((err: any) => {
      this.errorMessage = err.error.message;
    });
  }
  
  navigateToRegister(): void {
    this.router.navigate(['/register']);
  }
}
