import { Component, OnInit } from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  public form: any = {};
  constructor(private sessionService: SessionSbService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.sessionService.signUp(this.form.eMail, this.form.passWord).subscribe(x =>
        window.alert('you will need to confirm you account using the mail we send to ' + this.form.eMail)
    );
  }
}
