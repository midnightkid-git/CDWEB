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
  getCart(){
    return this.http.get<any>(
      `${environment.apiUrl}/user/cart`,this.httpOptions_Token);
  }
  removeCartItem(id:any){
    return this.http.delete<any>(
      `${environment.apiUrl}/user/cart/${id}`,this.httpOptions_Token);
  }
  order(param: any){
    return this.http.post<any>(
      `${environment.apiUrl}/user/cart/order`,
      param,
      this.httpOptions_Token
    );
  }
  getOrder(){
    return this.http.get<any>(
      `${environment.apiUrl}/user/order`,this.httpOptions_Token);
  }
}
