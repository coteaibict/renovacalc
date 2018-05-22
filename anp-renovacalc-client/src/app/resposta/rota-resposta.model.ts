import { RotaVersao } from '../rotas/rota-versao.model';
import { RotaAtributoResposta } from './rota-atributo-resposta.model';

export class RotaResposta {

    constructor(
        public id : number,
        public nomeUsina : string,
        public endereco : string,
        public enderecoNumero : string,
        public enderecoComplemento : string,
        public enderecoBairro : string,
        public enderecoCEP : string,
        public nomeContato : string,
        public telefoneContato : string,
        public emailContato : string,
        public ativo : boolean,
        public avaliacaoANP : string,
        public observacaoNaoConformidade : string,
        public dataEnvio : Date,
        public versao : RotaVersao,
        public respostas : RotaAtributoResposta[]
    ) { }
}
