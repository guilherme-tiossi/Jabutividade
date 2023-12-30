import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-tarefas',
  templateUrl: './tarefas.component.html',
  styleUrls: ['./tarefas.component.css']
})
export class TarefasComponent implements OnInit {
  IDUSUARIO: string | null = window.localStorage.getItem("id_user");
  descricaoTarefaCriacao: string = '';
  idTarefaEdicao: string = '';
  descricaoTarefaEdicao: string = '';
  listaTarefasExibicao: any[] = [];
  mensagensErro: string[] = [];

  @ViewChild('FormCriarTarefa', { static: false }) FormCriarTarefa!: NgForm;

  constructor(private axiosService: AxiosService) { }

  ngOnInit() {
    this.carregarTarefas()
  }

  onSubmit() {
    const tarefaData = {
      descricaoTarefa: this.descricaoTarefaCriacao,
      idUsuario: this.IDUSUARIO,
      completa: false
    }

    this.salvarTarefa(tarefaData);
  }

  editarTarefaForm(idTarefa: string) {
    this.idTarefaEdicao = this.idTarefaEdicao == idTarefa ? '' : idTarefa;
    this.descricaoTarefaEdicao = this.listaTarefasExibicao.find(tarefa => tarefa.idTarefa === idTarefa).descricaoTarefa;
    this.carregarTarefas()
  }

  limparFormulario() {
    this.FormCriarTarefa.resetForm();
  }

  salvarTarefa(tarefa: any) {
    this.axiosService.request(
      "POST",
      "/api",
      tarefa
    ).then(
      (response) => {
        this.carregarTarefas();
        this.limparFormulario();
      }).catch(
        (error) => {
          console.log("erro");
        }
      );
  }

  carregarTarefas() {
    this.axiosService.request(
      "GET",
      "/api/" + this.IDUSUARIO,
      {}).then(
        (response) => {
          this.listaTarefasExibicao = response.data;
        }).catch(
          (error) => {
            console.log("erro");
          }
        );
  }

  editarTarefa(idTarefa: string) {
    const tarefaData = {
      idTarefa: idTarefa,
      descricaoTarefa: this.descricaoTarefaEdicao,
      idUsuario: this.IDUSUARIO,
      completa: this.listaTarefasExibicao.find(tarefa => tarefa.idTarefa === idTarefa).completa
    }

    this.axiosService.request(
      "PUT",
      "/api/editar",
      tarefaData
    ).then(
      (response) => {
        this.idTarefaEdicao = '';
        this.carregarTarefas();
      }).catch(
        (error) => {
          this.handleError(error, "criação de tarefa!")
        }
      )
  }

  completarTarefa(idTarefa: string, completo: boolean) {
    this.axiosService.request(
      "PUT",
      "/api/completarTarefa/" + idTarefa,
      !completo
    ).then(
      (response) => {
        this.carregarTarefas();
      }).catch(
        (error) => {
          this.handleError(error, "a finalização de tarefa!")
        }
      );
  }

  deletarTarefa(idTarefa: string) {
    this.axiosService.request(
      "DELETE",
      "/api/" + idTarefa,
      {}).then(
        (response) => {
          this.carregarTarefas()
        }).catch(
          (error) => {
            this.handleError(error, "a deleção de tarefa!")
          }
        );
  }

  private handleError(error: any, caso: string): void {
    this.mensagensErro = [];
    if (error.response && error.response.data && error.response.data.error) {
      this.mensagensErro = error.response.data.error;
    } else {
      this.mensagensErro.push("Ocorreu um erro durante " + caso);
    }
  }
}
