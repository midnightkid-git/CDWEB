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
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ProductsComponent } from './components/admin/products/products.component';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireStorageModule, BUCKET } from '@angular/fire/compat/storage';
import { ConfirmationService, MessageService } from 'primeng/api';
import { environment } from 'src/environments/environment';
import { LoadingComponent } from './shared/loading/loading.component';
import { LoaderInterceptor } from './interceptors/loader.interceptor';
import { SafeUrlPipe } from './pipes/safe-url.pipe';
import { AdminLoginComponent } from './components/admin/admin-login/admin-login.component';
import { AdminRegisterComponent } from './components/admin/admin-register/admin-register.component';
import { DropdownModule } from 'primeng/dropdown';
import { BrandsComponent } from './components/admin/brands/brands.component';

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
    LoadingComponent,
    SafeUrlPipe,
    AdminLoginComponent,
    AdminRegisterComponent,
    BrandsComponent,
  ],

  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ScrollingModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    PrimengModule,
    DropdownModule,
    AngularFireModule.initializeApp(
      {
        apiKey: "AIzaSyD93GOBqxi37SxFNUMU5Nm8pGzFgYEEBTM",
        authDomain: "peakyblinders-12eb8.firebaseapp.com",
        projectId: "peakyblinders-12eb8",
        storageBucket: "peakyblinders-12eb8.appspot.com",
        messagingSenderId: "817951574350",
        appId: "1:817951574350:web:184c1d78d092a822fdb61f"
      }),
    AngularFireStorageModule
  ],
  providers: [
    MessageService,
    ConfirmationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptor,
      multi: true,

    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
