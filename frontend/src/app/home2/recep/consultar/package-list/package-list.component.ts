import { Component } from '@angular/core';
import { PackageListService } from './package-list.service';
import { response } from 'express';
import { error } from 'console';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-package-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './package-list.component.html',
  styleUrl: './package-list.component.css',
})
export class PackageListComponent {
  /// detos recibido del servlet
  resultError: String;
  package: any[] = [];

  constructor(private peticion: PackageListService) {
    this.resultError = '';
    /// obtener paquetes sin entregar
    this.getPackage();
  }

  getPackage() {
    let datoSolicitud = {
      columan: 'estado',
      condicion: '1',
    };

    this.peticion.getPackage(datoSolicitud).subscribe(
      (response: { message: String; package: any[]; succes: boolean }) => {
        if (response.succes) {
          this.package = response.package;
        } else {
          // si sale error
          this.resultError = response.message;
        }
      },
      (error: String) => {
        this.resultError = error;
      }
    );
  }
}
