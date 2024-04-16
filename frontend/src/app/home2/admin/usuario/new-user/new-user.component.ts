import { Component } from '@angular/core';
import { NewUserService } from './new-user.service';

@Component({
  selector: 'app-new-user',
  standalone: true,
  imports: [],
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css'
})
export class NewUserComponent {

  constructor( private newService: NewUserService){

  }

}
