import {Component, OnInit} from '@angular/core';

import {Scooter} from '../../../models/scooter';
import {ScootersService} from '../../../services/scooters.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-overview4',
  templateUrl: './overview4.component.html',
  styleUrls: ['./overview4.component.css']
})

export class Overview4Component implements OnInit {
  scooters: Scooter[];
  selectedScooterId: number;
  protected childParamsSubscription: Subscription = null;

  constructor(protected scooterService: ScootersService,
              protected router: Router,
              protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getScooters();
    this.selectedScooterId = null;
    this.childParamsSubscription = this.activatedRoute.params.subscribe((params: Params) => {
      this.setSelectedScooterId(+params.id || -1);
    });
  }

  public getScooters(): void {
    this.scooterService.findAll().subscribe(scooter => this.scooters = scooter);
  }

  public onCreateScooter(): void {
    const newScooter = this.scooterService.addRandomScooter();
    this.selectedScooterId = newScooter.id;
    this.router.navigate([this.selectedScooterId], {relativeTo: this.activatedRoute});
  }

  public onSelectScooter(sId: number): void {
    this.selectedScooterId = sId;
    this.router.navigate([sId], {relativeTo: this.activatedRoute});
  }

  public setSelectedScooterId(selectedScooterId: number): void {
    this.selectedScooterId = selectedScooterId;
  }
}
