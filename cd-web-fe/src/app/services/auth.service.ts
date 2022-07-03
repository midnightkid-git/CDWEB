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
  public user: any;
  constructor(
    private http: HttpClient
  ) { }

  login(param: any) {
    return this.http.post(`${environment.apiUrl}/login`, param).toPromise().then((_token: any) => {
      let decoded: any = jwt_decode(_token.accessToken);
      this.user = decoded;
      this.token.next(_token.accessToken);
      window.sessionStorage.setItem("accesstoken", _token.accessToken);
    })
  }

  register(param: any): any {
    return this.http.post(`${environment.apiUrl}/register`, param).toPromise();;
  }

  logout() {
    window.sessionStorage.setItem("accesstoken", '')
    this.token.next('')
  }

}
