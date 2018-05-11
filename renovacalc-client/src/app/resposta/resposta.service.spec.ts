import { TestBed, inject } from '@angular/core/testing';

import { RespostaService } from './resposta.service';

describe('RespostaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RespostaService]
    });
  });

  it('should be created', inject([RespostaService], (service: RespostaService) => {
    expect(service).toBeTruthy();
  }));
});
