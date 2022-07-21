import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import jwt_decode from "jwt-decode";
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public token = new BehaviorSubject('')
  get user() {
    return this.token.toPromise().then((accessToken: any) => {
      let decoded: any = jwt_decode(accessToken);
      return decoded;
    })

  }
  constructor(
    private http: HttpClient
  ) { }

  login(param: any) {
    return this.http.post(`${environment.apiUrl}/auth/login`, param).toPromise().then((_res: any) => {
      this.token.next(_res.data.accessToken);
    })
  }

  adminLogin(param: any) {
    return this.http.post(`${environment.apiUrl}/auth/admin/login`, param).toPromise().then((_res: any) => {
      this.token.next(_res.data.accessToken);
    })
  }

  verifyOtp(param: any) {
    return this.http.post(`${environment.apiUrl}/auth/confirm`, param).toPromise();
  }

  register(param: any): any {
    return this.http.post(`${environment.apiUrl}/auth/register`, param).toPromise();;
  }

  logout() {
    window.sessionStorage.setItem("accesstoken", '')
    this.token.next('')
  }

}
