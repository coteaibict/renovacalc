import { RotaResposta } from './rota-resposta.model';
import { RotaAtributo } from '../rotas/rota-atributo.model';

type RespostaAtributoID = { respostaID : number, atributoId : number }

export class RotaAtributoResposta {

    constructor(
        public id : RespostaAtributoID,
        public valor : String,
        public avaliacaoANP : boolean,
        public observacaoNaoConformidade : String,
        public resposta : RotaResposta,
        public atributo : RotaAtributo
    ) { }
}
