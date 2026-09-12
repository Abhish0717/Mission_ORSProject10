import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {

  form: any = {
    data: {},
    message: '',
  }

  isLogin() {

    let check = localStorage.getItem('fname');

    if (check != "null" && check != null) {

      this.form.data.fname = localStorage.getItem("fname");
      this.form.data.role = localStorage.getItem("role");

      return true;

    } else {

      return false;

    }
  }

}