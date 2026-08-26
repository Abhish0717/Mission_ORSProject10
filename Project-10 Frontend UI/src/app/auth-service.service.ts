import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, EMPTY, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService implements HttpInterceptor {

  token: any;

  constructor(private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (localStorage.getItem('fname') && localStorage.getItem('token')) {
      this.token = localStorage.getItem('token');
      console.log("Actual Tocken = " + this.token);
      req = req.clone({
        setHeaders: {
          withCredentials: 'true',
          name: 'Abhishish',
          Authorization: this.token,
        },
      });
    }
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {

        if (error.status === 401) {
          localStorage.clear();
          this.router.navigate(['/login'], {
            queryParams: { errorMessage: error.error },
          });
          return EMPTY;

        }

        if (error.status === 403) {
          localStorage.clear();
          this.router.navigate(['/login'], {
            queryParams: {
              errorMessage: 'Your Token is expired... plz login again..!!',
            },
          });
          return EMPTY;
        }
        return throwError(error);
      }),
    );
  }
}