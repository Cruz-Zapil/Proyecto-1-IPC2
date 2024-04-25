import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SesionComponent } from './sesion/sesion.component';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [SesionComponent],
})
export class HomeComponent {

}
