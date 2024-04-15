import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewRutaComponent } from './new-ruta.component';

describe('NewRutaComponent', () => {
  let component: NewRutaComponent;
  let fixture: ComponentFixture<NewRutaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewRutaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
