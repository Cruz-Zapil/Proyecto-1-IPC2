import { Component } from '@angular/core';
import { NewUserComponent } from "./usuario/new-user/new-user.component";

@Component({
    selector: 'app-admin',
    standalone: true,
    templateUrl: './admin.component.html',
    styleUrl: './admin.component.css',
    imports: [ NewUserComponent]
})
export class AdminComponent {

}
