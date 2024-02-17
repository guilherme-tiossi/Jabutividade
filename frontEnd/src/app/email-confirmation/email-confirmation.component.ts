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
  codigo: string = "";
  showEmailForm: boolean = true;

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
        if (response.data.success === true) {
          this.showEmailForm = false;
        } else {
          console.log(response)
          this.handleError(null, response.data.message, "o envio do código.");
        }
      },
      (error) => {
        this.handleError(error, '', "o envio do código.");
      }
    );
  }

  onSubmitCode() {

    const authToken = this.axiosService.getAuthToken();

    if (authToken == null) { this.contentComponent.deslogar(); return; }

    this.axiosService.request(
      "POST",
      "/validar-codigo/" + this.codigo,
      this.email
    ).then(
      (response) => {
        if (response.data.success === true) {
          this.contentComponent.confirmadoEmail();
        } else {
          this.handleError(null, response.data.message, "a validação do código.");
        }
      },
      (error) => {
        this.handleError(error, '', "a validação do código.");
      }
    );
  }

  cancelSubmitCode() {
    this.showEmailForm = true;
  }

  private handleError(messageObjeto: any, messageRetorno: string, messageGenerica: string): void {
    this.contentComponent.mensagensErro = [];
    if (messageObjeto && messageObjeto.response && messageObjeto.response.data && messageObjeto.response.data.error) {
        this.contentComponent.mensagensErro = messageObjeto.response.data.error;
        return;
    } 
    if (messageRetorno) {
        this.contentComponent.mensagensErro.push(messageRetorno);
        return;
    }
    this.contentComponent.mensagensErro.push("Ocorreu um erro durante " + messageGenerica);
  }
}