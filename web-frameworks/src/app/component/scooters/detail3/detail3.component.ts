import {Component, Input, OnChanges, SimpleChanges, OnInit, Output, EventEmitter} from '@angular/core';
import {Scooter} from '../../../models/scooter';
import {ScootersService} from '../../../services/scooters.service';

@Component({
  selector: 'app-detail3',
  templateUrl: './detail3.component.html',
  styleUrls: ['./detail3.component.css']
})
export class Detail3Component implements OnChanges, OnInit {
  scooterCopy: Scooter;
  originalScooter: Scooter;
  change: boolean;
  @Input() editedScooterId: number;
  @Output() editedScooterIdChange = new EventEmitter<number>();

  constructor(private scooterService: ScootersService) {
  }

  ngOnInit(): void {
    this.change = false;
  }

  public onCancelScooter(oId: number): void {
    if ((this.change === true) && (this.confirmation() === false)) {
      return null;
    } else {
      this.scooterCopy = this.scooterService.findById(oId);
      this.editedScooterIdChange.emit();
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
      this.editedScooterIdChange.emit();
      this.onChange();
    }
  }

  public onSaveScooter(scooter: Scooter): void {
    this.scooterService.save(scooter);
    this.originalScooter = JSON.parse(JSON.stringify(this.scooterCopy));
    this.onChange();
    this.editedScooterIdChange.emit();
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

  ngOnChanges(changes: SimpleChanges): void {
    this.setScooter(this.editedScooterId);
  }
}
