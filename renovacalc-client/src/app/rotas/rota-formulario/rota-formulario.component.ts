import { Component, OnInit } from '@angular/core'
import { RotaVersao } from '../rota-versao.model'
import { Rota } from '../rota.model'
import { RotaService } from '../rota.service'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs/Observable'

@Component({
    selector: 'app-rota-formulario',
    templateUrl: './rota-formulario.component.html',
    styleUrls: ['./rota-formulario.component.css']
})
export class RotaFormularioComponent implements OnInit {

    rota: Rota;
    versao: RotaVersao;

    private rotasUrl = 'api/rotas';

    constructor(private rotaService: RotaService, private http: HttpClient) { }

    ngOnInit() {
        this.rota = new Rota(1, "dummy");
        this.rotaService.getVersaoAtual(this.rota).subscribe(versao => this.versao = versao);
    }

}
