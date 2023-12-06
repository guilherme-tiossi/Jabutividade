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
  formCriarDescricaoTarefa: string = '';
  listaExibicaoTarefas: any[] = [];
  edicaoIdTarefa: string = '';
  edicaoNovaDescricaotarefa: string = '';

  @ViewChild('FormCriarTarefa', { static: false }) FormCriarTarefa!: NgForm;

  constructor(private axiosService: AxiosService) { }

  ngOnInit() {
    this.carregarTarefas()
  }

  limparFormulario() {
    this.FormCriarTarefa.resetForm();
  }

  editarTarefaForm(idTarefa: string) {
    this.edicaoIdTarefa = this.edicaoIdTarefa == idTarefa ? '' : idTarefa;
    this.edicaoNovaDescricaotarefa = this.listaExibicaoTarefas.find(tarefa => tarefa.idTarefa === idTarefa).descricaoTarefa;
    this.carregarTarefas()
  }

  onSubmit() {
    const tarefaData = {
      descricaoTarefa: this.formCriarDescricaoTarefa,
      idUsuario: this.IDUSUARIO,
      completa: false
    }

    this.salvarTarefa(tarefaData);
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
          this.listaExibicaoTarefas = response.data;
        }).catch(
          (error) => {
            console.log("erro");
          }
        );
  }

  editarTarefa(idTarefa: string) {
    console.log(this.listaExibicaoTarefas.find(tarefa => tarefa.idTarefa === idTarefa).descricaoTarefa)
    console.log(this.edicaoNovaDescricaotarefa)

    const tarefaData = {
      idTarefa: idTarefa,
      descricaoTarefa: this.edicaoNovaDescricaotarefa,
      idUsuario: this.IDUSUARIO,
      completa: this.listaExibicaoTarefas.find(tarefa => tarefa.idTarefa === idTarefa).completa
    }

    this.axiosService.request(
      "PUT",
      "/api/editar",
      tarefaData
    ).then(
      (response) => {
        console.log("tarefa editada!");
        this.edicaoIdTarefa = '';
        this.carregarTarefas();
      }).catch(
        (error) => {
          console.log("erro");
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
        console.log("tarefa completada.");
        this.carregarTarefas();
      }).catch(
        (error) => {
          console.log("erro");
        }
      );
  }

  deletarTarefa(idTarefa: string) {
    this.axiosService.request(
      "DELETE",
      "/api/" + idTarefa,
      {}).then(
        (response) => {
          console.log("tarefa deletada.")
          this.carregarTarefas()
        }).catch(
          (error) => {
            console.log("erro")
          }
        );
  }
}
