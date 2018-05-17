import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { RotaResposta } from './rota-resposta.model';
import { RotaVersao } from '../rotas/rota-versao.model';
import { RotaAtributo } from '../rotas/rota-atributo.model';
import { RotaAtributoResposta } from './rota-atributo-resposta.model';

@Injectable()
export class RespostaService {

    private rotasUrl = '/api/respostas';
    private simulacaoPrefix = '/simular';

    constructor(private http: HttpClient) { }

        /**
         * Método assíncrono que faz a chamada http ao servidor, enviando
         * o objeto RotaResposta com os inputs e retornando outro objeto 
         * RotaResposta com os valores calculados preenchidos, em uma Promise
         * @param resposta: objeto RotaResposta com os valores de input preenchidos
         * @returns Promise com o objeto RotaResposta, com os valores calculados
         * preenchidos
         */
    async calcularResposta(resposta: RotaResposta): Promise<RotaResposta> {
        return this.http.post<RotaResposta>(this.rotasUrl + this.simulacaoPrefix, resposta).toPromise();
    }

        /**
         * Método que inicializa um dicionário de atributo/valor, chaveado pelo ID do atributo,
         * com base na versão RotaVersao.
         * TODO: quando o produtor puder logar, verificar se já existe simulação salva como rascunho
         * e construir este dicionario com base nela
         * @param versao: RotaVersao usada como base para construir o dicionário inicial
         * @returns dicionário chaveado pelo ID de cada atributo e com entrada (atributo, valor)
         */
    inicializarRespostasDict(versao: RotaVersao): Map<number, [RotaAtributo, string]> {

        let respostasDict: Map<number, [RotaAtributo, string]> = new Map<number, [RotaAtributo, string]>();

        for (let sessaoPai of versao.sessoes) {
            for (let sessaoFilha of sessaoPai.sessoesFilhas) {
                for (let atributoResposta of sessaoFilha.atributos) {
                    let atributo = atributoResposta.atributo;
                    respostasDict.set(atributo.id, [atributo, ""]);
                }
            }

            for (let atributoResposta of sessaoPai.atributos) {
                let atributo = atributoResposta.atributo;
                respostasDict.set(atributo.id, [atributo, ""]);
            }
        }

        return respostasDict;
    }

        /**
         * Método que reconstrói o dicionario de atributo/valor, chaveado pelo ID do atributo, 
         * usado na view, com base em um objeto RotaResposta vindo do servidor 
         * @param resposta: objeto RotaResposta vindo do servidor. Assume que
         * todos os atributos de input tenham um correspondente, mesmo que
         * vazio
         * @returns dicionário chaveado pelo ID de cada atributo e com entrada (atributo, valor),
         * construído com base em resposta
         */
    reconstruirRespostasDict(resposta: RotaResposta): Map<number, [RotaAtributo, string]> {
        let respostasDict: Map<number, [RotaAtributo, string]> = new Map<number, [RotaAtributo, string]>();

        for (let atributoResposta of resposta.respostas) {
            respostasDict.set(atributoResposta.atributo.id, [atributoResposta.atributo, atributoResposta.valor])
        }

        return respostasDict;
    }

        /**
         * Método usado para construir um objeto RotaResposta para ser enviado ao servidor
         * Não adiciona os valores calculados no objeto
         * TODO: Quando o produtor puder logar, adicionar dados deste na resposta
         * @param respostasDict: dicionário com items do tipo (id_atributo, (atributo, valor_resposta))
         * @param versao: versão atual da rota
         * @returns o objeto RotaResposta construído, pronto para ser enviado ao servidor
         */
    reconstruirResposta(respostasDict: Map<number, [RotaAtributo, string]>, versao: RotaVersao): RotaResposta {
        let respostasList = [];

        // Por enquanto não há produtor logado: todos os campos referentes a este são vazios
        let resposta = new RotaResposta(0, "", "", "", "", "", "", "", "", "", true, "A", "", new Date(Date.now()), versao, respostasList);

        for (let [atributo, value] of Array.from(respostasDict.values())) {
            // Não adiciona os valores calculados na resposta
            if (atributo.formula == "" || atributo.formula == null ) {
                // Resposta é colocado como null para evitar loop na serialização
                respostasList.push(new RotaAtributoResposta({ respostaID: resposta.id, atributoID: atributo.id }, value, "A", "", null, atributo));
            }
        }
        return resposta;
    }

}
