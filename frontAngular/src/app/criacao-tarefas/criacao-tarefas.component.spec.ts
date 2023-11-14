import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriacaoTarefasComponent } from './criacao-tarefas.component';

describe('CriacaoTarefasComponent', () => {
  let component: CriacaoTarefasComponent;
  let fixture: ComponentFixture<CriacaoTarefasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CriacaoTarefasComponent]
    });
    fixture = TestBed.createComponent(CriacaoTarefasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
