import { TestBed } from '@angular/core/testing';

import { ScootersSbService } from './scooters-sb.service';

describe('ScootersSbService', () => {
  let service: ScootersSbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScootersSbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
