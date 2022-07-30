import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'my-auth-token'
    })
  };

  constructor(private http: HttpClient) {}

  getCategories() {
    return this.http.get<any>(`${environment.apiUrl}/user/category/no-token`, this.httpOptions);
  }

  getProducts() {
    return this.http.get<any>(`${environment.apiUrl}/user/product/no-token/1/1`, this.httpOptions);
  }
}
