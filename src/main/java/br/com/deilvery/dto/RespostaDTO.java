package br.com.deilvery.dto;

public class RespostaDTO {
	private Double valorTotal;
	private Long pedido;
	private String mensagem;

	public RespostaDTO(Long pedido, Double valorTotal, String mensagem) {
		this.pedido = pedido;
		this.mensagem = mensagem;
		this.valorTotal = valorTotal;
	}
	public RespostaDTO() {	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getPedido() {
		return pedido;
	}

	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
