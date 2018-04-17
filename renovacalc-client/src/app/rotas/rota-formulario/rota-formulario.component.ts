import { Component, OnInit } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs/Observable'

import { Rota } from '../rota.model'
import { RotaVersao } from '../rota-versao.model'
import { RotaService } from '../rota.service'

@Component({
    selector: 'app-rota-formulario',
    templateUrl: './rota-formulario.component.html',
    styleUrls: ['./rota-formulario.component.css']
})
export class RotaFormularioComponent implements OnInit {

    rota: Rota;
    versao: RotaVersao;

    constructor(private rotaService: RotaService) { }

    ngOnInit() {
        this.rota = new Rota(1, "dummy");
        this.rotaService.getVersaoAtual(this.rota).subscribe(versao => this.versao = versao);
    }

}
