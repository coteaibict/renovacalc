import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { RotaVersao } from './rota-versao.model'
import { Rota } from './rota.model'
import { RotaSessao } from './rota-sessao.model'
import { RotaAtributo } from './rota-atributo.model'

@Injectable()
export class RotaService {

    private rotasUrl = '/api/rotas';


    constructor(private http: HttpClient) { }

        /**
         * Método assíncrono que faz a chamada ao servidor para recuperar a lista de Rotas
         * cadastradas.
         * @returns Promise que irá prover a lista de Rotas ao ser resolvida
         *
         */
    async recuperarRotas() : Promise<Rota[]> {
        return this.http.get<Rota[]>(this.rotasUrl).toPromise();
    }


        /**
         * Método assíncrono que faz a chamada ao servidor para recuperar o objeto RotaVersao
         * correspondente à Rota de id rotaID, em sua versão mais atual.
         * @param rotaID: ID da rota a ser buscada
         * @returns Promise que irá prover o objeto RotaVersao ao ser resolvida
         *
         */
    async recuperarVersaoAtual(rotaID : number) : Promise<RotaVersao> {
        return this.http.get<RotaVersao>(this.rotasUrl + "/" + rotaID).toPromise();
    }

        /**
         * Método que, dado um objeto RotaVersao, constroi um dicionario (atributoID, obrigatorio) que indica 
         * se o atributo é obrigatório.
         * @param versao: Objeto RotaVersao atual
         * @returns dicionario chaveado pelo ID de cada atributo, cujo valor é true se este é obrigatório
         */
    criarDicionarioObrigatorios(versao : RotaVersao) : Map<number, boolean> {
        let obrigatoriedadeDict = new Map<number, boolean>();
        for (let sessaoPai of versao.sessoes) {
            for (let sessaoFilha of sessaoPai.sessoesFilhas) {
                for (let atributoResposta of sessaoFilha.atributos) {
                    obrigatoriedadeDict.set(atributoResposta.atributo.id, atributoResposta.obrigatorio);
                }
            }

            for (let atributoResposta of sessaoPai.atributos) {
                    obrigatoriedadeDict.set(atributoResposta.atributo.id, atributoResposta.obrigatorio);
            }
        }
        return obrigatoriedadeDict;
    }

        /**
         * Método que ordena as sessões e atributos da rota pelo campo "ordem" destes.
         * Não retorna nada, ordenação in-place
         * @param RotaVersao com as sessões e atributos com o campo "ordem" preenchido
         */
    ordenarRota(versao : RotaVersao) {
        versao.sessoes.sort((sessao1, sessao2) => sessao1.ordem - sessao2.ordem);
        for(let sessao of versao.sessoes) {
            this.ordenarSessao(sessao);
        }
    }

        /**
         * Método privado auxiliar para ordenar cada sessão. Percorre sessões aninhadas.
         * Ordena os atributos e a lista de sessões filhas.
         * Não retorna nada, ordenação in-place.
         */
    private ordenarSessao(sessao : RotaSessao) {
        sessao.sessoesFilhas.sort((sessao1, sessao2) => sessao1.ordem - sessao2.ordem);

        for(let filha of sessao.sessoesFilhas) {
            this.ordenarSessao(filha);
        }

        sessao.atributos.sort((atributo1, atributo2) => atributo1.ordem - atributo2.ordem);
    }


}
