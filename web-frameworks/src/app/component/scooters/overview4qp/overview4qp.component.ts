import {Component, OnInit} from '@angular/core';
import {Overview4Component} from '../overview4/overview4.component';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ScootersService} from '../../../services/scooters.service';

@Component({
  selector: 'app-overview4',
  templateUrl: './overview4qp.component.html',
  styleUrls: ['./overview4qp.component.css']
})
export class Overview4qpComponent extends Overview4Component implements OnInit {

  ngOnInit(): void {
    this.getScooters();
    this.selectedScooterId = null;
    this.childParamsSubscription = this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.setSelectedScooterId(+queryParams.id || -1);
    });
  }

  onSelectScooter(sId: number): void {
    this.selectedScooterId = sId;
    this.router.navigate(['edit'], {
      relativeTo: this.activatedRoute, queryParams: {id: sId}
    });
  }

  // Overriden om de navigate weg te halen
  public onCreateScooter(): void {
    const newScooter = this.scooterService.addRandomScooter();
    this.selectedScooterId = newScooter.id;
  }
}
