import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessNotiPageComponent } from './success-noti-page.component';

describe('SuccessNotiPageComponent', () => {
  let component: SuccessNotiPageComponent;
  let fixture: ComponentFixture<SuccessNotiPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SuccessNotiPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessNotiPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
