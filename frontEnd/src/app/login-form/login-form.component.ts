import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  providers: [FormsModule]
})
export class LoginFormComponent {
  constructor(public sharedService: SharedService) {}
  @Output() onSubmitLoginEvent = new EventEmitter();

  login: string = "";
  password: string  = "";

  onSubmitLogin(): void {
    this.onSubmitLoginEvent.emit({"login": this.login, "password": this.password});
  }
  
  showComponentEvent(componentToShow: string): void {
    this.sharedService.showComponent(componentToShow);
  }

}
