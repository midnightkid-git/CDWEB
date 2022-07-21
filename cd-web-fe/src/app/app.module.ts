import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PrimengModule } from './shared/primeng/primeng.module';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { ShopComponent } from './components/shop/shop.component';
import { CardComponent } from './shared/components/card/card.component';
import { DetailItemComponent } from './components/detail-item/detail-item.component';
import { CartComponent } from './components/cart/cart.component';
import { CartItemComponent } from './shared/components/cart-item/cart-item.component';
import { OrderComponent } from './components/order/order.component';
import { AdminComponent } from './components/admin/admin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ListOrdersComponent } from './components/list-orders/list-orders.component';
import { OrderCardComponent } from './shared/components/order-card/order-card.component';
import { HttpClientModule } from '@angular/common/http';
import { ProductsComponent } from './components/admin/products/products.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    ShopComponent,
    CardComponent,
    DetailItemComponent,
    CartComponent,
    CartItemComponent,
    OrderComponent,
    AdminComponent,
    ListOrdersComponent,
    OrderCardComponent,
    ProductsComponent,
  ],
  imports: [
    BrowserModule,
    ScrollingModule,
    AppRoutingModule,
    HttpClientModule,
    PrimengModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
