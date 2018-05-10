import { RotaSessao } from './rota-sessao.model'
import { RotaAtributo } from './rota-atributo.model'

type SessaoAtributoID = { sessaoId : number, atributoId : number }

export class RotaSessaoAtributo {

    public id : SessaoAtributoID;
    constructor(
        public valorPadrao: string,
        public ordem: number,
        public obrigatorio: boolean,
        public visivel: boolean,
        public editavel: boolean,
        public avaliado: boolean,
        public sessao : RotaSessao,
        public atributo : RotaAtributo,
    ) {
        this.id = { sessaoId : 0, atributoId : 0};
        this.id.sessaoId = sessao != null ? sessao.id : 0 ;
        this.id.atributoId = atributo.id;
    }

}
