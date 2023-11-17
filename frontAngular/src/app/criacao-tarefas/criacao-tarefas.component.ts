import { Component, OnInit } from '@angular/core';
import { TarefaService } from '../tarefa.service';

@Component({
  selector: 'app-criacao-tarefas',
  templateUrl: './criacao-tarefas.component.html',
  styleUrls: ['./criacao-tarefas.component.css'],
  providers: [TarefaService]
})

export class CriacaoTarefasComponent implements OnInit{
  tarefa: string = '';
  idUsuario: string = '';
  completa: boolean = false;
  mensagem: string = '';
  tarefas: any[] = [];

  constructor(private tarefaService: TarefaService) {}

  ngOnInit() {
    this.carregarTarefas()
  } 

  onSubmit() {

    const tarefaData = {
      descricaoTarefa: this.tarefa,
      idUsuario: this.idUsuario,
      completa: this.completa
    }

    this.tarefaService.salvarTarefa(tarefaData).subscribe(
      (response) => {
        // this.mensagem = `Tarefa enviada com sucesso!`;
        this.carregarTarefas();
      },
      (error) => {
        this.mensagem = `Erro encontrado!...`;
      }
    )
  }

  carregarTarefas() {
    this.tarefaService.listarTarefasPorUsuario("1").subscribe(
      (data) => {
        this.tarefas = data;
      },
      (erros) => {
        this.mensagem = `Erro encontrado!...`;
      }
    )
  }
}
