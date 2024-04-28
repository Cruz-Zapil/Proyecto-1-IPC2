import { Component } from '@angular/core';
import { RegistroPaqueteService } from '../registro-paquete.service';

@Component({
  selector: 'app-package-entrada',
  standalone: true,
  imports: [],
  templateUrl: './package-entrada.component.html',
  styleUrl: './package-entrada.component.css'
})
export class PackageEntradaComponent {

  constructor(private peticionRegistro: RegistroPaqueteService){}

  


}
