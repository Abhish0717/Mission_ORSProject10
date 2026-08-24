import { Component } from '@angular/core';
import { HttpServiceService } from '../http-service.service';
import { Router } from '@angular/router';
import { ServiceLocatorService } from '../service-locator.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  endpoint = "http://localhost:8080/Auth/";

  form: any = {
    data: {},
    message: '',
  }

  constructor(private httpService: HttpServiceService, private router: Router, private servicelocator: ServiceLocatorService) {

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

  logout() {

    this.httpService.get(this.endpoint + 'logout', (res: any) => {

      this.form.message = '';

      localStorage.clear();
      this.router.navigateByUrl('login?message=' + res.result.message)
    });
  }

  forward() {
    this.form.data.userId = localStorage.getItem("userId");
    this.servicelocator.forward("/myprofile/" + this.form.data.userId);
  }

}
