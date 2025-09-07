import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmpresaListagemComponent } from './components/cadastro/empresa-listagem/empresa-listagem.component';
import { EmpresaCadastroComponent } from './components/cadastro/empresa-cadastro/empresa-cadastro.component';
import { HomeComponent } from './components/home/home.component';


const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' }, // Rota padrão
  { path: 'empresas', component: EmpresaListagemComponent }, // Rota para listar empresas
  // Rota para cadastrar (sem id) ou editar (com id)
  { path: 'empresas/cadastro', component: EmpresaCadastroComponent },
  { path: 'empresas/cadastro/:id', component: EmpresaCadastroComponent },

];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
