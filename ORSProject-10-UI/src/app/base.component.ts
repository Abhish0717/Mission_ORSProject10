import { Component, OnInit } from '@angular/core';
import { ServiceLocatorService } from './service-locator.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-base',
  templateUrl: './base.component.html'
})
export class BaseCtl implements OnInit {

  constructor(public endpoint: String, public serviceLocator: ServiceLocatorService, public route: ActivatedRoute) {
    this.initApi(endpoint);

    serviceLocator.getPathVariable(route, (params: any) => {
      this.form.data.id = params["id"];
    });
  }


  public form: any = {
    error: false, //error 
    inputerror: {}, // form input error messages
    message: null, //error or success message
    data: { id: null }, //form data
    searchParams: {}, //search form
    preload: [], // preload data
    list: [], // search list 
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

  ngOnInit(): void {
    this.preload();
    if (this.form.data.id && this.form.data.id > 0) {
      this.display();
    }
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

  display() {
    this.serviceLocator.httpservice.get(this.api.get + "/" + this.form.data.id, (res: any) => {
      if (res.success) {
        this.form.data = res.result.data;
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

  search() {
    this.serviceLocator.httpservice.post(this.api.search + "/" + this.form.pageNo, this.form.searchParams, (res: any) => {
      this.form.message = '';
      this.form.list = [];
      if (res.success) {
        this.form.error = false;
        this.form.list = res.result.data;
        this.form.nextListSize = res.result.nextListSize;
      } else {
        this.form.error = true;
        this.form.message = res.result.message;
      }
    });
  }

  deleteMany(id: any) {
    this.serviceLocator.httpservice.post(this.api.deleteMany + "/" + id, this.form.searchParams, (res: any) => {
      this.form.message = '';
      this.form.list = [];
      if (res.success) {
        this.form.error = false;
        this.form.message = res.result.message;
        this.form.list = res.result.data;
        this.form.nextListSize = res.result.nextListSize;
      } else {
        this.form.error = true;
        this.form.message = res.result.message;
      }
    });
  }

  forward(page: any) {
    this.serviceLocator.forward(page);
  }

  reset() {
    location.reload();
  }
}
