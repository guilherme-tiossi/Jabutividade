import { Component } from '@angular/core';

@Component({
  selector: 'app-criacao-tarefas',
  templateUrl: './criacao-tarefas.component.html',
  styleUrls: ['./criacao-tarefas.component.css']
})

export class CriacaoTarefasComponent {
  nome: string = 'sorriso ronaldo';
  mensagem: string = '';

  onSubmit() {
    this.mensagem = `Olá, ${this.nome}!`; 
  }
}
