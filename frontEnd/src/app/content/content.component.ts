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
  mensagensErro: string[] = [];

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
            this.deslogar();
          }
        })
        .catch(error => {
          this.deslogar();
        });
    } else {
      this.deslogar();
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

    this.componentToShow = "carregando";
    
    this.axiosService.request(
      "POST",
      "/login",
      {
        login: input.login,
        password: input.password
      }
    ).then(response => {
      this.mensagensErro = [];
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
        email: input.email,
        password: input.password
      }
    ).then(response => {
      this.mensagensErro = [];
      this.axiosService.setAuthToken(response.data.token);
      this.axiosService.setIdUser(response.data.id);
      this.componentToShow = "home";
    }).catch(
      error => {
        this.handleError(error, "cadastro");
      })
  }

  private handleError(error: any, caso: string): void {
    this.axiosService.setAuthToken(null);
    this.axiosService.setIdUser(null);
    this.componentToShow = "login";
    this.mensagensErro = [];
    if (error.response && error.response.data && error.response.data.error) {
      this.mensagensErro = error.response.data.error;
    } else {
      this.mensagensErro.push("Ocorreu um erro durante o " + caso);
    }
  }

  deslogar(): void {
    this.axiosService.setAuthToken(null);
    this.axiosService.setIdUser(null);
    this.componentToShow = 'login';
  }

}
