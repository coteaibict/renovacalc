import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';


import { AppComponent } from './app.component';
import { RotaFormularioComponent } from './rotas/rota-formulario/rota-formulario.component';

import { RotaService } from './rotas/rota.service'

@NgModule({
    declarations: [
        AppComponent,
        RotaFormularioComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule
    ],
    providers: [RotaService],
    bootstrap: [AppComponent]
})
export class AppModule { }
