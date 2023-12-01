import { Component, OnInit, ViewChild } from '@angular/core';
import { AxiosService } from '../axios.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-auth-content',
  templateUrl: './auth-content.component.html',
  styleUrls: ['./auth-content.component.css']
})
export class AuthContentComponent implements OnInit {
  tarefa: string = '';
  idUsuario: string | null = window.localStorage.getItem("id_user");
  completa: boolean = false;
  mensagem: string = '';
  tarefas: any[] = [];
  @ViewChild('criarTarefaForm', { static: false }) criarTarefaForm!: NgForm;

  constructor(private axiosService: AxiosService) { }

  ngOnInit() {
    this.carregarTarefas()
  }

  onSubmit() {
    console.log("criacaodeUsuario | antes de criar tarefaData: " + this.idUsuario);
    const tarefaData = {
      descricaoTarefa: this.tarefa,
      idUsuario: this.idUsuario,
      completa: this.completa
    }

    console.log("criacaodeUsuario | depois de criar tarefaData/antes de enviar request: " + this.idUsuario);
    // Salvar Tarefa
    this.axiosService.request(
      "POST",
      "/api",
      tarefaData
    ).then(
      (response) => {
        console.log("tarefa registrada.");
        console.log("criacaodeUsuario | após criar a tarefa/antes de carregarTarefas: " + this.idUsuario);
        this.carregarTarefas();
        console.log("criacaodeUsuario | após carregarTarefas/antes de limparFormulario: " + this.idUsuario);
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
    console.log("antes de limpar formulário: " + this.idUsuario);
    this.criarTarefaForm.resetForm();
    console.log("depois de limpar formulário:" + this.idUsuario);
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

  //   this.axiosService.request(
  //     "GET",
  //     "/api/messages",
  //     {}).then(
  //       (response) => {
  //           this.data = response.data;
  //       }).catch(
  //       (error) => {
  //           if (error.response.status === 401) {
  //               this.axiosService.setAuthToken(null);
  //           } else {
  //               this.data = error.response.code;
  //           }

  //       }
  //   );

  // carregarTarefas() {
  //   this.axiosService.listarTarefasPorUsuario("1").subscribe(
  //     (data) => {
  //       this.tarefas = data;
  //       this.limparFormulario();
  //     },
  //     (erros) => {
  //       this.mensagem = `Erro encontrado!...`;
  //     }
  //   )
  // }

  // completarTarefa(idTarefa : string, completo: boolean) {
  //   this.axiosService.completarTarefa(idTarefa, completo).subscribe(
  //     (data) => {
  //       this.carregarTarefas();
  //     },
  //     (erros) => {
  //       this.mensagem = `Erro encontrado!...`;
  //     }
  //   )
  // }

  // deletarTarefa(idTarefa : string) {
  //   this.axiosService.deletarTarefa(idTarefa).subscribe(
  //     (data) => {
  //       this.carregarTarefas();
  //     },
  //     (erros) => {
  //       this.mensagem = `Erro encontrado!...`;
  //     }
  //   )
  // }

  // limparFormulario() {
  //   this.criarTarefaForm.resetForm();
  // }
}