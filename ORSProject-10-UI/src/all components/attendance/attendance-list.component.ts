import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseListCtl } from '../base-list.component';
import { ServiceLocatorService } from '../service-locator.service';

@Component({
  selector: 'app-attendance-list',
  templateUrl: './attendance-list.component.html'
})
export class AttendanceListComponent extends BaseListCtl {

  constructor(locator: ServiceLocatorService, route: ActivatedRoute) {
    super(locator.endpoints.ATTENDANCE, locator, route);
  }}