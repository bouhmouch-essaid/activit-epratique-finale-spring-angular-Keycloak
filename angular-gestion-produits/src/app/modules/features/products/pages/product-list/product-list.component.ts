import { AfterContentInit, Component ,ViewChild} from '@angular/core';
import { Product } from '../../../../../shared/models/products.model';
import { ProductService } from '../../services/product.service';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements AfterContentInit {
  products: Product[] = [];
  displayedColumns: string[] = ['id', 'name', 'price', 'quantity', 'available', 'actions'];
  dataSource!: MatTableDataSource<Product>;
  searchKeyword: string = '';

  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  constructor(private productService: ProductService,private router: Router) { }

  ngAfterContentInit(): void {
    this.getProducts();
  }

  
  ngOnInit(): void {
    
  }

  searchProducts(): void {
    console.log(this.searchKeyword);
    this.productService.searchProducts(this.searchKeyword).subscribe(products => {
      this.dataSource = new MatTableDataSource(products);
      this.dataSource.paginator = this.paginator;
    });
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.dataSource = new MatTableDataSource(products);
      this.dataSource.paginator = this.paginator;
    });
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(() => {
      this.getProducts();
    });
  }

  

  editProduct(id: number): void {
    this.router.navigate(['/products/edit', id]);
  }
}
