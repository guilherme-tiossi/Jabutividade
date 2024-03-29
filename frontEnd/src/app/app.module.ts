import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContentComponent } from './content/content.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { AuthContentComponent } from './auth-content/auth-content.component';
import { TarefasComponent } from './tarefas/tarefas.component';
import { CarregandoComponent } from './carregando/carregando.component';
import { MenuComponent } from './menu/menu.component';
import { EmailConfirmationComponent } from './email-confirmation/email-confirmation.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthContentComponent,
    ContentComponent,
    LoginFormComponent,
    RegisterFormComponent,
    TarefasComponent,
    CarregandoComponent,
    MenuComponent,
    EmailConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
