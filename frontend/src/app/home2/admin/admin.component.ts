import { Component } from '@angular/core';
import { NewUserComponent } from "./usuario/new-user/new-user.component";
import { UpdateUserComponent } from "./usuario/update-user/update-user.component";
import { NewTarifaComponent } from "./tarifa/new-tarifa/new-tarifa.component";

@Component({
    selector: 'app-admin',
    standalone: true,
    templateUrl: './admin.component.html',
    styleUrl: './admin.component.css',
    imports: [NewUserComponent, UpdateUserComponent, NewTarifaComponent]
})
export class AdminComponent {

}
