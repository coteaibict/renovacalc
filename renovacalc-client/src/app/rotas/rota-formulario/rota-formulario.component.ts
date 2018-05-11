import { Component, OnInit, OnDestroy } from '@angular/core'
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Observable } from 'rxjs/Observable'
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { ViewEncapsulation } from '@angular/core';

import 'rxjs/add/operator/first';

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

    private versao: RotaVersao;

    private respostasDict: Map<number, [RotaAtributo, string]>;

    constructor(private rotaService: RotaService,
                private respostaService: RespostaService,
                private route: ActivatedRoute,
                private router: Router) { }


    ngOnInit() {
        this.recuperarVersao();
    }

    async recuperarVersao() {
        let params = await this.route.params.first().toPromise();
        try {
            this.versao = await this.rotaService.recuperarVersaoAtual(+params['id']);
        } catch (e) {
            if (e instanceof HttpErrorResponse) {
                if (e.status == 404) {
                    this.router.navigate(['/notfound']);
                }
            }
        }
        this.respostasDict = await this.respostaService.inicializarRespostasDict(this.versao);
            console.log(this.respostasDict);
    }

    async calcularResposta() {
        let resposta = this.respostaService.reconstruirResposta(this.respostasDict, this.versao);
        try {
            resposta = await this.respostaService.calcularResposta(resposta);
            this.respostasDict = this.respostaService.reconstruirRespostasDict(resposta);
        } catch (e) {
            if (e instanceof HttpErrorResponse) {
                if (e.status == 404) {
                    this.router.navigate(['/notfound']);
                } else {
                    // Erro interno
                    console.log("erro");
                    this.router.navigate(['/notfound']);
                }
            }
        }
    }

}
