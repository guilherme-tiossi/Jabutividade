import { Injectable } from '@angular/core';
import axios, { AxiosRequestConfig, Method } from 'axios';

@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  constructor() {
    axios.defaults.baseURL = "http://localhost:8080"
    axios.defaults.headers.post["Content-type"] = "application/json"
  }

  getAuthToken(): string | null {
    return window.localStorage.getItem("auth_token");
  }

  getIdUser(): string | null {
    return window.localStorage.getItem("id_user");
  }

  getConfirmedEmail(): string | null {
    return window.localStorage.getItem("confirmed_email");
  }

  setConfirmedEmail(value: string | null): void {
    if (value !== null) {
      window.localStorage.setItem("confirmed_email", value);
    } else {
      window.localStorage.removeItem("confirmed_email");
    }
  }

  setIdUser(id: string | null): void {
    if (id !== null) {
      window.localStorage.setItem("id_user", id);
    } else {
      window.localStorage.removeItem("id_user");
    }
  }

  setAuthToken(token: string | null): void {
    if (token !== null) {
      window.localStorage.setItem("auth_token", token);
    } else {
      window.localStorage.removeItem("auth_token");
    }
  }

  request(method: Method, url: string, data: any, requestConfig?: AxiosRequestConfig): Promise<any> {
    let headers = {};

    if (this.getAuthToken() !== null) {
      headers = { "Authorization": "Bearer " + this.getAuthToken() };
    }

    const config: AxiosRequestConfig = {
      method: method,
      url: url,
      data: data,
      headers: { ...headers, "Content-Type": "application/json" }, // Adiciona o Content-Type
    };

    return axios(config);
  }
}
