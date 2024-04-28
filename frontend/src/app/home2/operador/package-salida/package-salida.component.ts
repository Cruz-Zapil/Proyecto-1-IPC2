import { Component } from '@angular/core';
import { RegistroPaqueteService } from '../registro-paquete.service';

@Component({
  selector: 'app-package-salida',
  standalone: true,
  imports: [],
  templateUrl: './package-salida.component.html',
  styleUrl: './package-salida.component.css'
})
export class PackageSalidaComponent {

  constructor(private peticionRegistro: RegistroPaqueteService){

  }


  

}
