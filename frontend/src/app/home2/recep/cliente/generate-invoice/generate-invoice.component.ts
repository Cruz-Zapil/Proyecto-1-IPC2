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

  /// mensajes:
  statusClient: String = '';
  statusPaquete: String = '';
  statusNewPaquete: boolean = false;
  statusNewFactura: String = '';
  statusNewDetalle: String = '';

  /// atributos de clase:
  nombreDestino: String = '';
  id_destino: String = '';
  id_cliente: String = ''; ///
  id_ruta: String = '';
  peso: number = 0;
  descripcion: String = '';
  referencia_destino: String = '';
  estado: number = 1;
  fecha: Date;
  fechaFormateada: String;
  fechaEntrega: String = '';

  listaPaquetes: any[] = [];
  total: number = 0;

  /// atributso destino:
  destinoPrecio: number = 0;

  /// id de facura creada
  id_facturaCreada: String = '';

  /// constructor:

  constructor(private peticiones: IvonoiceService) {
    this.result = '';
    this.resultError = '';
    this.fecha = new Date(); // Obtener la fecha de hoy
    this.fechaFormateada = this.obtenerFechaFormateada(this.fecha);
  }

  /// metodos locales:
  obtenerFechaFormateada(fecha: Date): string {
    const year = fecha.getFullYear();
    const month = ('0' + (fecha.getMonth() + 1)).slice(-2); // Agrega un cero al mes si es menor a 10
    const day = ('0' + fecha.getDate()).slice(-2); // Agrega un cero al día si es menor a 10
    const hours = ('0' + fecha.getHours()).slice(-2); // Agrega un cero a las horas si es menor a 10
    const minutes = ('0' + fecha.getMinutes()).slice(-2); // Agrega un cero a los minutos si es menor a 10
    const seconds = ('0' + fecha.getSeconds()).slice(-2); // Agrega un cero a los segundos si es menor a 10

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
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
      precioDestino: this.destinoPrecio
    };

    this.id_destino = '';
    this.id_ruta = '';
    this.peso = 0;
    this.descripcion = '';
    this.estado = 0;

    this.listaPaquetes.push(datePackage);
  }

  destinoSelec(destSelec: any): void {
    this.destinoPrecio = destSelec.precio;
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

  /// verificar cliente:
  verificarCliente(): void {
    let datos = {
      id_cliente: this.id_cliente,
    };

    this.peticiones
      .getCliente(datos)
      .subscribe((response: { susccess: boolean }) => {
        if (response.susccess) {
          this.statusClient = 'Cliente existente';
        } else {
          this.statusClient = 'Cliente inexistente';
        }
        (error: String) => {
          this.statusClient = error;
        };
      });
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

  confirmarPaquete(): void {
    for (let i = 0; i < this.listaPaquetes.length; i++) {
      
      const paquete = this.listaPaquetes[i];

      const datosJSON = JSON.stringify(paquete);

      this.peticiones.setPackage(datosJSON).subscribe(
        (response: { message: string; success: boolean }) => {
          if (response.success) {
            this.statusPaquete = response.message;
            this.statusNewPaquete = true;
            /// creamos una factura y luego creamos complemenos de esa factura:
            this.total = this.total + paquete.precioDestino * paquete.peso;
          } else {
            this.statusNewPaquete = false;
            this.statusPaquete = response.message;
          }
        },
        (error: any) => {}
      );
    }
  }

  crearFactura(): void {
    let datosAFacturar = {
      id_cliente: this.id_cliente,
      id_recepcionista: '',
      fecha: this.fechaFormateada,
      total: this.total,
    };

    const datosJSON = JSON.stringify(datosAFacturar);

    this.peticiones.setNewFactura(datosJSON).subscribe(
      (response: {
        id_factura: String;
        message: String;
        susccess: boolean;
      }) => {
        if (response.susccess) {
          this.statusNewFactura = response.message;
          this.id_facturaCreada = response.id_factura;
        } else {
          this.statusNewFactura = response.message;
        }
      },
      (error: String) => {
        this.statusNewFactura = error;
      }
    );
  }

  setDetalleFactura(): void {
    for (let i = 0; i < this.listaPaquetes.length; i++) {
      const paquete = this.listaPaquetes[i];

      let detalles = {
        id_factura: this.id_facturaCreada,
        id_paquete: paquete.id_paquete,
      };

      const datosJSON = JSON.stringify(detalles);

      this.peticiones.setDetalleFactura(datosJSON).subscribe(
        (response: { message: string; success: boolean }) => {
          if (response.success) {
            this.statusNewDetalle = response.message;
          } else {
            this.statusNewDetalle = response.message;
          }
        },
        (error: any) => {}
      );
    }
    this.listaPaquetes = [];

  }
}
