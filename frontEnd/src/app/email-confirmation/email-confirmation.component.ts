import { Component } from '@angular/core';
import { ContentComponent } from '../content/content.component';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-email-confirmation',
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent {

  constructor(private axiosService: AxiosService, private contentComponent: ContentComponent) { }

  email: string = "";
  codigo: string  = "";
  showEmailForm: boolean = true;
  mensagensErro: string[] = []

  onSubmitEmail() {

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
      "POST",
      "/enviar-codigo/" + this.email,
      {}
    ).then(
      (response) => {
        console.log(response)
      }).catch(
        (error) => {
          this.handleError(error, '', "o envio do código.")
        }
      )
    this.showEmailForm = false;
  }

  onSubmitCode() {
    this.axiosService.request(
      "POST",
      "/validar-codigo/" + this.codigo,
      this.email
    ).then(
      (response) => {
        console.log(response)
        if (response.data.success === true) {
          this.contentComponent.confirmadoEmail()
        } else {
          console.log(response.data.message)
          this.handleError(null, response.data.message, "a validação do código.")
        }
      }).catch(
        (error) => {
          this.handleError(error, '', "a validação do código.")
        }
      )
  }

  cancelSubmitCode() {
    this.showEmailForm = true;
  }

  private handleError(messageObjeto: any, messageRetorno: any, messageGenerica: string): void {
    console.log(messageObjeto)
    console.log(messageRetorno)
    console.log(messageGenerica)
    this.mensagensErro = []
    if (messageObjeto.response && messageObjeto.response.data && messageObjeto.response.data.error) {
      console.log("messageObjeto")
      this.mensagensErro = messageObjeto.response.data.error
      return
    } 
    console.log(messageRetorno)
    console.log(messageRetorno.length)
    console.log(messageRetorno != null)
    if (messageRetorno) {
      console.log("messageRetorno")
      this.mensagensErro.push(messageRetorno)
      return
    }
    console.log("messageGenerica")
    this.mensagensErro.push("Ocorreu um erro durante " + messageGenerica)
  }
}
