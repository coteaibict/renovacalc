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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Classe que simboliza a submissao de um produtor.
 * Possui todos os dados referente à usina correspondente ao envio.
 * Os valores de cada item da simulacao estao na classe RotaAtributoResposta.
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

    private boolean avaliacaoANP;

    private String observacaoNaoConformidade;

    private Timestamp dataEnvio;

    private RotaUsina usina;

    private RotaVersao versao;

    private Set<RotaAtributoResposta> respostas = new HashSet<>();


    public RotaResposta() { }

    public RotaResposta(String nomeUsina, String endereco, String enderecoNumero, String enderecoComplemento,
            String enderecoBairro, String enderecoCEP, String nomeContato, String telefoneContato, String emailContato,
            boolean ativo, Timestamp dataEnvio, RotaUsina usina, RotaVersao versao) {
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
        this.usina = usina;
        this.versao = versao;
    }


    /**
     * Método para adicionar um novo item de resposta, correspondendo ao preenchimento de um atributo
     * @param respostaAtributo item a ser adicionado
     */
    public void adicionarRespostaAtributo(RotaAtributoResposta respostaAtributo) {
        respostaAtributo.setResposta(this);
        respostas.add(respostaAtributo);
    }

    // Getters/Setters

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

    @Column(name = "IND_AVALIACAO_ANP")
    public boolean isAvaliacaoANP() { return avaliacaoANP; }
    public void setAvaliacaoANP(boolean avaliacaoANP) { this.avaliacaoANP = avaliacaoANP; }

    @Column(name = "DSC_OBSERVACAO_NAO_CONFORMIDADE")
    public String getObservacaoNaoConformidade() { return observacaoNaoConformidade; }
    public void setObservacaoNaoConformidade(String observacaoNaoConformidade) { this.observacaoNaoConformidade = observacaoNaoConformidade; }

    @Column(name = "DHA_ENVIO")
    public Timestamp getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(Timestamp dataEnvio) { this.dataEnvio = dataEnvio; }

    // TODO: Trocar nullable para false quando autenticação for feita
    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA_USINA", nullable = true)
    public RotaUsina getUsina() { return usina; }
    public void setUsina(RotaUsina usina) { this.usina = usina; }

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "SEQ_ROTA_VERSAO", nullable = false)
    public RotaVersao getVersao() { return versao; }
    public void setVersao(RotaVersao versao) { this.versao = versao; }

    @OneToMany(mappedBy = "resposta", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    public Set<RotaAtributoResposta> getRespostas() { return respostas; }
    public void setRespostas(Set<RotaAtributoResposta> respostas) { this.respostas = respostas; }

    // Equals/Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RotaResposta that = (RotaResposta) o;
        return getId() == that.getId() && isAtivo() == that.isAtivo() && Objects
                .equals(getNomeUsina(), that.getNomeUsina()) && Objects.equals(getEndereco(), that.getEndereco())
                && Objects.equals(getEnderecoNumero(), that.getEnderecoNumero()) && Objects
                .equals(getEnderecoComplemento(), that.getEnderecoComplemento()) && Objects
                .equals(getEnderecoBairro(), that.getEnderecoBairro()) && Objects
                .equals(getEnderecoCEP(), that.getEnderecoCEP()) && Objects
                .equals(getNomeContato(), that.getNomeContato()) && Objects
                .equals(getTelefoneContato(), that.getTelefoneContato()) && Objects
                .equals(getEmailContato(), that.getEmailContato()) && Objects
                .equals(getDataEnvio(), that.getDataEnvio()) &&
                ((RotaResposta) o).getUsina().getId() == that.getUsina().getId() &&
                ((RotaResposta) o).getVersao().getId() == that.getVersao().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomeUsina(), getEndereco(), getEnderecoNumero(), getEnderecoComplemento(),
                getEnderecoBairro(), getEnderecoCEP(), getNomeContato(), getTelefoneContato(), getEmailContato(),
                isAtivo(), getDataEnvio(), getUsina().getId(), getVersao().getId());
    }
}
