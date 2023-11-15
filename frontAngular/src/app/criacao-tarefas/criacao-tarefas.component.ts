import { Component } from '@angular/core';
import { TarefaService } from '../tarefa.service';

@Component({
  selector: 'app-criacao-tarefas',
  templateUrl: './criacao-tarefas.component.html',
  styleUrls: ['./criacao-tarefas.component.css'],
  providers: [TarefaService]
})

export class CriacaoTarefasComponent {
  tarefa: string = '';
  idUsuario: string = '';
  completa: boolean = false;
  mensagem: string = '';

  constructor(private tarefaService: TarefaService) {}

  onSubmit() {

    const tarefaData = {
      descricaoTarefa: this.tarefa,
      idUsuario: this.idUsuario,
      completa: this.completa
    }

    this.tarefaService.salvarTarefa(tarefaData).subscribe(
      (response) => {
        this.mensagem = `Tarefa enviada com sucesso!`;
      },
      (error) => {
        this.mensagem = `Erro encontrado!...`;
      }
    )
  }
}
