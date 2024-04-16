import { Component } from '@angular/core';
import { NewUserComponent } from './usuario/new-user/new-user.component';
import { UpdateUserComponent } from './usuario/update-user/update-user.component';
import { NewTarifaComponent } from './tarifa/new-tarifa/new-tarifa.component';
import { flatMap } from 'rxjs';
import { DeleteUserComponent } from './usuario/delete-user/delete-user.component';
import { NewRutaComponent } from './ruta/new-ruta/new-ruta.component';
import { DeleteRutaComponent } from './ruta/delete-ruta/delete-ruta.component';
import { UpdateRutaComponent } from './ruta/update-ruta/update-ruta.component';
import { NewPuntoComponent } from './punto-control/new-punto/new-punto.component';
import { DeletePuntoComponent } from './punto-control/delete-punto/delete-punto.component';
import { UpdatePuntoComponent } from './punto-control/update-punto/update-punto.component';
import { ReporteRutaComponent } from "./reporte/reporte-ruta/reporte-ruta.component";
import { ReporteGananciaComponent } from "./reporte/reporte-ganancia/reporte-ganancia.component";
import { ReporteClientesComponent } from "./reporte/reporte-clientes/reporte-clientes.component";
import { NewDestinoComponent } from "./destino/new-destino/new-destino.component";
import { DeleteDestinoComponent } from "./destino/delete-destino/delete-destino.component";
import { UpdateDestinoComponent } from "./destino/update-destino/update-destino.component";
import { ChangeTarifaComponent } from "./tarifa/change-tarifa/change-tarifa.component";
import { UpdateTarifaComponent } from "./tarifa/update-tarifa/update-tarifa.component";

@Component({
    selector: 'app-admin',
    standalone: true,
    templateUrl: './admin.component.html',
    styleUrl: './admin.component.css',
    imports: [
        NewUserComponent,
        UpdateUserComponent,
        NewTarifaComponent,
        DeleteUserComponent,
        NewRutaComponent,
        DeleteRutaComponent,
        UpdateRutaComponent,
        NewPuntoComponent,
        DeletePuntoComponent,
        UpdatePuntoComponent,
        ReporteRutaComponent,
        ReporteGananciaComponent,
        ReporteClientesComponent,
        NewDestinoComponent,
        DeleteDestinoComponent,
        UpdateDestinoComponent,
        ChangeTarifaComponent,
        UpdateTarifaComponent
    ]
})
export class AdminComponent {
  componente: boolean[] = [
    false, //new_user: 0
    false, //delete_user: 1
    false, //update_user: 2
    false, //new_ruta: 3
    false, //delete_ruta: 4
    false, //update_ruta: 5
    false, //new_punto: 6
    false, //delete_punto: 7
    false, //update_punto: 8
    false, //reporte_ruta: 9
    false, //reporte_ganancia: 10
    false, //reporte_cliente: 11
    false, //new_destino: 12
    false, //delete_destino: 13
    false, //update_destino: 14
    false, //new_tarifa: 15
    false, //delete_tarifa: 16
    false, //update_tarifa: 17
  ];

  desactivarComponente(id_componente: number): void {
    for (let key = 0; key < this.componente.length; key++) {
      this.componente[key] = key === id_componente;
    }
  }
}
