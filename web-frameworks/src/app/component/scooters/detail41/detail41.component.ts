import {Component, HostListener} from '@angular/core';
import {Detail4Component} from '../detail4/detail4.component';
import {Observable} from 'rxjs';
import {CanComponentDeactivate} from '../../../services/can-deactivate-guard.service';

@Component({
  selector: 'app-detail4',
  templateUrl: '../detail4/detail4.component.html',
  styleUrls: ['../detail4/detail4.component.css']
})
export class Detail41Component extends Detail4Component implements CanComponentDeactivate {
  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (this.change) {
      return this.confirmation();
    } else {
      return true;
    }
  }

}
