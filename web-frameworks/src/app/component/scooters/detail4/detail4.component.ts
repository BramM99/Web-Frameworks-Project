import {Component, OnInit, OnDestroy} from '@angular/core';
import {Scooter} from '../../../models/scooter';
import {ScootersService} from '../../../services/scooters.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-detail4',
  templateUrl: './detail4.component.html',
  styleUrls: ['./detail4.component.css']
})
export class Detail4Component implements OnInit, OnDestroy {
  scooterCopy: Scooter;
  originalScooter: Scooter;
  change: boolean;

  constructor(protected scooterService: ScootersService,
              protected router: Router,
              protected activatedRoute: ActivatedRoute) {
  }

  protected childParamsSubscription: Subscription = null;

  ngOnInit(): void {
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
      this.scooterCopy = this.scooterService.findById(oId);
      this.onChange();
    }
  }

  public onRemoveScooter(oId: number): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.scooterService.deleteById(oId);
      this.scooterCopy = null;
      this.router.navigate(['.'], {relativeTo: this.activatedRoute.parent});
      this.onChange();
    }
  }

  public onSaveScooter(scooter: Scooter): void {
    this.scooterService.save(scooter);
    this.originalScooter = JSON.parse(JSON.stringify(this.scooterCopy));
    this.onChange();
    this.router.navigate(['.'], {relativeTo: this.activatedRoute.parent});
  }

  public onClearScooter(): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.scooterCopy.tag = null;
      this.scooterCopy.status = null;
      this.scooterCopy.gpsLocation = null;
      this.scooterCopy.mileage = null;
      this.scooterCopy.batteryCharge = null;
      this.onChange();
    }
  }

  public setScooter(oId: number): void {
    this.scooterCopy = this.scooterService.findById(oId);
    this.originalScooter = JSON.parse(JSON.stringify(this.scooterCopy));
  }

  public onChange(): void {
    this.change = JSON.stringify(this.originalScooter) !== JSON.stringify(this.scooterCopy);
  }

  public confirmation(): boolean {
    return window.confirm('Are you sure to discard unsaved changes?');
  }

  ngOnDestroy(): void {
    this.childParamsSubscription.unsubscribe();
  }
}
