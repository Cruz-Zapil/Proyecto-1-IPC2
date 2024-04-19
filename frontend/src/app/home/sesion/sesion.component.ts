import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SesionService } from './sesion.service';
import { response } from 'express';
import { error } from 'console';

@Component({
  selector: 'app-sesion',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sesion.component.html',
  styleUrl: './sesion.component.css',
})
export class SesionComponent {
  /// datos.
  userName: String = '';
  password: String = '';

  /// mensaje en restultado:

  messageResult: String;
  errorLoggin:String;
  ErrorResult: String;
  

  constructor(private service_loggin: SesionService){

    this.errorLoggin='';
    this.messageResult ='';
    this.ErrorResult ='';

  }

  // metodo para usar el servicio
  newLoggin(){
    let userDate={
      username: this.userName,
      password: this.password
    };

    this.service_loggin.loggin(userDate).subscribe(
      (response)=>{
        /// analizar el tipo de mensaje.
        if (response.success){

          // si es verdadero:
          this.messageResult = response.messege;

        }else {
          this.errorLoggin =  response.messege;
        }
      },

      (error)=> {
        this.ErrorResult = error;
      }
    );
  }

}
