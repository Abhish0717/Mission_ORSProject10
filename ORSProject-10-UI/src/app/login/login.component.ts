import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpServiceService } from '../http-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  endpoint = "http://localhost:8080/Auth/login";

  form: any = {
    error: "false",
    message: '',
    data: { id: null },
    inputerror: {},
    errorMessage: ''
  };

  constructor(private httpService: HttpServiceService, private router: Router, private activatedRoute: ActivatedRoute) {

    this.activatedRoute.queryParams.subscribe(params => {
      if (params['message']) {
        this.form.message = params['message'];
      }

    });
  }

  signIn() {
    this.httpService.post(this.endpoint, this.form.data, (res: any) => {

      this.form.message = '';

      this.form.inputerror = {};

      if (res.result.message) {
        this.form.message = res.result.message;
      }

      this.form.error = !res.success;
      if (this.form.error && res.result.inputerror) {
        this.form.inputerror = res.result.inputerror;
      }

      if (res.success) {
        localStorage.setItem("login", res.result.login);
        localStorage.setItem("role", res.result.role);
        localStorage.setItem("fname", res.result.fname);
        localStorage.setItem("lname", res.result.lname);
        localStorage.setItem("userId", res.result.data.id);
        localStorage.setItem('token', 'Bearer ' + res.result.token)

        this.router.navigateByUrl('dashboard');
      }
    });
  }

  signUp() {
    this.router.navigateByUrl('signup');
  }
}