package com.renjamio.controller;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.renjamio.almundo.dispatcher.Dispatcher;
import com.renjamio.almundo.model.Llamada;
import com.renjamio.exception.ExcepcionDispatch;

public class HiloLlamada extends Thread {

	private static final AtomicInteger count = new AtomicInteger(0);
	private final static Logger LOGGER = Logger.getLogger("HiloLlamada");

	private int nombre;
	private int duración;
	private Dispatcher dispatcher;
	private Llamada llamada;

	public HiloLlamada(int duración, Dispatcher dispatcher, Llamada llamada) {
		this.nombre = count.incrementAndGet();
		this.duración = duración * 1000;
		this.dispatcher = dispatcher;
		this.llamada = llamada;
	}

	@Override
	public void run() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Soy el hilo " + this.nombre + " y he iniciado mi ejecución.");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		try {
			getDispatcher().dispatchCall(llamada);
			Thread.sleep(this.duración);
			getDispatcher().unDispatchCall(llamada);
		} catch (InterruptedException e) {
			LOGGER.log(null, "Error en run", e);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(null, "Error en run", e);
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Soy el hilo " + this.nombre + " y he finalizado mi ejecución.");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
