import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { Home2Component } from './home2/home2.component';
import { AdminComponent } from './home2/admin/admin.component';
import { SesionComponent } from './home/sesion/sesion.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    RouterOutlet,
    HomeComponent,
    Home2Component,
    AdminComponent,
    SesionComponent,
  ],
})
export class AppComponent {
  title = 'frontend-IPC2';

  constructor(private router: Router){
    this.router.navigate(['/home']);
  }

}
