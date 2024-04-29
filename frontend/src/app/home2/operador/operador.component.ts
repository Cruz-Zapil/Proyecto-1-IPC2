import { Component, EventEmitter, Output, output } from '@angular/core';
import { RegistroPaqueteService } from './registro-paquete.service';
import { FormsModule } from '@angular/forms';
import { CommonModule, Time } from '@angular/common';
import { response } from 'express';
import { error } from 'console';
@Component({
  selector: 'app-operador',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './operador.component.html',
  styleUrl: './operador.component.css',
})
export class OperadorComponent {
  /// compartir datos:

  componente: boolean[] = [
    true, /// registro de entrada
    false, // registro de salida
  ];

  ///datos registro para actualizar:
  id_registro: String = '';
  id_paquete: String = '';
  id_puntoRegistro: String = '';
  hora_acumulada: number = 0;
  costo_generado: number = 0;
  fechaEntra: String = '';
  fechaSale: Date | undefined;
  tmp: boolean = false;
  mensajeUpdate: String = '';

  /// datos recopilados del front:
  idUser: String = '';
  idPunto: String = '';
  idPackage: String = '';
  fechaEntrada: String = '';
  fechaSalida: String = '';

  /// datos recopilados en las peticiones:
  errorLoggin: String = '';
  messageResult: String = '';
  resultError: String = '';
  datos: any[] = [];
  id_rutaPunto: String = '';
  id_punto: String = '';
  id_usuario: String = '';
  logginTrue: boolean = false;
  paqueteRuta: boolean = false;
  datosPaquete: any[] = [];
  id_rutaPaquete: String = '';
  mensajeErroPaquete: String = '';
  datosRegistro: any[] = [];
  tarifaLocal: number = 0;

  constructor(private peticiones: RegistroPaqueteService) {}

  //// metodos para hacer peticiones
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

  getPackage() {
    let datosEnviar = {
      columna: 'id_paquete',
      condicion: this.idPackage,
    };

    this.peticiones
      .getPackage(datosEnviar)
      .subscribe((response: { success: boolean; package: any[] }) => {
        if (response.success) {
          this.datosPaquete = response.package;
          this.obtenerAtributosPaquete();

          if (this.id_rutaPunto == this.id_rutaPaquete) {
            this.paqueteRuta = true;
            this.saveRegistroEntrada();
          } else {
            this.mensajeErroPaquete = ' paquete no ligado a esta ruta';
          }
        } else {
        }
      });
  }

  obtenerAtributosPaquete(): void {
    if (this.datosPaquete && this.datosPaquete.length > 0) {
      let objeto = this.datosPaquete[0];
      this.id_rutaPaquete = objeto.id_ruta;
    }
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
      this.id_rutaPunto = objeto.id_ruta;
      this.id_usuario = objeto.id_usuario;
      this.tarifaLocal = objeto.tarifa_local;
    }
  }

  saveRegistroEntrada(): void {
    let datosRegisto = {
      id_paquete: this.idPackage,
      id_punto_control: this.id_punto,
      horas_acumuladas: 0,
      costo_generado: 0,
      fecha_entrada: this.fechaEntrada,
    };

    const datosJSON = JSON.stringify(datosRegisto);

    this.peticiones
      .setRegistro(datosJSON)
      .subscribe((response: { message: String; success: boolean }) => {
        if (response.success) {
          /// exito de registro
          this.mensajeUpdate = response.message;
        } else {
          this.mensajeUpdate = response.message;
        }
      });
  }

  getPackageRegistro(): void {
    let datosEnviar = {
      condicion: this.idPackage,
    };

    this.peticiones.getPackageRegistro(datosEnviar).subscribe(
      (response: { message: String; datos: any[]; success: boolean }) => {
        if (response.success) {
          /// exito si se encutra el registro del paquete:
          this.datosRegistro = response.datos;
          this.getAtributosRegistro();
        } else {
          this.mensajeUpdate = response.message;
        }
      },
      (error: String) => {
        this.resultError = error;
      }
    );
  }

  getAtributosRegistro(): void {
    // Verificar si hay datos
    if (this.datosRegistro && this.datosRegistro.length > 0) {
      // Obtener el primer elemento del arreglo
      let objeto = this.datosRegistro[0];

      this.id_registro = objeto.id_registro;
      this.id_paquete = objeto.id_paquete;
      this.id_puntoRegistro = objeto.id_punto_control;
      this.fechaEntra = objeto.fecha_entrada;

      if (this.fechaSalida && this.fechaEntra) {
        let tmpo = new Date(this.fechaSalida as string);
        let tmpoE = new Date(this.fechaEntra as string);

        const diferencia = tmpo.getTime() - tmpoE.getTime();
        this.tmp = true;

        this.hora_acumulada = Math.floor(diferencia / (1000 * 60 * 60)); // Convertir a horas
        this.costo_generado = this.hora_acumulada * this.tarifaLocal;

        let datosUpdate = {
          id_paquete: this.id_paquete,
          id_punto_control: this.id_puntoRegistro,
          horas_acumuladas: this.hora_acumulada,
          costo_generado: this.costo_generado,
          fecha_entrada: this.fechaEntra,
          fecha_salida: this.fechaSalida,
          id_punto_registro: this.id_registro,
        };

        this.peticiones.setUpdateRegistro(datosUpdate).subscribe(
          (response: { message: String; success: boolean }) => {
            if (response.success) {
              this.mensajeUpdate = response.message;
            } else {
              this.mensajeUpdate = response.message;
            }
          },
          (error: any) => {
            console.log(error);
          }
        );
      }
    }
  }
}
