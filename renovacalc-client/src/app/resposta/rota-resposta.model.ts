import { RotaVersao } from '../rotas/rota-versao.model';
import { RotaAtributoResposta } from './rota-atributo-resposta.model';

export class RotaResposta {

    constructor(
        public id : number,
        public nomeUsina : String,
        public endereco : String,
        public enderecoNumero : String,
        public enderecoComplemento : String,
        public enderecoBairro : String,
        public enderecoCEP : String,
        public nomeContato : String,
        public telefoneContato : String,
        public emailContato : String,
        public ativo : boolean,
        public avaliacaoANP : boolean,
        public observacaoNaoConformidade : String,
        public dataEnvio : Date,
        public versao : RotaVersao,
        public respostas : RotaAtributoResposta[]
    ) { }
}
