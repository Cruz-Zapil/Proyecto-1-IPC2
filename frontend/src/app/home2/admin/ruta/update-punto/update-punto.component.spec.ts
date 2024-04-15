import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePuntoComponent } from './update-punto.component';

describe('UpdatePuntoComponent', () => {
  let component: UpdatePuntoComponent;
  let fixture: ComponentFixture<UpdatePuntoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdatePuntoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdatePuntoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
