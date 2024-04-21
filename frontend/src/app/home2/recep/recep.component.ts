import { Component } from '@angular/core';
import { PackageListComponent } from "./consultar/package-list/package-list.component";
import { SearchPackageComponent } from "./consultar/search-package/search-package.component";
import { GenerateInvoiceComponent } from "./cliente/generate-invoice/generate-invoice.component";

@Component({
    selector: 'app-recep',
    standalone: true,
    templateUrl: './recep.component.html',
    styleUrl: './recep.component.css',
    imports: [PackageListComponent, SearchPackageComponent, GenerateInvoiceComponent]
})
export class RecepComponent {


  componente: boolean[] = [
    true, /// lista de paquetes por entregar
    false, // buscar paquete
    false, //  facturar o nuevo envio
    false // Entregas
  ];

  desactivarComponente(id_componente: number): void {
    for (let key = 0; key < this.componente.length; key++) {
      this.componente[key] = key === id_componente;
    }
  }



}
