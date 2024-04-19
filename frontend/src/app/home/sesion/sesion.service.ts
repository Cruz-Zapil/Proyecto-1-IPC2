import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SesionService {

  constructor(private http: HttpClient) { }

  loggin(user_data: any){
    return this.http.post<any>('http://localhost:8080/paqueExpres-1.0/inicio-sesion',user_data);
  }

}
