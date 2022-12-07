import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ScootersSbService} from '../../../services/scooters-sb.service';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-overview5',
  templateUrl: '../overview5/overview5.component.html',
  styleUrls: ['../overview4/overview4.component.css']
})
export class Overview5Component implements OnInit {
  protected childParamsSubscription: Subscription = null;

  constructor(public scooterService: ScootersSbService,
              protected router: Router,
              protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.childParamsSubscription = this.activatedRoute.params.subscribe((params: Params) => {
      this.setSelectedScooterId(+params.id || -1);
    });
    this.scooterService.findAll();
  }

  public onCreateScooter(): void {
    this.scooterService.addRandomScooter();
    if (this.scooterService.selectedScooterId > -1) {
      this.router.navigate([this.scooterService.selectedScooterId], {relativeTo: this.activatedRoute});
    }
  }

  public onSelectScooter(sId: number): void {
    this.scooterService.selectedScooterId = sId;
    this.router.navigate([sId], {relativeTo: this.activatedRoute});
  }

  public setSelectedScooterId(selectedScooterId: number): void {
    this.scooterService.selectedScooterId = selectedScooterId;
  }
}
