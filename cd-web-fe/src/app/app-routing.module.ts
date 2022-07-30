import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { ProductsComponent } from './components/admin/products/products.component';
import { CartComponent } from './components/cart/cart.component';
import { DetailItemComponent } from './components/detail-item/detail-item.component';
import { HomeComponent } from './components/home/home.component';
import { ListOrdersComponent } from './components/list-orders/list-orders.component';
import { OrderComponent } from './components/order/order.component';
import { ShopComponent } from './components/shop/shop.component';
import { AdminGuard } from './guards/admin.guard';
import { CartGuard } from './guards/cart.guard';
import { OrderGuard } from './guards/order.guard';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
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
      }
    ]
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
