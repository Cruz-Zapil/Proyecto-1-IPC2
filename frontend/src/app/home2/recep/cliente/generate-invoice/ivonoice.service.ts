import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class IvonoiceService {
  constructor(private http: HttpClient) {}
  getDestino(): any {
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/consulta-destino'
    );
  }

  getCliente(idCliente: any): any {
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/consulta-cliente',
      idCliente
    );
  }

  setCliene(datosCliente: any): any {
    return this.http.put<any>('', datosCliente);
  }

  setPackage(datosPaquete: any): any {
    return this.http.put<any>(
      'http://localhost:8080/paqueExpres-1.0/new-package',
      datosPaquete
    );
  }
  setDetalleFactura(datosDetalle: any): any {
    return this.http.put<any>('', datosDetalle);
  }
}
