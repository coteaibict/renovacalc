import { Injectable } from '@angular/core';
import { RotaVersao } from './rota-versao.model'
import { RotaSessao } from './rota-sessao.model'
import { RotaSessaoAtributo } from './rota-sessao-atributo.model'
import { RotaAtributo } from './rota-atributo.model'

@Injectable()
export class RotaService {

    constructor() { }

    getVersaoAtual() : RotaVersao {
        let sessoes = [new RotaSessao(1, "sessao 1", 1, null, [new RotaSessaoAtributo("0", 1, true, true, true, false, null, 
                            new RotaAtributo(1, "dado1", "asdf", "m", "ATR1", 1, 1, "", {id: 1, descricao: "real"},  []) )]), 
                       new RotaSessao(2, "sessao 2", 2, null, [new RotaSessaoAtributo("0", 1, true, true, true, false, null, 
                            new RotaAtributo(2, "dado2", "asdf", "m", "ATR2", 1, 1, "", {id: 2, descricao: "real"}, []))])]
        return new RotaVersao(1, 1, null, null, sessoes);
    }


}
