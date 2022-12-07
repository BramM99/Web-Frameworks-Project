import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './component/mainpage/home/home.component';
import {Overview1Component} from './component/scooters/overview1/overview1.component';
import {Overview2Component} from './component/scooters/overview2/overview2.component';
import {Overview3Component} from './component/scooters/overview3/overview3.component';
import {ErrorComponent} from './component/mainpage/error/error.component';
import {Overview4Component} from './component/scooters/overview4/overview4.component';
import {Detail4Component} from './component/scooters/detail4/detail4.component';
import {Detail41Component} from './component/scooters/detail41/detail41.component';
import {CanDeactivateGuard} from './services/can-deactivate-guard.service';
import {Detail4qpComponent} from './component/scooters/detail4qp/detail4qp.component';
import {Overview4qpComponent} from './component/scooters/overview4qp/overview4qp.component';
import {Overview5Component} from './component/scooters/overview5/overview5.component';
import {Detail5Component} from './component/scooters/detail5/detail5.component';
import {SignOnComponent} from './component/mainpage/sign-on/sign-on.component';
import {Detail51Component} from './component/scooters/detail51/detail51.component';
import {SignUpComponent} from './component/mainpage/sign-up/sign-up.component';



const appRoutes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: SignOnComponent},
  {path: 'signup', component: SignUpComponent},
  {path: 'scooters/overview1', component: Overview1Component},
  {path: 'scooters/overview2', component: Overview2Component},
  {path: 'scooters/overview3', component: Overview3Component},
  {path: 'scooters/overview4', component: Overview4Component, children: [
    {path: ':id', component: Detail4Component}
    ]},
  {path: 'scooters/overview41', component: Overview4Component, children: [
    {path: ':id', component: Detail41Component, canDeactivate: [
      CanDeactivateGuard
      ]}
    ]},
  {path: 'scooters/overview4qp', component: Overview4qpComponent, children: [
      {path: 'edit', component: Detail4qpComponent, canDeactivate: [
          CanDeactivateGuard
        ]}
    ]},
  {path: 'scooters/overview5', component: Overview5Component, children: [
      {path: ':id', component: Detail5Component}
    ]},
  {path: 'scooters/overview51', component: Overview5Component, children: [
      {path: ':id', component: Detail51Component}
    ]},
  {path: 'error', component: ErrorComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: '**', redirectTo: '/error'},
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
