import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {Detail4Component} from '../detail4/detail4.component';
import {Params} from '@angular/router';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-detail4',
  templateUrl: '../detail4/detail4.component.html',
  styleUrls: ['../detail4/detail4.component.css']
})
export class Detail4qpComponent extends Detail4Component implements OnInit, OnDestroy {
  ngOnInit(): void {
    this.change = false;
    this.childParamsSubscription = this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.setScooter(+queryParams.id || -1);
    });
  }

  ngOnDestroy(): void {
    this.childParamsSubscription.unsubscribe();
  }

  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (this.change) {
      return this.confirmation();
    } else {
      return true;
    }
  }
}
