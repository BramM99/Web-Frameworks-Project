import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Overview4qpComponent } from './overview4qp.component';

describe('Overview4qpComponent', () => {
  let component: Overview4qpComponent;
  let fixture: ComponentFixture<Overview4qpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Overview4qpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Overview4qpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
