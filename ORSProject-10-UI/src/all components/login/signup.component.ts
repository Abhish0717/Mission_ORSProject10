import { Component } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent {
 endpoint = "http://localhost:8080/Auth/signUp";

  form: any = {
    error: false,
    message: '',
    data: { id: null },
    inputerror: {},
  };

  constructor(private httpService: HttpServiceService, private router: Router) {
  }

  signUp() {
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
    });
  }

  reset() {
    location.reload();
  }
}