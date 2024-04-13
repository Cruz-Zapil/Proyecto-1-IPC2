import { Component } from '@angular/core';
import { AdminComponent } from "./admin/admin.component";

@Component({
    selector: 'app-home2',
    standalone: true,
    templateUrl: './home2.component.html',
    styleUrl: './home2.component.css',
    imports: [AdminComponent]
})
export class Home2Component {

  title: String='Admin';
}
