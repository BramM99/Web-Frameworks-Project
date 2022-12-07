import { Component, OnInit } from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';

@Component({
  selector: 'app-sign-on',
  templateUrl: './sign-on.component.html',
  styleUrls: ['./sign-on.component.css']
})
export class SignOnComponent implements OnInit {
  public form: any = {};
  constructor(private sessionService: SessionSbService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.sessionService.signIn(this.form.eMail, this.form.passWord);
  }

}
