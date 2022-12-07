import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {SessionSbService} from './session-sb.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private session: SessionSbService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.session.getTokenFromSessionStorage();

    if (token != null){
      // pass on the cloned request to the next handler
      const authReq = req.clone({headers: req.headers.set('Authorization', token)});
      console.log(authReq);
      return next.handle(authReq);
    } else {
      // just forward the request to the next handler
      return next.handle(req);
    }
  }
}
