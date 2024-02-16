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

      }).catch(
        
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
      }).catch(
      )
  }

  cancelSubmitCode() {
    this.showEmailForm = true;
  }
}
