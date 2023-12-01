import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  componentToShow: string = 'login';
  errorMessages: string[] = [];

  constructor(private axiosService: AxiosService) { }

  ngOnInit() {
    // Verifica se há um token de autenticação no localStorage
    const authToken = this.axiosService.getAuthToken();
    const idUser = this.axiosService.getIdUser();

    if (authToken) {
      // Se existir, define o token na instância do serviço
      this.axiosService.setAuthToken(authToken);
      this.axiosService.setIdUser(idUser);
      this.componentToShow = 'home';  // Define como 'home' se autenticado
    } else {
      // Se não existir, limpa o token na instância do serviço e define como 'login'
      this.axiosService.setAuthToken(null);
      this.axiosService.setIdUser(null);
      this.componentToShow = 'login';
    }
  }

  showComponent(componentToShow: string): void {
    this.componentToShow = componentToShow;
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
        this.handleError(error);
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
        this.axiosService.setAuthToken(null);
        this.axiosService.setIdUser(null);
        this.componentToShow = "login";
    })
  }

  logout(): void {
    this.axiosService.setAuthToken(null);
    this.axiosService.setIdUser(null);
    this.componentToShow = "login";
  }

  private handleError(error: any): void {
    this.axiosService.setAuthToken(null);
    this.axiosService.setIdUser(null);
    this.componentToShow = "login";
    this.errorMessages = [];
    if (error.response && error.response.data && error.response.data.error) {
      this.errorMessages = error.response.data.error;
    } else {
      this.errorMessages.push("Ocorreu um erro durante o login");
    }
  }

}
