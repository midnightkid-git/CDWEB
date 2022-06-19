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

const PRIMENG = [
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
