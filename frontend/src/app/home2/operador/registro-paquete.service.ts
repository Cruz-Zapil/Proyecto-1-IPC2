import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistroPaqueteService {

  constructor(private http: HttpClient) { }

  sesion(datosSesion: any):any{
    return this.http.get<any>('http://localhost:8080/paqueExpres-1.0/sesion-oper',datosSesion);
  }

  setRegistro(datosRegistro: any): any{
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/new-registro-punto',datosRegistro);
  }

  setUpdateRegistro(datosUpRegistro: any): any{
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/update-registro-punto',datosUpRegistro);
  }
}
