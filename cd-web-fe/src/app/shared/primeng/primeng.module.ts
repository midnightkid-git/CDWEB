import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { PaginatorModule } from 'primeng/paginator';
import { ImageModule } from 'primeng/image';
import { CarouselModule } from 'primeng/carousel';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { StepsModule } from 'primeng/steps';
import { SliderModule } from 'primeng/slider';
import { SplitButtonModule } from 'primeng/splitbutton';
import { DialogModule } from 'primeng/dialog';
import { MenuModule } from 'primeng/menu';
import { CheckboxModule } from 'primeng/checkbox';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

const PRIMENG = [
  ConfirmDialogModule,
  ToastModule,
  TableModule,
  ToolbarModule,
  CheckboxModule,
  DialogModule,
  ButtonModule,
  MenubarModule,
  InputTextModule,
  DropdownModule,
  PaginatorModule,
  ImageModule,
  CarouselModule,
  RadioButtonModule,
  InputNumberModule,
  StepsModule,
  SliderModule,
  SplitButtonModule,
  MenuModule
]


@NgModule({
  exports: [...PRIMENG],
  imports: [
    ...PRIMENG,
    CommonModule
  ]
})
export class PrimengModule { }
