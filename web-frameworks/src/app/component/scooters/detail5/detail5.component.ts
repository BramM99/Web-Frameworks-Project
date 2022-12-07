import {Component, OnDestroy, OnInit} from '@angular/core';
import {Scooter} from '../../../models/scooter';
import {ScootersSbService} from '../../../services/scooters-sb.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-detail5',
  templateUrl: '../detail5/detail5.component.html',
  styleUrls: ['../detail4/detail4.component.css']
})
export class Detail5Component implements OnInit, OnDestroy {
  change: boolean;

  constructor(public scooterService: ScootersSbService,
              protected router: Router,
              protected activatedRoute: ActivatedRoute) {
  }

  protected childParamsSubscription: Subscription = null;

  ngOnInit(): void {
    this.setScooter(this.scooterService.selectedScooterId);
    this.change = false;
    this.childParamsSubscription = this.activatedRoute.params.subscribe((params: Params) => {
      this.setScooter(+params.id || -1);
    });
  }

  public onCancelScooter(oId: number): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.router.navigate(['.'], {relativeTo: this.activatedRoute.parent});
    }
  }

  public onResetScooter(oId: number): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.scooterService.scooterCopy = this.scooterService.findById(oId);
      this.onChange();
    }
  }

  public onRemoveScooter(oId: number): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.scooterService.deleteById(oId);
      this.scooterService.selectedScooterId = null;
      this.router.navigate(['.'], {relativeTo: this.activatedRoute.parent});
      this.onChange();
    }
  }

  public onSaveScooter(scooter: Scooter): void {
    this.scooterService.save(scooter);
      // .subscribe(response => {
      // this.originalScooter = JSON.parse(JSON.stringify(response));
      // this.onChange();
    this.router.navigate(['.'], {relativeTo: this.activatedRoute.parent});
    // });
  }

  public onClearScooter(): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.scooterService.scooterCopy.tag = null;
      this.scooterService.scooterCopy.status = null;
      this.scooterService.scooterCopy.gpsLocation = null;
      this.scooterService.scooterCopy.mileage = null;
      this.scooterService.scooterCopy.batteryCharge = null;
      this.onChange();
    }
  }

  public setScooter(oId: number): void {
    this.scooterService.scooterCopy = this.scooterService.findById(oId);
    if (this.scooterService){
      this.scooterService.originalScooter = JSON.parse(JSON.stringify(this.scooterService.scooterCopy));
    }
  }

  public onChange(): void {
    this.change = JSON.stringify(this.scooterService.originalScooter) !== JSON.stringify(this.scooterService.scooterCopy);
  }

  public confirmation(): boolean {
    return window.confirm('Are you sure to discard unsaved changes?');
  }

  ngOnDestroy(): void {
    this.childParamsSubscription.unsubscribe();
  }
}
