import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Scooter} from '../../../models/scooter';

@Component({
  selector: 'app-detail2',
  templateUrl: './detail2.component.html',
  styleUrls: ['./detail2.component.css']
})
export class Detail2Component implements OnInit {
  @Input() scooter: Scooter;
  @Output() deleteScooterEvent = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit(): void {
  }

}
