import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class NewUserService {

  constructor(private http: HttpClient) { }

  enviar_datos_user(user_data: any){    
    return this.http.put<any>('http://localhost:8080/paqueExpres-1.0/new-user',user_data);
  }
  
}
