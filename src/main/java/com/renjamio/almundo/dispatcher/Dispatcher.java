package com.renjamio.almundo.dispatcher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import com.renjamio.almundo.exception.ExcepcionDispatch;
import com.renjamio.almundo.model.Empleado;
import com.renjamio.almundo.model.Llamada;
import com.renjamio.almundo.model.Operador;
import com.renjamio.almundo.model.Supervisor;

/**
 * @author raenjamio
 * Clase que encapsula la asignacion y desasignacion de llamadas a empleados
 */
public class Dispatcher {

	private final static Logger LOGGER = Logger.getLogger("Dispatcher");

	private List<Empleado> empleadosDisponibles;
	
	private List<Empleado> empleadosOcupados;
	private List<Empleado> operadoresDisponibles;
	private List<Empleado> supervisoresDisponibles;
	private List<Empleado> directoresDisponibles;
	private Queue<Llamada> llamadasEnEspera;
	private int cantLlamadasEnCurso = 0;


	public final int CANT_LLAMADAS_CONCURRENTES = 10;

	/**
	 * Instantiates a new dispatcher.
	 */
	public Dispatcher() {
		super();
		operadoresDisponibles = new ArrayList<Empleado>();
		supervisoresDisponibles = new ArrayList<Empleado>();
		directoresDisponibles = new ArrayList<Empleado>();
		setLlamadasEnEspera(new LinkedList<Llamada>());
		setCantLlamadasEnCurso(0);

	}

	/**
	 * @author raenjamio
	 *
	 * Metodo que asigna una llamada a un empleado, si hay disponible, sino duerme al hilo
	 * 
	 * @param llamada
	 * @throws ExcepcionDispatch 
	 * @throws InterruptedException
	 */
	public synchronized void dispatchCall(Llamada llamada) throws ExcepcionDispatch, InterruptedException {
		LOGGER.info("**** dispatchCall *****");
		Empleado empleadoLibre = getEmpleadoDisponible();
		if (empleadoLibre == null || getCantLlamadasEnCurso() >= CANT_LLAMADAS_CONCURRENTES) {
			LOGGER.info("+++++++Llamada Encolada e hilo esperando+++++ ");
			encolarLlamada(llamada);
			wait();
			empleadoLibre = getEmpleadoDisponible();
		}
		asignarLlamadaAEmpleado(llamada, empleadoLibre);
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info("Empleado asignado " + empleadoLibre + " asignado a llamada " + llamada);
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

	/**
	 * @author raenjamio
	 * 
	 * Pone una llamada en la cola de llamadas en espera
	 *
	 * @param llamada
	 */
	private void encolarLlamada(Llamada llamada) {
		getLlamadasEnEspera().add(llamada);
	}

	/**
	 * @author raenjamio
	 * 
	 * Saca una llamada de la cola de espera
	 * 
	 * @param llamada
	 * @return llamada
	 */
	private Llamada desencolarLlamada(Llamada llamada) {
		if (getLlamadasEnEspera().isEmpty()) {
			return null;
		}
		return getLlamadasEnEspera().poll();
	}

	/**
	 * @author raenjamio
	 * Metodo que desasigna una llamada del empleado, y notifica a los hilos que hay empleados disponibles
	 *
	 * @param llamada
	 * @throws ExcepcionDispatch 
	 */
	public synchronized void unDispatchCall(Llamada llamada) throws ExcepcionDispatch {
		desasignarEmpleadoALlamada(llamada);
		notify();
	}

	/**
	 * @author raenjamio
	 * 
	 * Desasignar empleado A llamada.
	 *
	 * @param llamada the llamada
	 */
	private void desasignarEmpleadoALlamada(Llamada llamada) {
		agregarEmpleadoDisponible(llamada.getEmpleadoAsignado());
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info("Empleado desasignado " + llamada.getEmpleadoAsignado() + " asignado a llamada " + llamada);
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		llamada.setEmpleadoAsignado(null);
		setCantLlamadasEnCurso(getCantLlamadasEnCurso() - 1);
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info("LLAMADAS EN CURSO " + getCantLlamadasEnCurso());
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	/**
	 * @author raenjamio
	 * Asignar llamada A empleado.
	 *
	 * @param llamada llamada
	 * @param Empleado empleadoLibre
	 * @throws ExcepcionDispatch 
	 */
	private void asignarLlamadaAEmpleado(Llamada llamada, Empleado empleadoLibre) throws ExcepcionDispatch {
		empleadoLibre.setLlamadaAsignada(llamada);
		llamada.setEmpleadoAsignado(empleadoLibre);
		eliminarDeDisponibles(empleadoLibre);
		setCantLlamadasEnCurso(getCantLlamadasEnCurso() + 1);
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info("LLAMADAS EN CURSO " + getCantLlamadasEnCurso());
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	/**
	 * @author raenjamio
	 * Eliminar de disponibles.
	 *
	 * @param empleadoLibre 
	 * @throws ExcepcionDispatch 
	 */
	private void eliminarDeDisponibles(Empleado empleadoLibre) throws ExcepcionDispatch {
		if (empleadoLibre.getTipoEmpleado() instanceof Operador) {
			operadoresDisponibles.remove(empleadoLibre);
		} else if (empleadoLibre.getTipoEmpleado() instanceof Supervisor) {
			supervisoresDisponibles.remove(empleadoLibre);
		} else {
			directoresDisponibles.remove(empleadoLibre);
		}

	}

	/**
	 * @author raenjamio
	 * Devuelve el empleado disponible.
	 *
	 * @return the empleado disponible
	 */
	private Empleado getEmpleadoDisponible() {
		if (!operadoresDisponibles.isEmpty()) {
			return operadoresDisponibles.get(0);
		}
		if (!supervisoresDisponibles.isEmpty()) {
			return supervisoresDisponibles.get(0);
		}
		if (!directoresDisponibles.isEmpty()) {
			return directoresDisponibles.get(0);
		}
		return null;
	}

	/**
	 * @author raenjamio
	 * Agregar empleado disponible.
	 *
	 * @param empleado
	 */
	public void agregarEmpleadoDisponible(Empleado empleado) {
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.info(" agregarEmpleadoDisponible" + empleado);
		LOGGER.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		if (empleado.getTipoEmpleado() instanceof Operador) {
			operadoresDisponibles.add(empleado);
		} else if (empleado.getTipoEmpleado() instanceof Supervisor) {
			supervisoresDisponibles.add(empleado);
		} else {
			directoresDisponibles.add(empleado);

		}
	}

	/**
	 * Gets the empleados disponibles.
	 *
	 * @return the empleados disponibles
	 */
	public List<Empleado> getEmpleadosDisponibles() {
		return empleadosDisponibles;
	}

	/**
	 * Sets the empleados disponibles.
	 *
	 * @param empleadosDisponibles the new empleados disponibles
	 */
	public void setEmpleadosDisponibles(List<Empleado> empleadosDisponibles) {
		this.empleadosDisponibles = empleadosDisponibles;
	}

	/**
	 * Gets the empleados ocupados.
	 *
	 * @return the empleados ocupados
	 */
	public List<Empleado> getEmpleadosOcupados() {
		return empleadosOcupados;
	}

	/**
	 * Sets the empleados ocupados.
	 *
	 * @param empleadosOcupados the new empleados ocupados
	 */
	public void setEmpleadosOcupados(List<Empleado> empleadosOcupados) {
		this.empleadosOcupados = empleadosOcupados;
	}

	/**
	 * Gets the operadores disponibles.
	 *
	 * @return the operadores disponibles
	 */
	public List<Empleado> getOperadoresDisponibles() {
		return operadoresDisponibles;
	}

	/**
	 * Sets the operadores disponibles.
	 *
	 * @param operadoresDisponibles the new operadores disponibles
	 */
	public void setOperadoresDisponibles(List<Empleado> operadoresDisponibles) {
		this.operadoresDisponibles = operadoresDisponibles;
	}

	/**
	 * Gets the supervisores disponibles.
	 *
	 * @return the supervisores disponibles
	 */
	public List<Empleado> getSupervisoresDisponibles() {
		return supervisoresDisponibles;
	}

	/**
	 * Sets the supervisores disponibles.
	 *
	 * @param supervisoresDisponibles the new supervisores disponibles
	 */
	public void setSupervisoresDisponibles(List<Empleado> supervisoresDisponibles) {
		this.supervisoresDisponibles = supervisoresDisponibles;
	}

	/**
	 * Gets the directores disponibles.
	 *
	 * @return the directores disponibles
	 */
	public List<Empleado> getDirectoresDisponibles() {
		return directoresDisponibles;
	}

	/**
	 * Sets the directores disponibles.
	 *
	 * @param directoresDisponibles the new directores disponibles
	 */
	public void setDirectoresDisponibles(List<Empleado> directoresDisponibles) {
		this.directoresDisponibles = directoresDisponibles;
	}

	/**
	 * Gets the llamadas en espera.
	 *
	 * @return the llamadas en espera
	 */
	public Queue<Llamada> getLlamadasEnEspera() {
		return llamadasEnEspera;
	}

	/**
	 * Sets the llamadas en espera.
	 *
	 * @param llamadasEnEspera the new llamadas en espera
	 */
	public void setLlamadasEnEspera(Queue<Llamada> llamadasEnEspera) {
		this.llamadasEnEspera = llamadasEnEspera;
	}

	/**
	 * Gets the cant llamadas en curso.
	 *
	 * @return the cant llamadas en curso
	 */
	public int getCantLlamadasEnCurso() {
		return cantLlamadasEnCurso;
	}

	/**
	 * Sets the cant llamadas en curso.
	 *
	 * @param cantLlamadasEnCurso the new cant llamadas en curso
	 */
	public void setCantLlamadasEnCurso(int cantLlamadasEnCurso) {
		this.cantLlamadasEnCurso = cantLlamadasEnCurso;
	}

}
