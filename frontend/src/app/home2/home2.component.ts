import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { RecepComponent } from './recep/recep.component';
import { OperadorComponent } from './operador/operador.component';

@Component({
  selector: 'app-home2',
  standalone: true,
  templateUrl: './home2.component.html',
  styleUrl: './home2.component.css',
  imports: [RouterOutlet,AdminComponent, RecepComponent, OperadorComponent],
})
export class Home2Component  {
  title: String = '';
  nombreUser: String = '';


  constructor(private router: Router, private route: ActivatedRoute ){
   
  }
 
}

