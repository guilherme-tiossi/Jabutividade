import { Component } from '@angular/core';

@Component({
  selector: 'app-email-confirmation',
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent {

  email: string = "";
  codigo: string  = "";

  onSubmitEmail() {
    console.log("Teste");
  }
}
