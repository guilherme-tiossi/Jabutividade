import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent {
  @Output() onSubmitRegisterEvent = new EventEmitter();

  login: string = "";
  password: string  = "";

  onSubmitRegister(): void {
    this.onSubmitRegisterEvent.emit({"login": this.login, "password": this.password});
  }
}
