import {Component, Input, OnInit} from '@angular/core';
import {Scooter} from '../../../models/scooter';
import {ScootersService} from '../../../services/scooters.service';

@Component({
  selector: 'app-overview3',
  templateUrl: './overview3.component.html',
  styleUrls: ['./overview3.component.css']
})

export class Overview3Component implements OnInit {
  scooters: Scooter[];
  selectedScooterId: number;
  newScooter: Scooter;

  constructor(private scooterService: ScootersService) {
  }

  ngOnInit(): void {
    this.getScooters();
    this.selectedScooterId = null;
  }

  public getScooters(): void {
    this.scooterService.findAll().subscribe(scooter => this.scooters = scooter);
  }

  public onCreateScooter(): void {
    this.newScooter = this.scooterService.addRandomScooter();
    this.selectedScooterId = this.newScooter.id;
  }

  public onSelectScooter(scooter: Scooter): void {
    this.selectedScooterId = scooter.id;
  }
}
