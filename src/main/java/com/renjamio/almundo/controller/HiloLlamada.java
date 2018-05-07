package com.renjamio.almundo.controller;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.renjamio.almundo.dispatcher.Dispatcher;
import com.renjamio.almundo.exception.ExcepcionDispatch;
import com.renjamio.almundo.model.Llamada;

public class HiloLlamada extends Thread {

	private static final AtomicInteger count = new AtomicInteger(0);
	private final static Logger LOGGER = Logger.getLogger("HiloLlamada");

	private int nombre;
	private int duracion;
	private Dispatcher dispatcher;
	private Llamada llamada;

	public HiloLlamada(int duración, Dispatcher dispatcher, Llamada llamada) {
		this.nombre = count.incrementAndGet();
		this.duracion = duración * 1000;
		this.dispatcher = dispatcher;
		this.llamada = llamada;
		this.llamada.setDuracion(this.duracion);
	}

	@Override
	public void run() {
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info("Soy el hilo " + this.nombre + " y he iniciado mi ejecución.");
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		try {
			getDispatcher().dispatchCall(llamada);
			Thread.sleep(this.duracion);
			getDispatcher().unDispatchCall(llamada);
		} catch (InterruptedException e) {
			LOGGER.log(null, "Error en run", e);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(null, "Error en run", e);
		}
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info("Soy el hilo " + this.nombre + " y he finalizado mi ejecución.");
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
