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
import com.renjamio.almundo.controller.HiloLlamada;
import com.renjamio.almundo.dispatcher.Dispatcher;
import com.renjamio.almundo.exception.ExcepcionDispatch;
import com.renjamio.almundo.model.Director;
import com.renjamio.almundo.model.Empleado;
import com.renjamio.almundo.model.Llamada;
import com.renjamio.almundo.model.Operador;
import com.renjamio.almundo.model.Supervisor;
import com.renjamio.almundo.model.TipoEmpleado;

import junit.framework.Assert;
import junit.framework.TestCase;

@RunWith(ConcurrentTestRunner.class)
public class ConcurrenciaTest3 extends TestCase {

	private Dispatcher dispatcher;

	private final static int THREAD_COUNT = 10;

	@Before
	public void inicializar() {
		Empleado empleadoOperador1 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador2 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador3 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador4 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador5 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador6 = Mockito.mock(Empleado.class);
		Empleado empleadoOperador7 = Mockito.mock(Empleado.class);
		Empleado empleadoSupervisor1 = Mockito.mock(Empleado.class);
		Empleado empleadoSupervisor2 = Mockito.mock(Empleado.class);
		Empleado empleadoDirector = Mockito.mock(Empleado.class);

		TipoEmpleado tipoOperador = Mockito.mock(Operador.class);
		TipoEmpleado tipoSupervisor = Mockito.mock(Supervisor.class);
		TipoEmpleado tipoDirector = Mockito.mock(Director.class);

		Mockito.when(empleadoOperador1.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador2.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador3.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador4.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador5.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador6.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoOperador7.getTipoEmpleado()).thenReturn(tipoOperador);
		Mockito.when(empleadoSupervisor1.getTipoEmpleado()).thenReturn(tipoSupervisor);
		Mockito.when(empleadoSupervisor2.getTipoEmpleado()).thenReturn(tipoSupervisor);
		Mockito.when(empleadoDirector.getTipoEmpleado()).thenReturn(tipoDirector);
		
		

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.agregarEmpleadoDisponible(empleadoOperador1);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador2);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador3);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador4);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador5);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador6);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador7);
		dispatcher.agregarEmpleadoDisponible(empleadoSupervisor1);
		dispatcher.agregarEmpleadoDisponible(empleadoSupervisor2);
		dispatcher.agregarEmpleadoDisponible(empleadoDirector);

		setDispatcher(dispatcher);
	}

	/**
	 * @author raenjamio
	 * Test donde para verificar que si la cantidad de llamadas e hilos es igual que la cantidad de empleados disponibles
	 * no hay ninguna llamada en espera
	 */

	@Test
	@ThreadCount(THREAD_COUNT)
	public void testIgualHilosQueEmpleados() {
		HiloLlamada hilo = new HiloLlamada(5, dispatcher, new Llamada());
		hilo.run();
		assertTrue(dispatcher.getCantLlamadasEnCurso() <= 10);
		assertTrue(dispatcher.getLlamadasEnEspera().size() == 0);
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
