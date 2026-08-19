import { Component, OnInit } from '@angular/core';
import { ServiceLocatorService } from './service-locator.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html'
})
export class BaseCtl implements OnInit {

  public form: any = {
    error: false,
    inputerror: {},
    message: null,
    data: { id: null },
    searchParams: {},
    preload: [],
    list: [],
    pageNo: 0,
    nextListSize: 0
  };

  public api: any = {
    endpoint: '',
    get: '',
    save: '',
    search: '',
    deleteMany: '',
    preload: ''
  }

  initApi(ep: any) {
    this.api.endpoint = ep;
    this.api.get = ep + "/get";
    this.api.save = ep + "/save";
    this.api.search = ep + "/search";
    this.api.deleteMany = ep + "/deleteMany";
    this.api.preload = ep + "/preload";
  }

  constructor(public endpoint: String, public serviceLocator: ServiceLocatorService, public route: ActivatedRoute) {
    this.initApi(endpoint);

    serviceLocator.getPathVariable(route, (params: any) => {
      this.form.data.id = params["id"];
    });
  }
  ngOnInit(): void {
    this.preload();
  }

  preload() {
    this.serviceLocator.httpservice.get(this.api.preload, (res: any) => {
      if (res.success) {
        this.form.preload = res.result;
      } else {
        this.form.error = true;
        this.form.message = res.result.message;
      }
    });
  }

  submit() {
    this.serviceLocator.httpservice.post(this.api.save, this.form.data, (res: any) => {
      this.form.message = '';
      this.form.inputerror = {};
      if (res.success) {
        this.form.error = false;
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
