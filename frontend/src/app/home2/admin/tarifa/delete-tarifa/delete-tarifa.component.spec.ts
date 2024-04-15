import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteTarifaComponent } from './delete-tarifa.component';

describe('DeleteTarifaComponent', () => {
  let component: DeleteTarifaComponent;
  let fixture: ComponentFixture<DeleteTarifaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteTarifaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteTarifaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
