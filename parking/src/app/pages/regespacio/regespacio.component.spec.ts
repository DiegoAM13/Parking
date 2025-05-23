import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegespacioComponent } from './regespacio.component';

describe('RegespacioComponent', () => {
  let component: RegespacioComponent;
  let fixture: ComponentFixture<RegespacioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegespacioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegespacioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
