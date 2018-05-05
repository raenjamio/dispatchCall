package com.renjamio.almundo.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Empleado {
	
	private static final AtomicInteger count = new AtomicInteger(0); 
	
	private long id = 0;
	private TipoEmpleado tipoEmpleado;
	private Llamada llamadaAsignada;
	private String nombre;
	private String apellido;
	
	

	public Empleado(TipoEmpleado tipoEmpleado) {
		super();
		this.tipoEmpleado = tipoEmpleado;
	}

	public Empleado(TipoEmpleado tipoEmpleado, String nombre, String apellido) {
		super();
		this.tipoEmpleado = tipoEmpleado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = count.incrementAndGet();
	}

	public TipoEmpleado getTipoEmpleado() {
		return tipoEmpleado;
	}

	public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	public Llamada getLlamadaAsignada() {
		return llamadaAsignada;
	}

	public void setLlamadaAsignada(Llamada llamadaAsignada) {
		this.llamadaAsignada = llamadaAsignada;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", tipoEmpleado=" + tipoEmpleado + ", nombre=" + nombre + ", apellido=" + apellido
				+ "]";
	}

	
}
