import { Component} from '@angular/core';
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

 // datos recibidos en backEnd.
 idRol: number = 0;
 nombreUser: String = '';
 logginTrue: boolean = false;

  /// datos recopilados en front.
  userName: String = '';
  password: String = '';

  /// mensaje en restultado:

  messageResult: String;
  errorLoggin: String;
  errorResult: String;

  constructor(private service_loggin: SesionService, private router: Router) {
    this.errorLoggin = '';
    this.messageResult = '';
    this.errorResult = '';
  }

  entrar(): void {
    if (this.idRol == 1) {
      this.router.navigate(['/home2/admin']);
    } else if (this.idRol == 2) {
      /// rol operador
      this.router.navigate(['/home2/operador']);
    } else if(this.idRol == 3) {
      /// rel recepcionista
      this.router.navigate(['/home2/recep']);
    }
  }


  limpiarImputs(): void {
    this.idRol = 0;
    this.userName = '';
    this.nombreUser = '';
    this.password = '';
    this.errorLoggin = '';
    this.messageResult = '';
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
          if (response.estado) {
            this.messageResult = response.messege;
            this.idRol = response.id_rol;
            this.nombreUser = response.nombre;
            this.logginTrue = true;

          } else {
            /// usuario inactivo =
          }
        } else {
             /// error de datos:
             this.errorLoggin = response.messege;
             this.logginTrue = false;
        }
      },

      (error) => {
        this.errorResult = error;
      }
    );
  }
}
