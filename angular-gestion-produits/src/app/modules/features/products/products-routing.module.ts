import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './products.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { ProductEditComponent } from './pages/product-edit/product-edit.component';
import { ProductCreateComponent } from './pages/product-create/product-create.component';

const routes: Routes = [
  {
    path: '',
    component: ProductsComponent,
    children: [
      { path: "", redirectTo: "list", pathMatch: 'full' },
      {path: "list",component: ProductListComponent },
      {path: "create",component: ProductCreateComponent },
      {path: "edit/:id",component: ProductEditComponent },
    ]
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
