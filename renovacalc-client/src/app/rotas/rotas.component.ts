import { Component, OnInit } from '@angular/core';
import { Rota } from './rota.model';
import { RotaService } from './rota.service';

@Component({
    selector: 'app-rotas',
    templateUrl: './rotas.component.html',
    styleUrls: ['./rotas.component.css']
})
export class RotasComponent implements OnInit {

    rotas : Rota[];

    selecionada : Rota;

    constructor(private rotaService: RotaService) { }

    ngOnInit() {
        this.rotaService.recuperarRotas().then(rotas => this.rotas = rotas);

         // this.rotas = [ {id: 1, nome: "Etanol"}, {id: 2, nome: "Biodísel" }]
        this.selecionada = { id: 0, nome: "Diretório de rotas de produção de biocombustíveis"}
    }

}
