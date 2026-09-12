import { Component } from '@angular/core';
import { BaseCtl } from '../base.component';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html'
})
export class AttendanceComponent extends BaseCtl {

  constructor(public locator: ServiceLocatorService, route: ActivatedRoute) {
    super(locator.endpoints.ATTENDANCE, locator, route)
  }

}
