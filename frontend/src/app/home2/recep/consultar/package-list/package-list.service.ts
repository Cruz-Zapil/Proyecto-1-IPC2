import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PackageListService {

  constructor(private http: HttpClient) { }

  getPackage(datos: any):any{
    return this.http.get<any>('http://localhost:8080/paqueExpres-1.0/search-package', datos);

  }


}
