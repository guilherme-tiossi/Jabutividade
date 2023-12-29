import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private componentToShowSource = new BehaviorSubject<string>('carregando');
  componentToShow$ = this.componentToShowSource.asObservable();

  showComponent(componentToShow: string) {
    this.componentToShowSource.next(componentToShow);
  }
}
