import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization:'Bearer ' + sessionStorage.getItem('adminToken')
    })
  };
  constructor(
    private http: HttpClient
  ) { }
  getProducts() {
    return this.http.get<any>(`${environment.apiUrl}/admin/product/getListProduct/1/10`,this.httpOptions);
  }
  saveProduct(param:any){
    return this.http.post<any>(`${environment.apiUrl}/admin/product`,param,this.httpOptions)
  }
  updateProduct(param:any, id:any){
    return this.http.put<any>(`${environment.apiUrl}/admin/product/${id}`,param,this.httpOptions)
  }
  deleteProduct(ids:any){
    return this.http.delete<any>(`${environment.apiUrl}/admin/product/${ids}`, this.httpOptions)
  }
}
