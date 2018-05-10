import { Component, OnInit, OnDestroy } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs/Observable'
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { ViewEncapsulation } from '@angular/core';
import 'rxjs/add/operator/first';

import { Rota } from '../rota.model'
import { RotaVersao } from '../rota-versao.model'
import { RotaSessao } from '../rota-sessao.model';
import { RotaService } from '../rota.service'
import { RotaResposta } from '../../resposta/rota-resposta.model'
import { RotaAtributo } from '../rota-atributo.model';

@Component({
    selector: 'app-rota-formulario',
    templateUrl: './rota-formulario.component.html',
    styleUrls: ['./rota-formulario.component.css'],
    encapsulation: ViewEncapsulation.None,
})
export class RotaFormularioComponent implements OnInit {

    private versao: RotaVersao;

    private resposta: RotaResposta;

    private respostasDict : Map<number, [RotaAtributo, string]> = new Map<number, [RotaAtributo, string]>();

    constructor(private rotaService: RotaService, 
                private route: ActivatedRoute) { }


    ngOnInit() {
        this.fetchData();
    }

    async fetchData() {
        let params = await this.route.params.first().toPromise();
        this.versao = await this.rotaService.recuperarVersaoAtual(+params['id']).toPromise();
        this.construirResposta();
    }

    construirResposta() {
        for (let sessaoPai of this.versao.sessoes) {
            for(let sessaoFilha of sessaoPai.sessoesFilhas) {
                for(let atributoResposta of sessaoPai.atributos) {
                    let atributo = atributoResposta.atributo;
                    this.respostasDict.set(atributo.id, [atributo, ""]);
                }
            }

            for(let atributoResposta of sessaoPai.atributos) {
                let atributo = atributoResposta.atributo;
                this.respostasDict.set(atributo.id, [atributo, ""]);
            }
        }
    }

}
