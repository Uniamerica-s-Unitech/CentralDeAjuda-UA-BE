import { HTTP_INTERCEPTORS, HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, of, throwError } from 'rxjs';
import { KeycloakService } from '../services/KeycloakService';

@Injectable(
  // {
  // providedIn: 'root'
// }
)
export class HttpinterceptorService implements HttpInterceptor {
  
  constructor(
    private keycloakService: KeycloakService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.keycloakService.keycloak.token;
    if (token) {
      const authReq = request.clone({
        headers: new HttpHeaders({
          Authorization: `Bearer ${token}`
        })
      });
      return next.handle(authReq);
    }
    return next.handle(request);
  }
  
  // router = inject(Router);

  // intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

  //   let token = localStorage.getItem('token');
  //   if (token) {
  //     request = request.clone({
  //       setHeaders: { Authorization: 'Bearer ' + token }
  //     });
  //   }

  //   return next.handle(request).pipe(catchError(x => this.errorHandler(x)));
  // }

  // private errorHandler(err: HttpErrorResponse): Observable<any> {
  //   if (err.status === 401) {
  //     alert('401 - tratar');
  //     this.router.navigateByUrl(`/login`);
  //     return of(err.message);
  //   } else if (err.status === 403) {
  //     alert('403 - tratar');
  //     return of(err.message);
  //   }else  if (err.status === 500) {
  //     this.router.navigateByUrl(`/login`);
  //     return of(err.message);
  //   } 
  //   return throwError(() => err);
  // }

}

// export const httpInterceptorProviders = [
//   { provide: HTTP_INTERCEPTORS, useClass: HttpinterceptorService, multi: true }
// ];

