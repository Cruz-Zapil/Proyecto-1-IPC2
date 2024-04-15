import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletePuntoComponent } from './delete-punto.component';

describe('DeletePuntoComponent', () => {
  let component: DeletePuntoComponent;
  let fixture: ComponentFixture<DeletePuntoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeletePuntoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeletePuntoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
