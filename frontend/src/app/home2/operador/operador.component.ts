import { Component, EventEmitter, Output, output } from '@angular/core';
import { PackageSalidaComponent } from './package-salida/package-salida.component';
import { PackageEntradaComponent } from './package-entrada/package-entrada.component';
import { RegistroPaqueteService } from './registro-paquete.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-operador',
  standalone: true,
  imports: [
    CommonModule,
    PackageSalidaComponent,
    PackageEntradaComponent,
    FormsModule,
  ],
  templateUrl: './operador.component.html',
  styleUrl: './operador.component.css',
})
export class OperadorComponent {
  /// compartir datos:

  componente: boolean[] = [
    true, /// registro de entrada
    false, // registro de salida
  ];

  /// datos recopilados del front:
  idUser: String = '';
  idPunto: String = '';

  /// datos recopilados en las peticiones:
  errorLoggin: String = '';
  messageResult: String = '';
  resultError: String = '';
  datos: any[] = [];
  id_ruta: String = '';
  id_punto: String = '';
  id_usuario: String = '';
  logginTrue: boolean = false;

  constructor(private peticiones: RegistroPaqueteService) {}

  sesionUser() {
    let datosUserE = {
      id_usuario: this.idUser,
      id_punto_control: this.idPunto,
    };

    this.peticiones.sesion(datosUserE).subscribe(
      (response: { message: String; success: boolean; datos: any[] }) => {
        /// analizar tipo de mensaje

        if (response.success) {
          /// se encontro el usuario
          this.messageResult = 'Bienvenido';
          this.datos = response.datos;
          this.obtenerAtributos();
          this.logginTrue = true;
        } else {
          /// no se encontro concidencias
          this.messageResult = response.message;
          this.errorLoggin = 'Escriba bien los datos.';
          this.limpiarImputs();
        }
      },
      (error: String) => {
        this.resultError = error;
      }
    );
  }

  limpiarImputs(): void {
    this.idUser = '';
    this.idPunto = '';
  }

  obtenerAtributos(): void {
    // Verificar si hay datos
    if (this.datos && this.datos.length > 0) {
      // Obtener el primer elemento del arreglo
      let objeto = this.datos[0];
      // Acceder a los atributos del objeto
      this.id_punto = objeto.id_punto_control;
      this.id_ruta = objeto.id_ruta;
      this.id_usuario = objeto.id_usuario;
    }
  }
}
