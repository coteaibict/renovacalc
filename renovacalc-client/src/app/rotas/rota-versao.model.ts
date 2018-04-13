import { Rota } from './rota.model'
import { RotaSessao } from './rota-sessao.model'

type SituacaoVersao = { id: string, descricao: string };

export class RotaVersao {

    constructor(
        public id: number,
        public numVersao: number,
        public rota: Rota,
        public situacao: SituacaoVersao,
        public sessoes : RotaSessao[]
    ) { }
}
