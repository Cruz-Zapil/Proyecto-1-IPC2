import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SesionComponent } from './sesion/sesion.component';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [SesionComponent,RouterOutlet ],
})
export class HomeComponent {

  constructor(private router: Router){

    this.router.navigate(['home/sesion']);

  }
  
}
