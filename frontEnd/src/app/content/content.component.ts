import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';
import { SharedService } from '../shared.service';
@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  componentToShow: string = 'carregando';
  errorMessages: string[] = [];

  constructor(private axiosService: AxiosService, private sharedService: SharedService) { }

  ngOnInit() {
    this.sharedService.componentToShow$.subscribe(componentToShow => {
      this.componentToShow = componentToShow;
    });
    
    const authToken = this.axiosService.getAuthToken();

    if (authToken != null) {
      this.validarToken(authToken)
        .then((validToken) => {
          if (validToken) {
            const idUser = this.axiosService.getIdUser();
            this.axiosService.setAuthToken(authToken);
            this.axiosService.setIdUser(idUser);
            this.componentToShow = 'home';
          } else {
            this.axiosService.setAuthToken(null);
            this.axiosService.setIdUser(null);
            this.componentToShow = 'login';
          }
        })
        .catch(error => {
          this.axiosService.setAuthToken(null);
          this.axiosService.setIdUser(null);
          this.componentToShow = 'login';
        });
    } else {
      this.axiosService.setAuthToken(null);
      this.axiosService.setIdUser(null);
      this.componentToShow = 'login';
    }
  }

  showComponent(componentToShow: string): void {
    this.componentToShow = componentToShow;
  }

  async validarToken(token: string): Promise<boolean> {
    let result = false;
    const response = await this.axiosService.request(
      "GET",
      "/validar-token/" + token,
      {}
    ).then(response => {
      result = true;
    })
    return result;
  }

  onLogin(input: any): void {
    this.axiosService.request(
      "POST",
      "/login",
      {
        login: input.login,
        password: input.password
      }
    ).then(response => {
      this.errorMessages = [];
      this.axiosService.setAuthToken(response.data.token);
      this.axiosService.setIdUser(response.data.id);
      this.componentToShow = "home";
    }).catch(error => {
      this.handleError(error, "login");
    })
  }

  onRegister(input: any): void {
    this.axiosService.request(
      "POST",
      "/register",
      {
        username: input.login,
        password: input.password
      }
    ).then(response => {
      this.errorMessages = [];
      this.axiosService.setAuthToken(response.data.token);
      this.axiosService.setIdUser(response.data.id);
      this.componentToShow = "home";
    }).catch(
      error => {
        this.handleError(error, "cadastro");
      })
  }

  logout(): void {
    this.axiosService.setAuthToken(null);
    this.axiosService.setIdUser(null);
    this.componentToShow = "login";
  }

  private handleError(error: any, caso: string): void {
    this.axiosService.setAuthToken(null);
    this.axiosService.setIdUser(null);
    this.componentToShow = "login";
    this.errorMessages = [];
    if (error.response && error.response.data && error.response.data.error) {
      this.errorMessages = error.response.data.error;
    } else {
      this.errorMessages.push("Ocorreu um erro durante o " + caso);
    }
  }

}
