import {Component, OnInit} from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';

@Component({
  selector: 'app-header-sb',
  templateUrl: './header-sb.component.html',
  styleUrls: ['./header-sb.component.css']
})
export class HeaderSbComponent implements OnInit {
  today: number = Date.now();
  firstname: string;

  constructor(public sessionService: SessionSbService) {
  }

  ngOnInit(): void {
    this.sessionService.username.subscribe(username => {
      this.firstname = username;
    });
  }

}
