import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'my-auth-token',
    }),
  };

  httpOptions_Token = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + sessionStorage.getItem('basicToken'),
    }),
  };

  constructor(private http: HttpClient) {}

  getCategories() {
    return this.http.get<any>(
      `${environment.apiUrl}/user/category/no-token`,
      this.httpOptions
    );
  }

  getProducts() {
    return this.http.get<any>(
      `${environment.apiUrl}/user/product/no-token`,
      this.httpOptions
    );
  }

  getProduct(id: any) {
    return this.http.get<any>(
      `${environment.apiUrl}/user/product/no-token/${id}`,
      this.httpOptions
    );
  }

  addToCart(param: any) {
    return this.http.post<any>(
      `${environment.apiUrl}/user/cart`,
      param,
      this.httpOptions_Token
    );
  }
}
