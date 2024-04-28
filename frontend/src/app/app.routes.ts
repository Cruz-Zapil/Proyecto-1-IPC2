import { Routes } from '@angular/router';
import { app } from '../../server';
import { AdminComponent } from './home2/admin/admin.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { Home2Component } from './home2/home2.component';
import { RecepComponent } from './home2/recep/recep.component';
import { OperadorComponent } from './home2/operador/operador.component';
import { SesionComponent } from './home/sesion/sesion.component';

export const routes: Routes = [

  {
    path: 'home', component: HomeComponent,
    children: [
      { path: 'sesion', component: SesionComponent}
    ]
  },
  {
    path: 'home2',
    component: Home2Component,
    children: [
      { path: 'admin', component: AdminComponent },
      { path: 'recep', component: RecepComponent },
      { path: 'operador', component: OperadorComponent },
    ],
  },
];
