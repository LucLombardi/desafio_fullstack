import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmpresaListagemComponent } from './components/cadastro/empresa-listagem/empresa-listagem.component';
import { EmpresaCadastroComponent } from './components/cadastro/empresa-cadastro/empresa-cadastro.component';
import { HomeComponent } from './components/home/home.component';
import { FornecedorListagemComponent } from './components/cadastro/fornecedor-listagem/fornecedor-listagem.component';
import { FornecedorCadastroComponent } from './components/cadastro/fornecedor-cadastro/fornecedor-cadastro.component';


const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' }, 
  { path: 'empresas', component: EmpresaListagemComponent }, // Rota para listar empresas

  { path: 'empresas/cadastro', component: EmpresaCadastroComponent },
  { path: 'empresas/cadastro/:id', component: EmpresaCadastroComponent },
  { path: 'fornecedores', component: FornecedorListagemComponent },
  { path: 'fornecedores/cadastro', component: FornecedorCadastroComponent },
  { path: 'fornecedores/cadastro/:id', component: FornecedorCadastroComponent },

];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
