import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';
import { ContentComponent } from '../content/content.component';
import { timer } from 'rxjs';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {

  constructor(private axiosService: AxiosService, private contentComponent: ContentComponent) { }


  logout(): void {
    this.contentComponent.componentToShow = "carregando";
  
    const tempo = Math.floor(Math.random() * (600 - 200 + 1)) + 400;

    timer(tempo).subscribe(() => {
      this.axiosService.setAuthToken(null);
      this.axiosService.setIdUser(null);
      this.contentComponent.componentToShow = "login";
    });
  }
  
}
