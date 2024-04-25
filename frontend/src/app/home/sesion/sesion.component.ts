import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SesionService } from './sesion.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sesion',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './sesion.component.html',
  styleUrl: './sesion.component.css',
})
export class SesionComponent {
  @Output() datosEnvio = new EventEmitter<any>();

  // datos recibidos en backEnd.
  idRol: number = 0;
  nombreUser: String = '';

  /// datos recopilados en front.
  userName: String = '';
  password: String = '';

  /// mensaje en restultado:

  messageResult: String;
  errorLoggin: String;
  ErrorResult: String;

  constructor(private service_loggin: SesionService, private router: Router) {
    this.errorLoggin = '';
    this.messageResult = '';
    this.ErrorResult = '';
  }

  /// enviar datos al componente padre:

  enviarDatos() {
    let datos = {
      idRol: this.idRol,
      nombreUser: this.nombreUser,
    };

    this.datosEnvio.emit(datos);
  }

  // metodo para usar el servicio
  newLoggin() {
    let userDate = {
      username: this.userName,
      password: this.password,
    };

    this.service_loggin.loggin(userDate).subscribe(
      (response) => {
        /// analizar el tipo de mensaje.
        if (response.success) {
          // si es verdadero:
          if (response.Estado) {
            this.messageResult = response.messege;
            this.idRol = response.ID_Rol;
            this.nombreUser = response.Nombre;

            if (response.ID_Rol == 1) {
              this.router.navigate(['/home2/admin']);
            } else if (response.ID_Rol == 2) {
              this.router.navigate(['/home2/operador']);
            } else {
              this.router.navigate(['/home2/recep']);
            }
          } else {
            /// usuario inactivo =
          }
        } else {
          this.errorLoggin = response.messege;
        }
      },

      (error) => {
        this.ErrorResult = error;
      }
    );
  }
}
