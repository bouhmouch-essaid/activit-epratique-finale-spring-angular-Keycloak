import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommandesComponent } from './commandes.component';
import { CommandesRoutingModule } from './commandes-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';



@NgModule({
  declarations: [
    CommandesComponent,
  ],
  imports: [
    CommonModule,
    CommandesRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatPaginatorModule,
    MatTableModule,
    MatCheckboxModule

  ],
  providers:[]
})
export class CommandesModule { }
