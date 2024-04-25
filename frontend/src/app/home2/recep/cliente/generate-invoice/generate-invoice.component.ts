import { Component } from '@angular/core';

@Component({
  selector: 'app-generate-invoice',
  standalone: true,
  imports: [],
  templateUrl: './generate-invoice.component.html',
  styleUrl: './generate-invoice.component.css'
})
export class GenerateInvoiceComponent {


  constructor() { }

  mostrarSiguiente(numContainer: number) {
    // Oculta el contenedor actual
    const containerActual = document.getElementById("container" + numContainer);
    if (containerActual) {
      containerActual.classList.add("d-none");
    }

    // Muestra el siguiente contenedor
    const siguienteContainer = numContainer + 1;
    const siguienteContainerElement = document.getElementById("container" + siguienteContainer);
    if (siguienteContainerElement) {
      siguienteContainerElement.classList.remove("d-none");
    }
  }

}
