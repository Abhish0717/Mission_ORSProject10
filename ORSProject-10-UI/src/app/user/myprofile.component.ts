import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseCtl } from '../base.component';
import { ServiceLocatorService } from '../service-locator.service';

@Component({
  selector: 'app-myprofile',
  templateUrl: './myprofile.component.html'
})
export class MyprofileComponent extends BaseCtl {

  constructor(public locator: ServiceLocatorService, route: ActivatedRoute) {
    super(locator.endpoints.USER, locator, route);
  }

  myProfile() {
    this.serviceLocator.httpservice.post('http://localhost:8080/User/myProfile', this.form.data,  (res: any) => {
      this.form.message = '';
      this.form.inputerror = {};
      if (res.success) {
        this.form.message = res.result.message;
        this.form.data.id = res.result.data;
      } else {
        this.form.error = true;
        if (res.result.inputerror) {
          this.form.inputerror = res.result.inputerror;
        }
        this.form.message = res.result.message;
      }
    });
  }

}