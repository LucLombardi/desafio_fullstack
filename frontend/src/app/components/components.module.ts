import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { RouterModule } from '@angular/router';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { FooterComponent } from './footer/footer.component';
import { EmpresaCadastroComponent } from './cadastro/empresa-cadastro/empresa-cadastro.component';
import {  FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EmpresaListagemComponent } from './cadastro/empresa-listagem/empresa-listagem.component';
import { FornecedorListagemComponent } from './cadastro/fornecedor-listagem/fornecedor-listagem.component';
import { FornecedorCadastroComponent } from './cadastro/fornecedor-cadastro/fornecedor-cadastro.component';
import { VinculacaoFornecedorEmpresaComponent } from './cadastro/vinculacao-fornecedor-empresa/vinculacao-fornecedor-empresa.component';





@NgModule({
  declarations: [
    HomeComponent,
    NavBarComponent,
    FooterComponent,
    EmpresaCadastroComponent,
    EmpresaListagemComponent,
    FornecedorListagemComponent,
    FornecedorCadastroComponent,
    VinculacaoFornecedorEmpresaComponent
    
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    
  ],
  exports:[
    HomeComponent,
    NavBarComponent,
    FooterComponent,
    EmpresaCadastroComponent,
    FornecedorListagemComponent,
    FornecedorCadastroComponent,
    VinculacaoFornecedorEmpresaComponent
  ]
})
export class ComponentsModule { }
