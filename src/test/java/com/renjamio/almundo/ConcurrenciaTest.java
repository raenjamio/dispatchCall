package com.renjamio.almundo;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.renjamio.almundo.dispatcher.Dispatcher;
import com.renjamio.almundo.model.Director;
import com.renjamio.almundo.model.Empleado;
import com.renjamio.almundo.model.Llamada;
import com.renjamio.almundo.model.Operador;
import com.renjamio.almundo.model.Supervisor;
import com.renjamio.almundo.model.TipoEmpleado;
import com.renjamio.controller.HiloLlamada;
import com.renjamio.exception.ExcepcionDispatch;

import junit.framework.Assert;
import junit.framework.TestCase;

@RunWith(ConcurrentTestRunner.class)
public class ConcurrenciaTest extends TestCase {

	private Dispatcher dispatcher;

	private final static int THREAD_COUNT = 10;

	@Before
	public void inicializar() {
		Empleado empleadoOperador1 = new Empleado(new Operador(), "dddd", "asdf");// Mockito.mock(Empleado.class);
		Empleado empleadoOperador2 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador3 = Mockito.mock(Empleado.class);

		Empleado empleadoOperador4 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador5 = Mockito.mock(Empleado.class);

		TipoEmpleado tipoOperador = Mockito.mock(Operador.class);
		TipoEmpleado tipoSupervisor = Mockito.mock(Supervisor.class);
		TipoEmpleado tipoDirector = Mockito.mock(Director.class);

		// Mockito.when(empleadoOperador1.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador2.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador3.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador4.getTipoEmpleado()).thenReturn(tipoSupervisor);
		Mockito.when(empleadoOperador5.getTipoEmpleado()).thenReturn(tipoDirector);

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.agregarEmpleadoDisponible(empleadoOperador1);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador2);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador3);
		// dispatcher.agregarEmpleadoDisponible(empleadoOperador4);
		// dispatcher.agregarEmpleadoDisponible(empleadoOperador5);

		setDispatcher(dispatcher);
	}

	@Test(timeout = 10000)
	@ThreadCount(THREAD_COUNT)
	public void testConcurrencia() {
		Llamada llamada = new Llamada();
		// llamada.setDuracion(5000);
		// dispatcher.dispatchCall(new Llamada());
		HiloLlamada tarea1 = new HiloLlamada(10, getDispatcher(), llamada);
		tarea1.start();

	}

	/**
	 * Testamos que cuando hay operadores disponibles solo asigne operadores a
	 * la llamada
	 */
	public void TestDispatchOk() {

		Empleado empleadoOperador1 = new Empleado(new Operador(), "dddd", "asdf");// Mockito.mock(Empleado.class);
		Empleado empleadoOperador2 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador3 = Mockito.mock(Empleado.class);

		Empleado empleadoOperador4 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador5 = Mockito.mock(Empleado.class);

		TipoEmpleado tipoOperador = Mockito.mock(Operador.class);
		TipoEmpleado tipoSupervisor = Mockito.mock(Supervisor.class);
		TipoEmpleado tipoDirector = Mockito.mock(Director.class);

		// Mockito.when(empleadoOperador1.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador2.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador3.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador4.getTipoEmpleado()).thenReturn(tipoSupervisor);
		Mockito.when(empleadoOperador5.getTipoEmpleado()).thenReturn(tipoDirector);

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.agregarEmpleadoDisponible(empleadoOperador1);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador2);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador3);
		// dispatcher.agregarEmpleadoDisponible(empleadoOperador4);
		// dispatcher.agregarEmpleadoDisponible(empleadoOperador5);

		List<HiloLlamada> hilos = buildHilosLlamadas(3, dispatcher);

		int i = 0;
		HiloLlamada hilo = new HiloLlamada(5 + i, dispatcher, new Llamada());
		hilo.start();
		/*
		 * for (HiloLlamada hiloLlamada : hilos) { hiloLlamada.start();
		 * //assertTrue(dispatcher.getOperadoresDisponibles().size() ==
		 * hilos.size() - i - 1); }
		 */
		// assertTrue(dispatcher.getOperadoresDisponibles().size() == 0);
		// assertTrue(dispatcher.getDirectoresDisponibles().size() == 1);
		// assertTrue(dispatcher.getSupervisoresDisponibles().size() == 1);

	}

	public void TestDispatchOk2() {

		Empleado empleadoOperador1 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador2 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador3 = Mockito.mock(Empleado.class);

		Empleado empleadoOperador4 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador5 = Mockito.mock(Empleado.class);

		TipoEmpleado tipoOperador = Mockito.mock(Operador.class);
		TipoEmpleado tipoSupervisor = Mockito.mock(Supervisor.class);
		TipoEmpleado tipoDirector = Mockito.mock(Director.class);

		Mockito.when(empleadoOperador1.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador2.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador3.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador4.getTipoEmpleado()).thenReturn(tipoSupervisor);
		Mockito.when(empleadoOperador5.getTipoEmpleado()).thenReturn(tipoDirector);

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.agregarEmpleadoDisponible(empleadoOperador1);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador2);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador3);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador4);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador5);

		List<HiloLlamada> hilos = buildHilosLlamadas(3, dispatcher);

		for (HiloLlamada hiloLlamada : hilos) {
			hiloLlamada.start();
			// assertTrue(dispatcher.getOperadoresDisponibles().size() ==
			// hilos.size() - i - 1);
		}
		// assertTrue(dispatcher.getOperadoresDisponibles().size() == 0);
		// assertTrue(dispatcher.getDirectoresDisponibles().size() == 1);
		// assertTrue(dispatcher.getSupervisoresDisponibles().size() == 1);

	}

	private List<HiloLlamada> buildHilosLlamadas(int cantLlamadas, Dispatcher dispatcher) {
		List<Llamada> llamadasAux = new ArrayList<Llamada>();
		List<HiloLlamada> hilosAux = new ArrayList<HiloLlamada>();

		for (int i = 0; i < cantLlamadas; i++) {
			Llamada llamada = new Llamada();
			llamadasAux.add(llamada);
		}

		for (int i = 0; i < cantLlamadas; i++) {
			HiloLlamada hilo = new HiloLlamada(5 + i, dispatcher, llamadasAux.get(i));
			hilosAux.add(hilo);
		}

		return hilosAux;
	}

	private Dispatcher builDispatcher() {
		Empleado empleadoOperador1 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador2 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador3 = Mockito.mock(Empleado.class);

		Empleado empleadoOperador4 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador5 = Mockito.mock(Empleado.class);

		TipoEmpleado tipoOperador = Mockito.mock(Operador.class);
		TipoEmpleado tipoSupervisor = Mockito.mock(Supervisor.class);
		TipoEmpleado tipoDirector = Mockito.mock(Director.class);

		Mockito.when(empleadoOperador1.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador2.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador3.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador4.getTipoEmpleado()).thenReturn(tipoSupervisor);
		Mockito.when(empleadoOperador5.getTipoEmpleado()).thenReturn(tipoDirector);

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.agregarEmpleadoDisponible(empleadoOperador1);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador2);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador3);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador4);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador5);
		return dispatcher;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
