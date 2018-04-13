type AtributoItem = { id: number, descricao: string, atributo: RotaAtributo };
type AtributoTipo = { id: number, descricao: string };

export class RotaAtributo {

    constructor(
        public id: number,
        public nome: string,
        public descricao: string,
        public unidadeMedida: string,
        public tag: string,
        public tamanho: number,
        public precisao: number,
        public formula: string,
        public tipo: AtributoTipo,
        public items: AtributoItem[]
    ) { }
}
