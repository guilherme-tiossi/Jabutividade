import { Component, OnInit, ViewChild } from '@angular/core';
import { TarefaService } from '../tarefa.service';
import { NgForm, FormsModule } from '@angular/forms';

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
  @ViewChild('criarTarefaForm', {static: false}) criarTarefaForm!: NgForm;

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
        this.limparFormulario();
      },
      (erros) => {
        this.mensagem = `Erro encontrado!...`;
      }
    )
  }

  completarTarefa(idTarefa : string, completo: boolean) {
    this.tarefaService.completarTarefa(idTarefa, completo).subscribe(
      (data) => {
        this.carregarTarefas();
      },
      (erros) => {
        this.mensagem = `Erro encontrado!...`;
      }
    )
    console.log(idTarefa)
  }

  limparFormulario() {
    this.criarTarefaForm.resetForm();
  }
}
