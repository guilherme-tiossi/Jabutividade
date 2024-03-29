import { Component, OnInit, ViewChild } from '@angular/core'
import { NgForm } from '@angular/forms'
import { AxiosService } from '../axios.service'
import { ContentComponent } from '../content/content.component'

@Component({
  selector: 'app-tarefas',
  templateUrl: './tarefas.component.html',
  styleUrls: ['./tarefas.component.css']
})
export class TarefasComponent implements OnInit {
  IDUSUARIO: string | null = window.localStorage.getItem("id_user")
  descricaoTarefaCriacao: string = ''
  idTarefaEdicao: string = ''
  descricaoTarefaEdicao: string = ''
  listaTarefasExibicao: any[] = []
  mensagensErro: string[] = []

  @ViewChild('FormCriarTarefa', { static: false }) FormCriarTarefa!: NgForm

  constructor(private axiosService: AxiosService, private contentComponent: ContentComponent) { }

  ngOnInit() {
    this.carregarTarefas()
  }

  onSubmit() {
    const tarefaData = {
      descricaoTarefa: this.descricaoTarefaCriacao,
      idUsuario: this.IDUSUARIO,
      completa: false
    }

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (validToken) {
          this.salvarTarefa(tarefaData);
        } else {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });
  }

  editarTarefaForm(idTarefa: string) {
    this.idTarefaEdicao = this.idTarefaEdicao == idTarefa ? '' : idTarefa
    this.descricaoTarefaEdicao = this.listaTarefasExibicao.find(tarefa => tarefa.idTarefa === idTarefa).descricaoTarefa
    this.carregarTarefas()
  }

  limparFormulario() {
    this.FormCriarTarefa.resetForm()
  }

  salvarTarefa(tarefa: any) {
    this.axiosService.request(
      "POST",
      "/api",
      tarefa
    ).then(
      (response) => {
        if (response.data.success === true) {
          this.carregarTarefas()
          this.limparFormulario()
        } else {
          this.handleError(response.data.message, "a criação de tarefa.")
        }
      }).catch(
        (error) => {
          this.handleError(error, "a criação de tarefa.")
        }
      )
  }

  carregarTarefas() {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (!validToken) {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });

    this.axiosService.request(
      "GET",
      "/api/" + this.IDUSUARIO,
      {}).then(
        (response) => {
          if (response.data.success === true) {
            this.listaTarefasExibicao = response.data.tarefas
            this.listaTarefasExibicao.sort((a, b) => a.order - b.order)
            let i = 0
            this.listaTarefasExibicao.forEach((tarefa: any) => {
              i++
              tarefa["order"] = i
              tarefa["primeiroArray"] = this.listaTarefasExibicao[0].order
              tarefa["tamanhoArray"] = this.listaTarefasExibicao.length;
            })
          } else {
            this.handleError(response.data.message, "o carregamento de tarefas.")
          }
        }).catch(
          (error) => {
            this.handleError(error, "o carregamento de tarefas.")
          }
        )
  }

  priorizarOrderTarefa(order: number) {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (!validToken) {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });

    this.axiosService.request(
      "PUT",
      "/api/priorizarOrderTarefa",
      {
        order: order,
        listaTarefas: this.listaTarefasExibicao.map(tarefa => {
          const novaTarefa = { ...tarefa };
          delete novaTarefa.tamanhoArray;
          return novaTarefa;
        })
      }
    ).then(
      (response) => {
        if (response.data.success === true) {
          this.carregarTarefas()
        } else {
          this.handleError(response.data.message, "o aumento de tarefa.")
        }
      }).catch(
        (error) => {
          this.handleError(error, "o aumento de tarefa.")
        }
      )
  }

  postergarOrderTarefa(order: number) {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (!validToken) {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });

    this.axiosService.request(
      "PUT",
      "/api/postergarOrderTarefa",
      {
        order: order,
        listaTarefas: this.listaTarefasExibicao.map(tarefa => {
          const novaTarefa = { ...tarefa };
          delete novaTarefa.tamanhoArray;
          return novaTarefa;
        })
      }
    ).then(
      (response) => {
        if (response.data.success === true) {
          this.carregarTarefas()
        } else {
          this.handleError(response.data.message, "o rebaixamento de tarefa.")
        }
      }).catch(
        (error) => {
          this.handleError(error, "o rebaixamento de tarefa.")
        }
      )
  }

  editarTarefa(idTarefa: string) {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (!validToken) {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });

    const tarefaData = {
      idTarefa: idTarefa,
      descricaoTarefa: this.descricaoTarefaEdicao,
      idUsuario: this.IDUSUARIO,
      completa: this.listaTarefasExibicao.find(tarefa => tarefa.idTarefa === idTarefa).completa,
      order: this.listaTarefasExibicao.find(tarefa => tarefa.idTarefa === idTarefa).order
    }

    this.axiosService.request(
      "PUT",
      "/api/editar",
      tarefaData
    ).then(
      (response) => {
        if (response.data.success === true) {
          this.idTarefaEdicao = ''
          this.carregarTarefas()
        } else {
          this.handleError(response.data.message, "edição de tarefa.")
        }
      }).catch(
        (error) => {
          this.handleError(error, "edição de tarefa.")
        }
      )
  }

  completarTarefa(idTarefa: string, completo: boolean) {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (!validToken) {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });

    this.axiosService.request(
      "PUT",
      "/api/completarTarefa/" + idTarefa,
      !completo
    ).then(
      (response) => {
        if (response.data.success === true) {
          this.carregarTarefas()
        } else {
          this.handleError(response.data.message, "a finalização de tarefa.")
        }
      }).catch(
        (error) => {
          this.handleError(error, "a finalização de tarefa.")
        }
      )
  }

  deletarTarefa(idTarefa: string) {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.contentComponent.validarToken(authToken)
      .then((validToken) => {
        if (!validToken) {
          this.contentComponent.deslogar();
        }
      })
      .catch(error => {
        this.contentComponent.deslogar();
      });

    this.axiosService.request(
      "DELETE",
      "/api/" + idTarefa,
      {}).then(
        (response) => {
          if (response.data.success === true) {
            this.carregarTarefas()
          } else {
            this.handleError(response.data.message, "a deleção de tarefa.")
          }
        }).catch(
          (error) => {
            this.handleError(error, "a deleção de tarefa.")
          }
        )
  }

  private handleError(error: any, caso: string): void {
    this.mensagensErro = []
    if (error.response && error.response.data && error.response.data.error) {
      this.mensagensErro = error.response.data.error
    } else {
      this.mensagensErro.push("Ocorreu um erro durante " + caso)
    }
  }
}
