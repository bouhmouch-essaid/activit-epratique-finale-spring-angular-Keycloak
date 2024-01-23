
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiHttp } from '../../../../core/http';
import { Product } from '../../../../shared/models/products.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  endpoint!: string;

  constructor(private apiHttp: ApiHttp) {
    this.setEndpoint('http://localhost:3000/products');
  }

  setEndpoint(endpoint: string): void {
    this.endpoint = endpoint;
  }

  searchProducts(keyword: string): Observable<any[]> {
    return this.apiHttp.get(`${this.endpoint}?name=${keyword}`);
  }

  getProducts(): Observable<Product[]> {
    return this.apiHttp.get(`${this.endpoint}`);
  }

  getProduct(id: number): Observable<Product> {
    return this.apiHttp.get(`${this.endpoint}/${id}`);
  }

  addProduct(product: Product): Observable<Product> {
    return this.apiHttp.post(this.endpoint, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.apiHttp.put(`${this.endpoint}/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.apiHttp.delete(`${this.endpoint}/${id}`);
  }
}