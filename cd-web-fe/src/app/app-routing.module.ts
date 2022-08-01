import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { AdminRegisterComponent } from './components/admin/admin-register/admin-register.component';
import { AdminComponent } from './components/admin/admin.component';
import { BrandsComponent } from './components/admin/brands/brands.component';
import { OrdersComponent } from './components/admin/orders/orders.component';
import { ProductsComponent } from './components/admin/products/products.component';
import { CartComponent } from './components/cart/cart.component';
import { DetailItemComponent } from './components/detail-item/detail-item.component';
import { HomeComponent } from './components/home/home.component';
import { ListOrdersComponent } from './components/list-orders/list-orders.component';
import { OrderComponent } from './components/order/order.component';
import { ShopComponent } from './components/shop/shop.component';
import { SuccessNotiPageComponent } from './components/success-noti-page/success-noti-page.component';
import { AdminGuard } from './guards/admin.guard';
import { CartGuard } from './guards/cart.guard';
import { OrderGuard } from './guards/order.guard';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'success',
    component: SuccessNotiPageComponent
  },
  {
    path: 'shop',
    component: ShopComponent
  },
  {
    path: 'shop/category/:category',
    component: ShopComponent
  },
  {
    path: 'shop/size/:size',
    component: ShopComponent
  },
  {
    path: 'shop/category/:category/size/:size',
    component: ShopComponent
  },
  {
    path: 'shop/size/:size/category/:category',
    component: ShopComponent
  },
  {
    path: 'detail-item/:id',
    component: DetailItemComponent
  },
  {
    canActivate: [CartGuard],
    path: 'cart',
    component: CartComponent
  },
  {
    canActivate: [OrderGuard],
    path: 'order/:id',
    component: OrderComponent
  },
  {
    canActivate: [OrderGuard],
    path: 'order',
    component: ListOrdersComponent
  },
  {
    canActivate: [AdminGuard],
    path: 'admin',
    component: AdminComponent,
    children: [
      {
        path: 'products',
        component: ProductsComponent
      },
      {
        path: 'brands',
        component: BrandsComponent
      },
      {
        path: 'orders',
        component: OrdersComponent
      }
    ]
  },
  {
    path: 'login',
    component: AdminLoginComponent
  },
  {
    path: 'register',
    component: AdminRegisterComponent
  },
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: "full"
  },
  {
    path: '**',
    redirectTo: 'home'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
