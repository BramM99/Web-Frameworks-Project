import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {shareReplay} from 'rxjs/operators';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class SessionSbService {
  public readonly BACKEND_AUTH_URL = 'http://localhost:8085/authenticate';
  private readonly BS_TOKEN_NAME = 'ES_SB_AUTH_TOKEN';
  private readonly BS_TOKEN_USERNAME = 'ES_SB_AUTH_USERNAME';

  private usernameSubject: Subject<string> = new BehaviorSubject('visitor');
  public username: Observable<string> = this.usernameSubject.asObservable();
  private authenticated = false;

  constructor(private http: HttpClient) {
    this.getTokenFromSessionStorage();
  }

  signIn(email: string, password: string): void {
    console.log('login ' + email + '/' + password);
    const signInResponse =
      this.http.post<HttpResponse<User>>(this.BACKEND_AUTH_URL + '/login',
        {eMail: email, passWord: password},
        {observe: 'response'});
    signInResponse
      .subscribe(
        response => {
          this.authenticated = true;
          console.log(response);
          this.saveTokenIntoLocalStorage(
            response.headers.get('Authorization'),
            (response.body as unknown as User).name
          );
        },
        error => {
          console.log(error);
          this.signOff();
        }
      );
  }

  signUp(email: string, password: string): Observable<any> {
    return this.http.post<User>(this.BACKEND_AUTH_URL + '/register',
      {eMail: email, passWord: password});
  }

  signOff(): void {
    sessionStorage.clear();
    localStorage.clear();
    this.authenticated = false;
    this.usernameSubject.next('visitor');
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  saveTokenIntoLocalStorage(token: string, username: string): void {
    // Set given token and username in the local storage
    localStorage.setItem(this.BS_TOKEN_NAME, token);
    localStorage.setItem(this.BS_TOKEN_USERNAME, username);
    this.usernameSubject.next(username);
  }

  getTokenFromSessionStorage(): string {
    // Check if token exists in session storage
    const token = localStorage.getItem(this.BS_TOKEN_NAME);
    const username = localStorage.getItem(this.BS_TOKEN_USERNAME);
    this.usernameSubject.next(username);

    return token;
  }
}

