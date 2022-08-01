import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + sessionStorage.getItem('adminToken')
    })
  };
  constructor(private http: HttpClient) {

  }

  getAllOrders() {
    return this.http.get(`${environment.apiUrl}/admin/order`, this.httpOptions);
  }

  updateOrderStatus(id: any, param: any) {
    return this.http.put(`${environment.apiUrl}/admin/order/${id}`, param, this.httpOptions)
  }

}
