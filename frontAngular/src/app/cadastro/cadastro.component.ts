import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {
  idEmail: string = '';
  idSenha: string = '';
  mensagem: string = '';
  @ViewChild('cadastroUsuarioForm', {static: false}) cadastrarUsuarioForm!: NgForm;

  constructor(private usuarioService: UsuarioService) {}

  onSubmit() {
    const usuarioData = {
      email: this.idEmail,
      password: this.idSenha
    }

    this.usuarioService.cadastrarUsuario(usuarioData).subscribe(
      (response) => {
        this.mensagem = `Usuario cadastrado com sucesso!`;
        this.cadastrarUsuarioForm.resetForm();
      },
      (error) => {
        this.mensagem = `Erro encontrado!`;
      }
    )
  }
}
