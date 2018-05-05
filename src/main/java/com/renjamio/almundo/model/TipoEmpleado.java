package com.renjamio.almundo.model;

public abstract class TipoEmpleado {
	
	private long id;
	private String codigo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "codigo=" + codigo + "]";
	}

	
	
}
