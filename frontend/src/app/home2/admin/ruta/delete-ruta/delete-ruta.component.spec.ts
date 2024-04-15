import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteRutaComponent } from './delete-ruta.component';

describe('DeleteRutaComponent', () => {
  let component: DeleteRutaComponent;
  let fixture: ComponentFixture<DeleteRutaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteRutaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteRutaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
