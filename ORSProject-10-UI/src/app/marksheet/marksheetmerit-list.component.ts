import { Component } from '@angular/core';
import { BaseCtl } from '../base.component';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';
import { HttpServiceService } from '../http-service.service';

@Component({
  selector: 'app-marksheetmerit-list',
  templateUrl: './marksheetmerit-list.component.html'
})
export class MarksheetmeritlistComponent extends BaseCtl {

  constructor(public locator: ServiceLocatorService, route: ActivatedRoute, private httpservice: HttpServiceService) {
    super(locator.endpoints.MARKSHEET, locator, route);

  }
  override ngOnInit(): void {

    this.getMeritList();

  }

  getMeritList() {

    this.httpservice.get("http://localhost:8080/Marksheet/meritlist", (res: any) => {

      if (res.success) {
        this.form.list = res.result.list;
        if (this.form.list.length == 0) {
          this.form.message = "No record found";
          this.form.error = true;
        }
        console.log("List Size", this.form.list.length);
      } else {
        this.form.error = false;
        this.form.message = res.result.message;
      }
      console.log('FORM', this.form);
    });
  }

  printReport() {
    const token = localStorage.getItem('token');

    if (!token) {
      alert("User not logged in");
      return;
    }

    this.httpservice.getReport(
      "http://localhost:8080/jasper/report",
      token
    );
  }

}
