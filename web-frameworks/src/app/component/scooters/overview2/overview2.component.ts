import {Component, OnInit} from '@angular/core';
import {Scooter} from '../../../models/scooter';

@Component({
  selector: 'app-overview2',
  templateUrl: './overview2.component.html',
  styleUrls: ['./overview2.component.css']
})
export class Overview2Component implements OnInit {
  scooters: Scooter[];
  highlightedScooter: Scooter;

  constructor() {
  }

  ngOnInit(): void {
    this.scooters = [];
    for (let i = 0; i < 8; i++) {
      this.onCreateScooter();
    }
    this.highlightedScooter = null;
  }

  onCreateScooter(): void {
    const randomScooter = Scooter.createRandomScooter();
    this.scooters.push(randomScooter);
    this.highlightedScooter = randomScooter;
  }

  onDeleteScooter(id): void {
    for (const scooter in this.scooters) {
      if (this.scooters[scooter].id === id) {
        const i = this.scooters.indexOf(this.scooters[scooter]);
        this.scooters.splice(i, 1);
      }
    }
    this.highlightedScooter = null;
  }

  public onSelectScooter(scooter: Scooter): void {
    this.highlightedScooter = scooter;
  }
}
