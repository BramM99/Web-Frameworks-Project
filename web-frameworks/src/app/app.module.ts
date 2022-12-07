import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {ScootersSbService} from './services/scooters-sb.service';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/mainpage/header/header.component';
import {HomeComponent} from './component/mainpage/home/home.component';
import {NavBarComponent} from './component/nav-bar/nav-bar.component';
import {LeftColumnComponent} from './component/mainpage/left-column/left-column.component';
import {RightColumnComponent} from './component/mainpage/right-column/right-column.component';
import {Overview1Component} from './component/scooters/overview1/overview1.component';
import {Overview2Component} from './component/scooters/overview2/overview2.component';
import {Detail2Component} from './component/scooters/detail2/detail2.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Detail3Component} from './component/scooters/detail3/detail3.component';
import {Overview3Component} from './component/scooters/overview3/overview3.component';
import {ErrorComponent} from './component/mainpage/error/error.component';
import {Overview4Component} from './component/scooters/overview4/overview4.component';
import {Detail4Component} from './component/scooters/detail4/detail4.component';
import {Detail41Component} from './component/scooters/detail41/detail41.component';
import {CanDeactivateGuard} from './services/can-deactivate-guard.service';
import {Detail4qpComponent} from './component/scooters/detail4qp/detail4qp.component';
import {Overview4qpComponent} from './component/scooters/overview4qp/overview4qp.component';
import {Detail5Component} from './component/scooters/detail5/detail5.component';
import {Overview5Component} from './component/scooters/overview5/overview5.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { HeaderSbComponent } from './component/mainpage/header-sb/header-sb.component';
import { SignOnComponent } from './component/mainpage/sign-on/sign-on.component';
import { NavBarSbComponent } from './component/mainpage/nav-bar-sb/nav-bar-sb.component';
import { AuthInterceptorService } from './services/auth-interceptor.service';
import { Detail51Component } from './component/scooters/detail51/detail51.component';
import { SignUpComponent } from './component/mainpage/sign-up/sign-up.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    NavBarComponent,
    LeftColumnComponent,
    RightColumnComponent,
    Overview1Component,
    Overview2Component,
    Detail2Component,
    Detail3Component,
    Overview3Component,
    ErrorComponent,
    Overview4Component,
    Detail4Component,
    Detail41Component,
    Detail4qpComponent,
    Overview4qpComponent,
    Detail5Component,
    Overview5Component,
    HeaderSbComponent,
    SignOnComponent,
    NavBarSbComponent,
    Detail51Component,
    SignUpComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}, Overview1Component, CanDeactivateGuard, ScootersSbService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
