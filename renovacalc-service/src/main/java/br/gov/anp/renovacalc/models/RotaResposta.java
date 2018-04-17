/*
 *  ANP - Agência Nacional de Petróleo, Gás Natural e Biocombustíveis
 *  ----------------------------------------------------------------
 *          Projeto: renovacalc-service
 *  Data de criação: 17/04/2018
 *          Arquivo: RotaResposta
 *            Autor: IBICT - Instituto Brasileiro de Informação em Ciência e Tecnologia
 *  ----------------------------------------------------------------
 *
 */

package br.gov.anp.renovacalc.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe que simboliza a submissao de um produtor.
 * Possui todos os dados referente à usina correspondente ao envio.
 * Os valores de cada item da simulacao estao na classe RotaRespostaAtributo.
 */
@Entity
@Table(name = "ROTA_RESPOSTA")
public class RotaResposta {

    private long id;

    private String nomeUsina;

    private String endereco;

    private String enderecoNumero;

    private String enderecoComplemento;

    private String enderecoBairro;

    private String enderecoCEP;

    private String nomeContato;

    private String telefoneContato;

    private String emailContato;

    private boolean ativo;

    private Timestamp dataEnvio;

    private Set<RotaRespostaAtributo> respostas = new HashSet<>();


    public RotaResposta() { }

    public RotaResposta(String nomeUsina, String endereco, String enderecoNumero, String enderecoComplemento,
            String enderecoBairro, String enderecoCEP, String nomeContato, String telefoneContato, String emailContato,
            boolean ativo, Timestamp dataEnvio) {
        this.nomeUsina = nomeUsina;
        this.endereco = endereco;
        this.enderecoNumero = enderecoNumero;
        this.enderecoComplemento = enderecoComplemento;
        this.enderecoBairro = enderecoBairro;
        this.enderecoCEP = enderecoCEP;
        this.nomeContato = nomeContato;
        this.telefoneContato = telefoneContato;
        this.emailContato = emailContato;
        this.ativo = ativo;
        this.dataEnvio = dataEnvio;
    }

    @Id
    @Column(name = "SEQ_ROTA_RESPOSTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceResposta")
    @SequenceGenerator(name = "sequenceResposta", sequenceName = "SICO_RESPOSTA", allocationSize = 1)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "NOM_USINA")
    public String getNomeUsina() { return nomeUsina; }
    public void setNomeUsina(String nomeUsina) { this.nomeUsina = nomeUsina; }

    @Column(name = "DSC_ENDERECO")
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Column(name = "DSC_ENDERECO_NUMERO")
    public String getEnderecoNumero() { return enderecoNumero; }
    public void setEnderecoNumero(String enderecoNumero) { this.enderecoNumero = enderecoNumero; }

    @Column(name = "DSC_ENDERECO_COMPLEMENTO")
    public String getEnderecoComplemento() { return enderecoComplemento; }
    public void setEnderecoComplemento(String enderecoComplemento) { this.enderecoComplemento = enderecoComplemento; }

    @Column(name = "DSC_ENDERECO_BAIRRO")
    public String getEnderecoBairro() { return enderecoBairro; }
    public void setEnderecoBairro(String enderecoBairro) { this.enderecoBairro = enderecoBairro; }

    @Column(name = "DSC_ENDERECO_CEP")
    public String getEnderecoCEP() { return enderecoCEP; }
    public void setEnderecoCEP(String enderecoCEP) { this.enderecoCEP = enderecoCEP; }

    @Column(name = "NOM_CONTATO")
    public String getNomeContato() { return nomeContato; }
    public void setNomeContato(String nomeContato) { this.nomeContato = nomeContato; }

    @Column(name = "DSC_CONTATO_TELEFONE")
    public String getTelefoneContato() { return telefoneContato; }
    public void setTelefoneContato(String telefoneContato) { this.telefoneContato = telefoneContato; }

    @Column(name = "DSC_CONTATO_EMAIL")
    public String getEmailContato() { return emailContato; }
    public void setEmailContato(String emailContato) { this.emailContato = emailContato; }

    @Column(name = "IND_ATIVO")
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Column(name = "DHA_ENVIO")
    public Timestamp getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(Timestamp dataEnvio) { this.dataEnvio = dataEnvio; }

    @OneToMany(mappedBy = "resposta", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    public Set<RotaRespostaAtributo> getRespostas() { return respostas; }
    public void setRespostas(Set<RotaRespostaAtributo> respostas) { this.respostas = respostas; }
}
