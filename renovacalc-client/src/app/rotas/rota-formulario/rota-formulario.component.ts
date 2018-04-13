import { Component, OnInit } from '@angular/core'
import { RotaVersao } from '../rota-versao.model'
import { RotaService } from '../rota.service'

@Component({
    selector: 'app-rota-formulario',
    templateUrl: './rota-formulario.component.html',
    styleUrls: ['./rota-formulario.component.css']
})
export class RotaFormularioComponent implements OnInit {

    versao: RotaVersao;

    constructor(private rotaService: RotaService) { }

    ngOnInit() {
        this.versao = this.rotaService.getVersaoAtual();
    }

}
