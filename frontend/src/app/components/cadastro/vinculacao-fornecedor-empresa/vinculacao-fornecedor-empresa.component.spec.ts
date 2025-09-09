import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VinculacaoFornecedorEmpresaComponent } from './vinculacao-fornecedor-empresa.component';

describe('VinculacaoFornecedorEmpresaComponent', () => {
  let component: VinculacaoFornecedorEmpresaComponent;
  let fixture: ComponentFixture<VinculacaoFornecedorEmpresaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VinculacaoFornecedorEmpresaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VinculacaoFornecedorEmpresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
