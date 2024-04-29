import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RegistroPaqueteService {

  constructor(private http: HttpClient) {}

  sesion(datosSesion: any): any {
    let params = new HttpParams();
    for (const key in datosSesion) {
      params = params.append(key, datosSesion[key]);
    }
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/sesion-oper',
      { params: params }
    );
  }

  getRegistro(datosRegistro: any): any{

    let params = new HttpParams();
    for (const key in datosRegistro) {
      params = params.append(key, datosRegistro[key]);
    }
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/get-registro',
      { params: params }
    );
  }
  getPackageRegistro(datosRegistro: any): any{

    let params = new HttpParams();
    for (const key in datosRegistro) {
      params = params.append(key, datosRegistro[key]);
    }
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/get-package-registro',
      { params: params }
    );
  }

  getPackage(datosPackage: any): any {
    let params = new HttpParams();
    for (const key in datosPackage) {
      params = params.append(key, datosPackage[key]);
    }
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/search-package',
      { params: params }
    );
  }

  setRegistro(datosRegistro: any): any {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<any>(
      'http://localhost:8080/paqueExpres-1.0/new-registro-punto',
      datosRegistro,
      { headers }
    );
  }
  setUpdateRegistro(datosUpRegistro: any): any {
    return this.http.put<any>(
      'http://localhost:8080/paqueExpres-1.0/update-registro',
      datosUpRegistro
    );
  }
}
