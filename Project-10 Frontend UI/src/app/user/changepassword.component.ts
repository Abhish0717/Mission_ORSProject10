import { Component } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html'
})
export class ChangepasswordComponent {

  form: any = {
    error: false,
    message: '',
    data: { 
      id: null,
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    },
    inputerror: {},
  };

  constructor(public httpService: HttpServiceService, private route: Router) {
  }

  changePassword() {
    this.form.data.login = localStorage.getItem("login")
    this.httpService.post("http://localhost:8080/User/changePassword", this.form.data,  (res: any) => {
      this.form.message = '';
      this.form.inputerror = {};
      if (res.success) {
        this.form.error = false;
        this.form.message = res.result.message;
        this.form.data = { 
          id: null,
          oldPassword: '',
          newPassword: '',
          confirmPassword: ''
        };
      } else {
        this.form.error = true;
        if (res.result.inputerror) {
          this.form.inputerror = res.result.inputerror;
        }
        this.form.message = res.result.message;
      }
    });
  }

  forward(page: any) {
    this.route.navigateByUrl(page);
  }

  closeMessage() {
    this.form.message = '';
  }

}