import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { RouterModule } from '@angular/router';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { FooterComponent } from './footer/footer.component';
import { EmpresaCadastroComponent } from './cadastro/empresa-cadastro/empresa-cadastro.component';
import {  FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EmpresaListagemComponent } from './cadastro/empresa-listagem/empresa-listagem.component';



@NgModule({
  declarations: [
    HomeComponent,
    NavBarComponent,
    FooterComponent,
    EmpresaCadastroComponent,
    EmpresaListagemComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule
    
  ],
  exports:[
    HomeComponent,
    NavBarComponent,
    FooterComponent,
    EmpresaCadastroComponent
  ]
})
export class ComponentsModule { }
