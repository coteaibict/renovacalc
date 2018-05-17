import { Component, OnInit, OnDestroy, ViewChild, TemplateRef } from '@angular/core'
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Observable } from 'rxjs/Observable'
import { ActivatedRoute, Router } from '@angular/router';
import { ViewEncapsulation } from '@angular/core';
import { NgForm } from '@angular/forms';

import 'rxjs/add/operator/first';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Rota } from '../rota.model'
import { RotaVersao } from '../rota-versao.model'
import { RotaSessao } from '../rota-sessao.model';
import { RotaService } from '../rota.service'
import { RespostaService } from '../../resposta/resposta.service'
import { RotaResposta } from '../../resposta/rota-resposta.model'
import { RotaAtributo } from '../rota-atributo.model';
import { RotaAtributoResposta } from '../../resposta/rota-atributo-resposta.model';

@Component({
    selector: 'app-rota-formulario',
    templateUrl: './rota-formulario.component.html',
    styleUrls: ['./rota-formulario.component.css'],
    encapsulation: ViewEncapsulation.None,
})
export class RotaFormularioComponent implements OnInit {

    @ViewChild('validacaoModal') validacaoModal: TemplateRef<any>;

    private versao: RotaVersao;

    private simulado: boolean = false;

    private respostasDict: Map<number, [RotaAtributo, string]>;

    private obrigatoriedadeAtributos : Map<number, boolean>;

    constructor(private rotaService: RotaService,
                private respostaService: RespostaService,
                private modalService: NgbModal,
                private route: ActivatedRoute,
                private router: Router) { }


    ngOnInit() {
        this.recuperarVersao();
    }

    async recuperarVersao() {
        let params = await this.route.params.first().toPromise();
        try {
            this.versao = await this.rotaService.recuperarVersaoAtual(+params['id']);
            this.obrigatoriedadeAtributos = this.rotaService.criarDicionarioObrigatorios(this.versao);
            this.rotaService.ordenarRota(this.versao);
        } catch (e) {
            if (e instanceof HttpErrorResponse) {
                if (e.status == 404) {
                    this.router.navigate(['/notfound']);
                }
            }
        }
        this.respostasDict = await this.respostaService.inicializarRespostasDict(this.versao);
    }

    async calcularResposta(form: NgForm) {
        form.ngSubmit.emit();
        this.simulado = true;
        if (!this.todosObrigatoriosPreenchidos()) {
            this.abrirModal(this.validacaoModal)
            return;
        }
        let resposta = this.respostaService.reconstruirResposta(this.respostasDict, this.versao);
        try {
            resposta = await this.respostaService.calcularResposta(resposta);
            this.respostasDict = this.respostaService.reconstruirRespostasDict(resposta);
        } catch (e) {
            if (e instanceof HttpErrorResponse) {
                if (e.status == 404) {
                    this.router.navigate(['/notfound']);
                } else {
                    // Erro interno no servidor
                    this.router.navigate(['/erro']);
                }
            }
        }
    }

    todosObrigatoriosPreenchidos() {
        for (let [atributo, value] of Array.from(this.respostasDict.values())) {
            if (this.obrigatoriedadeAtributos.get(atributo.id) && !atributo.formula && !value ) {
                return false;
            }
        }
        return true;
    }

    abrirModal(content) {
        this.modalService.open(content, {backdropClass: 'light-gray-background'});
    }

}
