import { RotaResposta } from './rota-resposta.model';
import { RotaAtributo } from '../rotas/rota-atributo.model';

type RespostaAtributoID = { respostaID : number, atributoID : number }

export class RotaAtributoResposta {

    constructor(
        public id : RespostaAtributoID,
        public valor : string,
        public avaliacaoANP : boolean,
        public observacaoNaoConformidade : string,
        public resposta : RotaResposta,
        public atributo : RotaAtributo
    ) { }
}
