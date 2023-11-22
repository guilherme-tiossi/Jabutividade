import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CriacaoTarefasComponent } from './criacao-tarefas/criacao-tarefas.component';

const routes: Routes = [
  {path: 'criacao-tarefas', component: CriacaoTarefasComponent}
  // {path: 'criacao-tarefas', component: CriacaoTarefasComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
