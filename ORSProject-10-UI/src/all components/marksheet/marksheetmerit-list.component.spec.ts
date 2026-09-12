import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarksheetmeritListComponent } from './marksheetmerit-list.component';

describe('MarksheetmeritListComponent', () => {
  let component: MarksheetmeritListComponent;
  let fixture: ComponentFixture<MarksheetmeritListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarksheetmeritListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarksheetmeritListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
