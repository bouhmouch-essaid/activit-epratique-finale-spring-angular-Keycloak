import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ApiHttp } from '../http';
import { API_END_POINT } from '../../app.constants';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _isAuthenticated = false;
  private endpoint!: string;

  constructor(private apiHttp: ApiHttp) {
    this.setEndpoint(`http://localhost:9086/login`);
  }

  get isAuthenticated(): boolean {
    return this._isAuthenticated;
  }

  set isAuthenticated(val: boolean) {
    this._isAuthenticated = val;
  }

  setEndpoint(endpoint: string): void {
    this.endpoint = endpoint;
  }

  login(username: string, password: string): Observable<any> {
    return this.apiHttp.post(this.endpoint, { username, password });
  }

  logout(): void {
    this._isAuthenticated = false;
  }

}
