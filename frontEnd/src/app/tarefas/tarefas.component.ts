import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-tarefas',
  templateUrl: './tarefas.component.html',
  styleUrls: ['./tarefas.component.css']
})
export class TarefasComponent implements OnInit {
  inputDescricaoTarefa: string = '';
  idUsuario: string | null = window.localStorage.getItem("id_user");
  completa: boolean = false;
  mensagem: string = '';
  tarefas: any[] = [];
  IDTarefaParaEditar: string = '';

  @ViewChild('criarTarefaForm', { static: false }) criarTarefaForm!: NgForm;

  constructor(private axiosService: AxiosService) { }

  ngOnInit() {
    this.carregarTarefas()
  }

  onSubmit() {
    const tarefaData = {
      descricaoTarefa: this.inputDescricaoTarefa,
      idUsuario: this.idUsuario,
      completa: this.completa
    }

    this.axiosService.request(
      "POST",
      "/api",
      tarefaData
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
      "/api/" + this.idUsuario,
      {}).then(
        (response) => {
          this.tarefas = response.data;
        }).catch(
          (error) => {
            console.log("erro");
          }
        );
  }

  limparFormulario() {
    this.criarTarefaForm.resetForm();
  }

  editarTarefaForm(idTarefa: string) {
    this.IDTarefaParaEditar = this.IDTarefaParaEditar == idTarefa ? '' : idTarefa;
    this.carregarTarefas()
  }

  edicaoTarefa(idTarefa : string) {
    console.log("editar tarefa confirmada")
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
