import {Injectable} from '@angular/core';
import {Scooter} from '../models/scooter';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScootersSbService {
  scooters: Scooter[] = [];
  scooterCopy: Scooter;
  originalScooter: Scooter;
  selectedScooterId: number = null;

  constructor(private http: HttpClient) {
  }

  public findAll(): void {
    this.scooters = [];
    this.restGetScooters().subscribe(data => {
      for (const index of data) {
        this.scooters.push(index);
      }
    });
  }

  public findById(oId: number): Scooter {
    if (oId > -1) {
      const copy = this.scooters.find(x => x.id === oId);
      return Scooter.createCopy(copy);
    } else {
      return null;
    }
  }

  public save(scooter: Scooter): void {
    this.restPutScooter(scooter).subscribe(newScooter => {
      // need index to replace old scooter with new scooter
      const index = this.scooters.indexOf(scooter);
      this.scooters.splice(index, 1, scooter);
      this.selectedScooterId = scooter.id;
    });
  }

  public deleteById(oId: number): void {
    const foundScooter: Scooter = this.findById(oId);
    if (foundScooter === null) {
      return null;
    } else {
      this.restDeleteScooter(oId).subscribe(removedScooter => {
        const scooterInArray = this.scooters.find(x => x.id === oId);
        const index = this.scooters.indexOf(scooterInArray);
        this.scooters.splice(index, 1);
        this.scooterCopy = null;
      });
    }
  }

  public addRandomScooter(): void {
    const newScooter = Scooter.createRandomScooter();
    newScooter.setId(0);
    this.restPostScooter(newScooter).subscribe(data => {
      this.selectedScooterId = data.id;
      this.scooters.push(data);
    });
  }

  private restGetScooters(): Observable<Scooter[]> {
    return this.http
      .get<Scooter[]>('http://localhost:8085/scooters');
  }

  private restPostScooter(scooter: Scooter): Observable<Scooter> {
    return this.http
      .post<Scooter>(
        'http://localhost:8085/scooters', scooter);
  }

  private restPutScooter(scooter: Scooter): Observable<Scooter> {
    return this.http
      .put<Scooter>(
        'http://localhost:8085/scooters/' + scooter.id, scooter);
  }

  private restDeleteScooter(scooterId): Observable<Scooter> {
    return this.http
      .delete<Scooter>('http://localhost:8085/scooters/' + scooterId);
  }
}
