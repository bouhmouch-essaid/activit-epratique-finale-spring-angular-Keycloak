import { Component } from '@angular/core';
import { Product } from '../../../../../shared/models/products.model';
import { ProductService } from '../../services/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.scss'
})
export class ProductCreateComponent {
  newProduct: Product = {
    id: 0,
    name: '',
    price: 0,
    quantity: 0,
    available: false
  };

  constructor(private productService: ProductService, private router: Router) { }

  onSubmit(): void {
    this.productService.addProduct(this.newProduct).subscribe(() => {
      this.router.navigate(['/products']);
    });
  }
}
