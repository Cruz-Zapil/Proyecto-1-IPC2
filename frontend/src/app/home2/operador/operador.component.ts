import { Component } from '@angular/core';
import { PackageSalidaComponent } from './package-salida/package-salida.component';
import { PackageEntradaComponent } from './package-entrada/package-entrada.component';
import { RegistroPaqueteService } from './registro-paquete.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import e, { response } from 'express';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-operador',
  standalone: true,
  imports: [CommonModule, PackageSalidaComponent, PackageEntradaComponent, FormsModule],
  templateUrl: './operador.component.html',
  styleUrl: './operador.component.css',
})
export class OperadorComponent {
  componente: boolean[] = [
    true, /// registro de entrada
    false, // registro de salida
  ];

  /// datos recopilados del front:
  idUser: String = '';
  idPunto: String = '';

  /// datos recopilados en las peticiones:
  result: String;
  resultError: String;
  datos: any[] = [];
  id_ruta: String = '';
  id_punto: String = '';

  constructor(private peticiones: RegistroPaqueteService) {
    this.result = '';
    this.resultError = '';
  }

  sesionUser(): void {
    let datosUser = {
      id_punto_control: this.idPunto,
      id_usuario: this.idUser,
    };

    this.peticiones.sesion(datosUser).subscribe(
      (response: { message: String; success: boolean; datos: any[] }) => {
        /// analizar tipo de mensaje

        if (response.success) {
          /// se encontro el usuario
          this.result = response.message;
          this.datos = response.datos;
        } else {
          /// no se encontro concidencias
          this.result = response.message;
        }
      },
      (error: String) => {
        this.resultError = error;
      }
    );
  }

  obtenerAtributos(): void {
    let objeto = this.datos[0];
    this.id_punto = objeto.id_punto;
    this.id_ruta = objeto.id_ruta;
  }
}
