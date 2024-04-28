import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeseionFalseComponent } from './seseion-false.component';

describe('SeseionFalseComponent', () => {
  let component: SeseionFalseComponent;
  let fixture: ComponentFixture<SeseionFalseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeseionFalseComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SeseionFalseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
