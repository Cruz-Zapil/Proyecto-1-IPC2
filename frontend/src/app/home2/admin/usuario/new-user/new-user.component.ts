import { Component } from '@angular/core';
import { NewUserService } from './new-user.service';
import { FormsModule } from '@angular/forms';
import { response } from 'express';
import { error } from 'console';

@Component({
  selector: 'app-new-user',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css',
  providers: [NewUserService],
})
export class NewUserComponent {
  /// atributos:
  nombre: String = '';
  apellido: String = '';
  username: String = '';
  password_has: String = '';
  id_rol: String = '';
  genero: String = '';
  telefono: String = '';
  estado: String = '';
  edad: String = '';

  /// resultados:

  result: String;
  resultError: String;

  /// constructor
  constructor(private newService: NewUserService) {
    this.result = '';
    this.resultError = '';
  }

  /// asignar genero

  addGenero(tipoGenero: String) {
    this.genero = tipoGenero;
  }

  /// asignar edad
  obtenerEdad(event: any) {
    // Accede al valor del input usando event.target['value']
    this.edad = event.target['value'].toString();
  }

  /// metodo para asiganar estado:
  addEstado(tipoEstado: String): void {
    this.estado = tipoEstado;
  }

  // metodo para limpiar los imputs
  cleanImputs(): void {
    this.nombre = '';
    this.apellido = '';
    this.username = '';
    this.password_has = '';
    this.id_rol = '';
    this.telefono = '';
    this.edad = '';
  }

  /// metodo para utilizar servicio:
  newUser() {
    let userData = {
      nombre: this.nombre,
      apellido: this.apellido,
      username: this.username,
      password_has: this.password_has,
      estado: this.estado,
      id_rol: this.id_rol,
      genero: this.genero,
      telefono: this.telefono,
      edad: this.edad
    };

    this.cleanImputs();

    this.newService.enviar_datos_user(userData).subscribe(
      (response: {success: boolean; message: String}) => {
        if (response.success) {
          // Si es éxito, mostrar el mensaje de éxito
          this.result = response.message;
        } else {
          // Si no es éxito, mostrar el mensaje de error
          this.resultError = response.message;
        }
      },
      (error) => {
        this.resultError = error;
      }
    );
  }
}
