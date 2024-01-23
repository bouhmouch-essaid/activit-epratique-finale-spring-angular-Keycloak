
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiHttp } from '../../../../core/http';
import { API_END_POINT } from '../../../../app.constants';


@Injectable({
  providedIn: 'root'
})
export class CommandeService {
  endpoint!: string;

  constructor(private apiHttp: ApiHttp) {
    this.setEndpoint(`http://localhost:8081/commades`);
  }

  setEndpoint(endpoint: string): void {
    this.endpoint = endpoint;
  }

  createCommande(commande: any): Observable<any> {
    return this.apiHttp.post(this.endpoint, commande);
  }

}