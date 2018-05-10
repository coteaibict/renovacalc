import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { RotaVersao } from './rota-versao.model'
import { Rota } from './rota.model'
import { RotaSessao } from './rota-sessao.model'
import { RotaAtributo } from './rota-atributo.model'
import { RotaSessaoAtributo } from './rota-sessao-atributo.model'
import { Observable } from 'rxjs/Observable'
import { of } from 'rxjs/observable/of';
import { tap, map } from 'rxjs/operators';

@Injectable()
export class RotaService {

    private rotasUrl = '/api/rotas';


    constructor(private http: HttpClient) { }

    recuperarRotas() : Observable<Rota[]> {
        return this.http.get<Rota[]>(this.rotasUrl);
    }


    recuperarVersaoAtual(rotaID : number) : Observable<RotaVersao> {
        return this.http.get<RotaVersao>(this.rotasUrl + "/" + rotaID);
    }


}
