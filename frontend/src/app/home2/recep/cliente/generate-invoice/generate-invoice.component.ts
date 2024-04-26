import { Component } from '@angular/core';
import { IvonoiceService } from './ivonoice.service';
import { FormsModule } from '@angular/forms';
import { response } from 'express';
import { error } from 'console';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-generate-invoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './generate-invoice.component.html',
  styleUrl: './generate-invoice.component.css',
})
export class GenerateInvoiceComponent {
  /// datos recibidos desde el servicio:
  result: String;
  resultError: String;
  destinos: any[] = [];


  /// atributos de clase: 

  fecha: Date;
  fechaFormateada: String;

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
      hour: '2-digit'
    };
    return fecha.toLocaleDateString('es-GT', opciones);
  }





  /// metodo para usar servicio:

  obtnerDestino() {
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

  checkClient(){
    
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

  regresarContenedor(numContainer: number) {
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
}
