package com.renjamio.almundo.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Llamada {
	
	private static final AtomicInteger count = new AtomicInteger(0);
	
	private long duracion;
	private long id;
	private Empleado empleadoAsignado;
	
	

	public Llamada() {
		super();
		this.id =count.incrementAndGet();
	}

	public long getDuracion() {
		return duracion;
	}

	public void setDuracion(long duracion) {
		this.duracion = duracion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}

	public void setEmpleadoAsignado(Empleado empleadoAsignado) {
		this.empleadoAsignado = empleadoAsignado;
	}

	@Override
	public String toString() {
		return "Llamada [duracion=" + duracion + ", id=" + id + ", empleadoAsignado=" + empleadoAsignado + "]";
	}
	
	

}
