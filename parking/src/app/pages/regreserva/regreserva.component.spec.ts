import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegreservaComponent } from './regreserva.component';

describe('RegreservaComponent', () => {
  let component: RegreservaComponent;
  let fixture: ComponentFixture<RegreservaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegreservaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegreservaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
