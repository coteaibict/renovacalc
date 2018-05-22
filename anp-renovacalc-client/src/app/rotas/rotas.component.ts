import { Component, OnInit } from '@angular/core';
import { Rota } from './rota.model';
import { RotaService } from './rota.service';

@Component({
    selector: 'app-rotas',
    templateUrl: './rotas.component.html',
    styleUrls: ['./rotas.component.css']
})
export class RotasComponent implements OnInit {

    /**
     * Lista de rotas recuperadas do Service
     *
     */
    rotas : Rota[];

    /**
     * Rota selecionada pelo usuário. Inicia com uma rota dummy com ID zero.
     *
     */
    selecionada : Rota = { id: 0, nome: "Diretório de rotas de produção de biocombustíveis"};

    constructor(private rotaService: RotaService) { }

    ngOnInit() {
        this.rotaService.recuperarRotas().then(rotas => this.rotas = rotas);
    }

}
