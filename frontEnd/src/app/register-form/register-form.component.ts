import { Component, EventEmitter, Output } from '@angular/core';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent {
  constructor(public sharedService: SharedService) {}
  @Output() onSubmitRegisterEvent = new EventEmitter();

  login: string = "";
  password: string  = "";
  email: string = "";

  onSubmitRegister(): void {
    this.onSubmitRegisterEvent.emit({"login": this.login, "email": this.email, "password": this.password});
  }

  showComponentEvent(componentToShow: string): void {
    this.sharedService.showComponent(componentToShow);
  }
}
