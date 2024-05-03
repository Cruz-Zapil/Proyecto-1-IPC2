import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
    let params = new HttpParams();
    for (const key in idCliente) {
      params = params.append(key, idCliente[key]);
    }
    return this.http.get<any>(
      'http://localhost:8080/paqueExpres-1.0/consulta-cliente',
      { params: params }
    );
  }

  setCliene(datosCliente: any): any {
    return this.http.put<any>('', datosCliente);
  }

  
  setPackage(datosPaquete: any): any {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<any>(
      'http://localhost:8080/paqueExpres-1.0/new-package',datosPaquete,{ headers }
    );
  }

  setDetalleFactura(datosDetalle: any): any {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/new-detalleFactura', datosDetalle, {headers});
  }

  setNewFactura(datosFactura: any): any{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/new-factura',datosFactura,{headers})

  }

}
