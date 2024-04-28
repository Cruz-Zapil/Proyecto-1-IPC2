import { Component } from '@angular/core';
import { IvonoiceService } from './ivonoice.service';
import { FormsModule } from '@angular/forms';
import { response } from 'express';
import { error } from 'console';
import { CommonModule, DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-generate-invoice',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './generate-invoice.component.html',
  styleUrl: './generate-invoice.component.css',
})
export class GenerateInvoiceComponent {
  /// datos recibidos desde el servicio:
  result: String;
  resultError: String;
  destinos: any[] = [];

  /// atributos de clase:
  nombreDestino: String = '';
  id_destino: String = '';
  id_cliente: String = '';
  id_ruta: String = '';
  peso: number = 0;
  descripcion: String = '';
  referencia_destino: String = '';
  estado: number = 0;
  fecha: Date;
  fechaFormateada: String;
  fechaEntrega: String = '';

  listaPaquetes: any[] = [];

  /// constructor:

  constructor(private peticiones: IvonoiceService) {
    this.result = '';
    this.resultError = '';
    this.fecha = new Date(); // Obtener la fecha de hoy
    this.fechaFormateada = this.obtenerFechaFormateada(this.fecha);
  }

  /// metodos locales:
  obtenerFechaFormateada(fecha: Date): string {
    const opciones: Intl.DateTimeFormatOptions = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    };
    return fecha.toLocaleDateString('es-GT', opciones);
  }

  /// recopilar info de paquete:
  enlistaPaquete(): void {
    let datePackage = {
      id_cliente: this.id_cliente,
      id_destino: this.id_destino,
      id_ruta: this.id_ruta,
      peso: this.peso,
      descripcion: this.descripcion,
      referencia_destino: this.referencia_destino,
      estado: this.estado,
      fecha_entrada: this.fechaFormateada,
      fecha_entrega: this.fechaEntrega,
    };

    this.id_destino = '';
    this.id_ruta = '';
    this.peso = 0;
    this.descripcion = '';
    this.estado = 0;    

    this.listaPaquetes.push(datePackage);
  }




  destinoSelec(destSelec: any):void {
    this.nombreDestino = destSelec.nombre;
    this.id_destino = destSelec.id;
  }

  mostrarSiguiente(numContainer: number) {
    // Oculta el contenedor actual
    const containerActual = document.getElementById('container' + numContainer);
    if (containerActual) {
      containerActual.classList.add('d-none');
    }

    // Muestra el siguiente contenedor
    const siguienteContainer = numContainer + 1;
    const siguienteContainerElement = document.getElementById(
      'container' + siguienteContainer
    );
    if (siguienteContainerElement) {
      siguienteContainerElement.classList.remove('d-none');
    }
  }

  regresarContenedor(numContainer: number): void {
    // Oculta el contenedor actual
    const containerActual = document.getElementById('container' + numContainer);
    if (containerActual) {
      containerActual.classList.add('d-none');
    }

    // Muestra el anterior contenedor
    const siguienteContainer = numContainer - 1;
    const siguienteContainerElement = document.getElementById(
      'container' + siguienteContainer
    );
    if (siguienteContainerElement) {
      siguienteContainerElement.classList.remove('d-none');
    }
  }

  /// metodo para usar servicio:

  obtnerDestino(): void {
    this.peticiones.getDestino().subscribe(
      (response: { destinos: any[]; success: boolean; message: String }) => {
        if (response.success) {
          // Si es éxito, mostrar el mensaje de éxito
          this.result = response.message;
          this.destinos = response.destinos;
        } else {
          // Si no es éxito, mostrar el mensaje de error
          this.resultError = response.message;
        }
      },
      (error: string) => {
        this.resultError = error;
      }
    );
  }
/*
  setNewPackage(): void {
    this.peticiones.setPackage().subscribe((response) => {
      if (response.success) {
      } else {
      }
    });
  }
*/
  checkClient() {}
}
