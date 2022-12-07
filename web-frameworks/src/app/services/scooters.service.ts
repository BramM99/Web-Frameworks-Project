import {Injectable} from '@angular/core';
import {Scooter} from '../models/scooter';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScootersService {
  private scooters: Scooter[];
  private newScooter: Scooter;

  constructor() {
    this.scooters = [];
    for (let i = 0; i < 8; i++) {
      this.addRandomScooter();
    }
  }

  public findAll(): Observable<Scooter[]> {
    return of(this.scooters);
  }

  public findById(oId: number): Scooter {
    const copy = this.scooters.find(x => x.id === oId);
    return Scooter.createCopy(copy);

  }

  public save(scooter: Scooter): void {
    const scooterInArray = this.scooters.find(x => x.id === scooter.id);
    const index = this.scooters.indexOf(scooterInArray);
    this.scooters.splice(index, 1, scooter);
  }

  public deleteById(oId: number): Scooter {
    const foundScooter: Scooter = this.findById(oId);
    if (foundScooter === null) {
      return null;
    } else {
      const scooterInArray = this.scooters.find(x => x.id === oId);
      const index = this.scooters.indexOf(scooterInArray);
      this.scooters.splice(index, 1);
      return foundScooter;
    }
  }

  addRandomScooter(): Scooter {
    this.newScooter = Scooter.createRandomScooter();
    this.scooters.push(this.newScooter);
    return this.newScooter;
  }


}
