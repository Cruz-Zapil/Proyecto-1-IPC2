import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistroPaqueteService {

  constructor(private http: HttpClient) { }

  sesion(datosSesion: any):any{
    let params = new HttpParams();
    for (const key in datosSesion){

      params = params.append(key, datosSesion[key]);
    } 
      return this.http.get<any>('http://localhost:8080/paqueExpres-1.0/sesion-oper',{ params: params});
  }

  setRegistro(datosRegistro: any): any{
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/new-registro-punto',datosRegistro);
  }

  setUpdateRegistro(datosUpRegistro: any): any{
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/update-registro-punto',datosUpRegistro);
  }
}
