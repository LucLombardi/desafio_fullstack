import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpresaListagemComponent } from './empresa-listagem.component';

describe('EmpresaListagemComponent', () => {
  let component: EmpresaListagemComponent;
  let fixture: ComponentFixture<EmpresaListagemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmpresaListagemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpresaListagemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
