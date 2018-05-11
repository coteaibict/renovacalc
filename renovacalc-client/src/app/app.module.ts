import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';

import { RotaService } from './rotas/rota.service';
import { RespostaService } from './resposta/resposta.service';
import { RotasComponent } from './rotas/rotas.component';
import { RotaFormularioComponent } from './rotas/rota-formulario/rota-formulario.component';
import { PaginaNaoEncontradaComponent } from './pagina-nao-encontrada/pagina-nao-encontrada.component';

const appRoutes : Routes = [
    { path: 'rotas', component: RotasComponent },
    { path: 'rotas/:id', component: RotaFormularioComponent },
    { path: '', redirectTo: '/rotas', pathMatch: 'full' },
    { path: '**', component: PaginaNaoEncontradaComponent },
];

@NgModule({
    declarations: [
        AppComponent,
        RotaFormularioComponent,
        RotasComponent,
        PaginaNaoEncontradaComponent,
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        RouterModule.forRoot(appRoutes),
        NgbModule.forRoot(),
        FormsModule
    ],
    providers: [RotaService, RespostaService],
    bootstrap: [AppComponent]
})
export class AppModule { }
