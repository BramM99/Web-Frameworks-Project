import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Detail4qpComponent } from './detail4qp.component';

describe('Detail4qpComponent', () => {
  let component: Detail4qpComponent;
  let fixture: ComponentFixture<Detail4qpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Detail4qpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Detail4qpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
