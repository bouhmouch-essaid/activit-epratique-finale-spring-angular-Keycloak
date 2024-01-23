import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ApiHttp {



  constructor(private http: HttpClient) {}
 

  get(path: string, params :any = null): Observable<any> {
    return params? this.http.get(`${path}?`, { ...params, observe: 'response' }) : this.http.get(`${path}`);
  }

  put(path: string, body: any): Observable<any> {
    return this.http.put(`${path}`, body);
  }

  post(path: string, body: any): Observable<any> {
    return this.http.post(`${path}`, body);
  }

  delete(path: string): Observable<any> {
    return this.http.delete(`${path}`);
  }

}
