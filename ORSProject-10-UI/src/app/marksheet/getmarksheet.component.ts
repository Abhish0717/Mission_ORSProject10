import { Component } from '@angular/core';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';
import { HttpServiceService } from '../http-service.service';
import { BaseCtl } from '../base.component';

@Component({
  selector: 'app-getmarksheet',
  templateUrl: './getmarksheet.component.html'
})
export class GetmarksheetComponent extends BaseCtl {


  override form: any = {
    error: false,
    message: null,
    preload: [],
    data: { id: null, rollNo: null },
    inputerror: {},
    searchParams: {},
    searchMessage: null,
    list: {},   // ✅ object hona chahiye
    pageNo: 0
  };

  constructor(
    public locator: ServiceLocatorService,
    route: ActivatedRoute,
    private httpservice: HttpServiceService
  ) {
    super(locator.endpoints.MARKSHEET, locator, route);
  }

  // VALIDATE
  validate() {
    return this.form.data.rollNo != null && this.form.data.rollNo !== '';
  }

  // SEARCH
  go() {

    console.log("Calling API with RollNo:", this.form.data.rollNo);

    this.httpservice.get(
      "http://localhost:8080/Marksheet/rollno/" + this.form.data.rollNo,
      (res: any) => {   // ✅ arrow function (VERY IMPORTANT)

        console.log("API Response:", res);

        if (res && res.success) {

          // 🔥 try both (backend depend karta hai)
          this.form.list = res.result?.data || res.data || {};

          if (!this.form.list || !this.form.list.rollNo) {
            this.form.error = true;
            this.form.message = "No record found";
          } else {
            this.form.error = false;
            this.form.message = res.result?.message || "Success";
          }

        } else {
          this.form.error = true;
          this.form.message = res?.result?.message || "Something went wrong";
        }

      }
    );
  }

  // RESET
  override reset() {
    this.form.data.rollNo = null;
    this.form.list = {};
    this.form.message = null;
    this.form.error = false;
  }

}