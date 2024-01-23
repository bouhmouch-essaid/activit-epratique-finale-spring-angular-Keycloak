import { Component } from '@angular/core';
import { Product } from '../../../../../shared/models/products.model';
import { ProductService } from '../../services/product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrl: './product-edit.component.scss'
})
export class ProductEditComponent{
  editedProduct: Product = {
    id: 0,
    name: '',
    price: 0,
    quantity: 0,
    available: false
  };

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const productId = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProduct(productId).subscribe(product => {
      this.editedProduct = product;
    });
  }

  onSubmit(): void {
    this.productService.updateProduct(this.editedProduct.id, this.editedProduct).subscribe(() => {
      this.router.navigate(['/products']);
    });
  }
}
