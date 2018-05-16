import { RotaVersao } from './rota-versao.model'
import { RotaSessaoAtributo } from './rota-sessao-atributo.model'
export class RotaSessao {

    constructor(
        public id: number,
        public descricao: String,
        public ordem: number,
        public nivel: number,
        public resultado: boolean,
        public rotaVersao: RotaVersao,
        public superior: RotaSessao,
        public sessoesFilhas: RotaSessao[],
        public atributos: RotaSessaoAtributo[]
    ) { }
}
