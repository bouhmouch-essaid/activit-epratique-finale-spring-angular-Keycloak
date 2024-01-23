// commandes.component.ts

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { CommandeService } from './services/commande.service';
import { ProductService } from '../products/services/product.service';
import { SelectionModel } from '@angular/cdk/collections'; // Import SelectionModel

@Component({
  selector: 'app-commandes',
  templateUrl: './commandes.component.html',
  styleUrls: ['./commandes.component.scss']
})
export class CommandesComponent implements OnInit {
  // Selected products
  selectedProducts: any[] = [];

  // List of products
  products: any[] = [];

  // Search keyword
  searchKeyword: string = '';

  // Table data source
  dataSource = new MatTableDataSource<any>();

  // Paginator
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  // Checkbox selection
  selection = new SelectionModel<any>(true, []); // Use SelectionModel

  constructor(private productService: ProductService, private commandeService: CommandeService) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe((data: any) => {
      this.products = data;
      this.dataSource.data = this.products;
      this.dataSource.paginator = this.paginator;
    });
  }

  // Function to apply filter/search
  applyFilter(): void {
    this.dataSource.filter = this.searchKeyword.trim().toLowerCase();
  }

  // Function to handle pagination change
  onPageChange(event: any): void {
    // Handle pagination change if needed
  }

  // Master toggle for selection
  masterToggle(): void {
    if (this.isAllSelected()) {
      this.selection.clear();
    } else {
      this.dataSource.filteredData.forEach(row => this.selection.select(row));
    }
  }

  // Check if all products are selected
  isAllSelected(): boolean {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.filteredData.length;
    return numSelected === numRows;
  }

  // Submit Commande
  submitCommande(): void {
    const commande = {
      products: this.selection.selected.map(product => product.id)
    };

    this.commandeService.createCommande(commande).subscribe((response: any) => {
      console.log('Commande submitted successfully:', response);
    });
  }
}
