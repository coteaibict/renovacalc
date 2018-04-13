import { RotaVersao } from './rota-versao.model'
import { RotaSessaoAtributo } from './rota-sessao-atributo.model'
export class RotaSessao {

    constructor(
        public id: number,
        public descricao: String,
        public ordem: number,
        public rotaVersao: RotaVersao,
        public atributos: RotaSessaoAtributo[]
    ) { }
}
