package open.digytal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import open.digytal.util.DataHora;
import open.digytal.util.Formatador;
@Entity
@Table(name="tb_lancamento")
public class Lancamento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "cd_movimento", nullable = false, length = 2)
	private TipoMovimento tipoMovimento;
	
	@ManyToOne
	@JoinColumn(name="cd_conta",nullable=false)
	private Conta conta;
	
	@ManyToOne
	@JoinColumn(name="cd_destino")
	private Conta destino;
	
	@ManyToOne
	@JoinColumn(name="cd_origem")
	private Lancamento origem;
	
	@ManyToOne
	@JoinColumn(name="cd_natureza",nullable=false)
	private Natureza natureza;
	
	@Column(nullable=false)
	private String descricao;
	
	@Column(nullable=false,length=7,precision=2)
	private Double valor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date data;
	
	@Column(length=6,nullable=false)
	private Integer periodo;
	
	@Column(nullable=false)
	private boolean previsao;
	
	@Column(nullable=false)
	private boolean transferencia;
	
	private Parcelamento parcelamento;
	
	public Lancamento() {
		this.parcelamento = new Parcelamento();
	}
	public Parcelamento getParcelamento() {
		return parcelamento;
	}
	public boolean isTransferencia() {
		return transferencia;
	}
	public void setTransferencia(boolean transferencia) {
		this.transferencia = transferencia;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Natureza getNatureza() {
		return natureza;
	}
	public void setNatureza(Natureza natureza) {
		this.natureza = natureza;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getId() {
		return id;
	}
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public boolean isPrevisao() {
		return previsao;
	}
	public void setPrevisao(boolean previsao) {
		this.previsao = previsao;
	}
	public Conta getDestino() {
		return destino;
	}
	public void setDestino(Conta destino) {
		this.destino = destino;
	}
	public Lancamento getOrigem() {
		return origem;
	}
	public Lancamento copia() {
		Lancamento copia = new Lancamento();
		copia.setTransferencia(true);
		copia.setDescricao("TRANSF.DE: " + conta.getNome() + " - " + descricao);
		copia.setTipoMovimento(TipoMovimento.C);
		copia.setPrevisao(previsao);
		copia.setConta(destino);
		copia.setData(data);
		copia.setNatureza(natureza);
		copia.setTransferencia(true);
		copia.setValor(valor);
		copia.origem=this;
		this.transferencia=true;
		this.tipoMovimento=TipoMovimento.D;
		return copia;
	}
	public Double getValorMovimento() {
		valor = tipoMovimento==TipoMovimento.D?valor * -1:valor;
		return valor;
	}
	@PrePersist
	private void periodo() {
		this.periodo = Integer.valueOf(Formatador.formatar(DataHora.ano(data),"0000") + Formatador.formatar(DataHora.mes(data),"00"));
		if(this.isPrevisao()) {
			this.parcelamento.setRestante(getValor());
		}
	}
}
