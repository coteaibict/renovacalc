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

    /**
     * Variável que guarda referência ao elemento do DOM do modal de erro de validação
     * 
     */
    @ViewChild('validacaoModal') validacaoModal: TemplateRef<any>;

    /**
     * Objeto RotaVersao que guarda a estrutura da rota selecionada
     *
     */
    public versao: RotaVersao;

    /**
     * Booleano que indica se o usuário já submeteu alguma simulação.
     * Usado para ressaltar os campos obrigatórios mas não preenchidos
     * 
     */
    private simulado: boolean = false;

    /**
     * Dicionário que guarda os valores submetidos pelo usuário, junto com o objeto 
     * RotaAtributo, chaveado pelo ID do atributo. Usado pelo ngModel dos campos
     * referentes aos atributos.
     * 
     */
    private respostasDict: Map<number, [RotaAtributo, string]>;

    /**
     * Dicionário que indica se cada cada sessão-filha é visível (falso se ocultada).
     * Chaveado pelo ID da sessão filha.
     *
     */
    private sessoesVisiveis: Map<number, boolean> = new Map<number, boolean>();

    /**
     * Dicionário que indica se um atributo é obrigatório. Chaveado pelo ID do atributo.
     *
     */
    private obrigatoriedadeAtributos : Map<number, boolean>;

    constructor(private rotaService: RotaService,
                private respostaService: RespostaService,
                private modalService: NgbModal,
                private route: ActivatedRoute,
                private router: Router) { }


    ngOnInit() {
        this.recuperarVersao();
    }

        /**
         * Método assíncrono que faz o setup do componente. Faz a chamada ao
         * Service para recuperar o objeto RotaVersao, que contém a estrutura
         * da rota atual. Monta os dicionários de respostas, atributos
         * obrigatórios e sessões visíveis. Ordena as sessões e atributos.
         * Caso a rota acessada não exista em banco, redireciona o usuário para
         * uma página 404.
         *
         */
    async recuperarVersao() {
        let params = await this.route.params.first().toPromise();
        try {
            this.versao = await this.rotaService.recuperarVersaoAtual(+params['id']);
            this.obrigatoriedadeAtributos = this.rotaService.criarDicionarioObrigatorios(this.versao);
            this.rotaService.ordenarRota(this.versao);
            this.initSessoesVisiveis(this.versao);
        } catch (e) {
            if (e instanceof HttpErrorResponse) {
                if (e.status == 404) {
                    this.router.navigate(['/notfound']);
                }
            }
        }
        this.respostasDict = await this.respostaService.inicializarRespostasDict(this.versao);
    }

        /**
         * Método que faz a chamada ao serviço de resposta para realizar o cálculo.
         * Caso algum atributo obrigatório não tenha sido preenchido, impede a chamada
         * e abre um modal de erro de validação.
         * Caso haja algum erro no servidor, leva o usuário à página apropriada de erro.
         */
    async calcularResposta(form: NgForm) {
        this.simulado = true;
        if (!this.todosObrigatoriosPreenchidos()) {
            this.abrirModal(this.validacaoModal)
            return;
        }
        let resposta = this.respostaService.reconstruirResposta(this.respostasDict, this.versao);
        try {
            resposta = await this.respostaService.calcularResposta(resposta);
            console.log("aaaa");
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

        /**
         * Método que faz a validação de preenchimento dos atributos obrigatórios.
         * @returns true se todos os atributos obrigatórios foram preenchidos, 
         * false caso contrário.
         */
    todosObrigatoriosPreenchidos() {
        for (let [atributo, value] of Array.from(this.respostasDict.values())) {
            if (this.obrigatoriedadeAtributos.get(atributo.id) && !atributo.formula && !value ) {
                return false;
            }
        }
        return true;
    }

        /**
         * Método que inicializa o dicionário que indica se uma sessão deve ser exibida
         * ou ocultada. Inicialmente, todas devem ser exibidas.
         *
         */
    initSessoesVisiveis(versao: RotaVersao) {
        for(let sessao of versao.sessoes) {
            for(let filha of sessao.sessoesFilhas) {
                this.sessoesVisiveis.set(filha.id, true);
            }
        }
    }

        /**
         * Método auxiliar que chama o serviço modalService para abrir um modal.
         *
         */
    abrirModal(content) {
        this.modalService.open(content, {backdropClass: 'light-gray-background'});
    }

    preencherPadrao() {
        for(let sessao of this.versao.sessoes) {
            for(let filha of sessao.sessoesFilhas) {
                for(let a of filha.atributos) {
                    this.respostasDict.set(a.atributo.id, [a.atributo, a.valorPadrao]);
                }
            }
        }
    }

    limparValores() {
        for(let key of Array.from(this.respostasDict.keys())) {
            this.respostasDict.set(key, [this.respostasDict.get(key)[0], ""]);
        }
    }

}
