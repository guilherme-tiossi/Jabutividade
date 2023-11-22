import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl = 'http://localhost:8080/usuarios';
  
  constructor(private http: HttpClient) {}

  cadastrarUsuario(usuario: any): Observable<any> {

    const httpOptions = {
      headers : new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.http.post<any>(this.apiUrl, usuario, httpOptions);

  }
}
