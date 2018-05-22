import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErroNoServidorComponent } from './erro-no-servidor.component';

describe('ErroNoServidorComponent', () => {
  let component: ErroNoServidorComponent;
  let fixture: ComponentFixture<ErroNoServidorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ErroNoServidorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErroNoServidorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
