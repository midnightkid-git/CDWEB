import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BrandsService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + sessionStorage.getItem('adminToken')
    })
  };
  constructor(private http: HttpClient) { }

  getBrands() {
    return this.http.get(`${environment.apiUrl}/admin/brand`, this.httpOptions)
  }

  postBrands(param: any) {
    return this.http.post(`${environment.apiUrl}/admin/brand`, param, this.httpOptions)
  }

  putBrands(param: any, id: any) {
    return this.http.put(`${environment.apiUrl}/admin/brand/${id}`, param, this.httpOptions)
  }

}
