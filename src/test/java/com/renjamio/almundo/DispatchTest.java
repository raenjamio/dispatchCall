package com.renjamio.almundo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mockito.Mockito;

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

public class DispatchTest extends TestCase {
	
	private final static Logger LOGGER = Logger.getLogger("DispatchTest");

	public DispatchTest(String testName) {
		super(testName);
	}

	/**
	 * @author raenjamio
	 * Testamos que asigne todas las llamadas a operadores si hay disponibles
	 * en este caso la cantidad de llamadas son 3 y operadores son 3
	 */
	public void testDispatchMismaCantLlamadasYOperadores() {
		
		
		Llamada llamada1 = Mockito.mock(Llamada.class);
		Llamada llamada2 = Mockito.mock(Llamada.class);
		Llamada llamada3 = Mockito.mock(Llamada.class);
		Dispatcher dispatcher  = builDispatcher();
		try {
			dispatcher.dispatchCall(llamada1);
			dispatcher.dispatchCall(llamada2);
			dispatcher.dispatchCall(llamada3);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(Level.SEVERE, "Error en testDispatchMismaCantLlamadasYOperadores", e);
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, "Error en testDispatchMismaCantLlamadasYOperadores", e);
		}
		Assert.assertTrue(dispatcher.getDirectoresDisponibles().size() == 1);
		Assert.assertTrue(dispatcher.getSupervisoresDisponibles().size() == 1);
		Assert.assertTrue(dispatcher.getOperadoresDisponibles().size() == 0);
		Assert.assertTrue(dispatcher.getCantLlamadasEnCurso() == 3);
		
	}
	
	/**
	 * @author raenjamio
	 * Testamos que asigne todas las llamadas a operadores y si no hay disponibles al supervisor
	 * en este caso la cantidad de llamadas son 4 y operadores son 3 y 1 supervisor
	 */
	public void testDispatchMasCantLlamadasQueOperadores() {
		
		
		Llamada llamada = Mockito.mock(Llamada.class);
		Llamada llamada1 = Mockito.mock(Llamada.class);
		Llamada llamada2 = Mockito.mock(Llamada.class);
		Llamada llamada3 = Mockito.mock(Llamada.class);
		Dispatcher dispatcher  = builDispatcher();
		try {
			dispatcher.dispatchCall(llamada);
			dispatcher.dispatchCall(llamada1);
			dispatcher.dispatchCall(llamada2);
			dispatcher.dispatchCall(llamada3);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(Level.SEVERE, "Error en testDispatchMasCantLlamadasQueOperadores", e);
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, "Error en testDispatchMasCantLlamadasQueOperadores", e);
		}
		Assert.assertTrue(dispatcher.getDirectoresDisponibles().size() == 1);
		Assert.assertTrue(dispatcher.getSupervisoresDisponibles().size() == 0);
		//debe asignar la llamada al operador
		Assert.assertTrue(dispatcher.getOperadoresDisponibles().size() == 0);
		Assert.assertTrue(dispatcher.getCantLlamadasEnCurso() == 4);
		
	}
	
	/**
	 * @author raenjamio
	 * Testamos que asigne todas las llamadas a operadores y si no hay disponibles al supervisor y si no hay disponible al director
	 * en este caso la cantidad de llamadas son 5 y operadores son 3 y 1 supervisor y 1 director
	 */
	public void testDispatchMasCantLlamadasQueOperadoresYsupervisores() {
		
		
		Llamada llamada1 = Mockito.mock(Llamada.class);
		Llamada llamada2 = Mockito.mock(Llamada.class);
		Llamada llamada3 = Mockito.mock(Llamada.class);
		Llamada llamada4 = Mockito.mock(Llamada.class);
		Llamada llamada5 = Mockito.mock(Llamada.class);
		Dispatcher dispatcher  = builDispatcher();
		try {
			dispatcher.dispatchCall(llamada1);
			dispatcher.dispatchCall(llamada2);
			dispatcher.dispatchCall(llamada3);
			dispatcher.dispatchCall(llamada4);
			dispatcher.dispatchCall(llamada5);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(Level.SEVERE, "Error en testDispatchMasCantLlamadasQueOperadoresYsupervisores", e);
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, "Error en testDispatchMasCantLlamadasQueOperadoresYsupervisores", e);
		}
		Assert.assertTrue(dispatcher.getDirectoresDisponibles().size() == 0);
		Assert.assertTrue(dispatcher.getSupervisoresDisponibles().size() == 0);
		//debe asignar la llamada al operador
		Assert.assertTrue(dispatcher.getOperadoresDisponibles().size() == 0);
		Assert.assertTrue(dispatcher.getCantLlamadasEnCurso() == 5);
		
	}
	
	/**
	 * @author raenjamio
	 * Testamos que asigne todas las llamadas a operadores y si no hay disponibles al supervisor y si no hay disponible al director
	 * y si no hay disponible lo deje en espera, en este caso la cantidad de llamadas son 6 y operadores son 3 y 1 supervisor y 1 director
	 */
	public void TestDispatchMasCantLlamadasQueEmpleados() {
		
		
		Llamada llamada1 = Mockito.mock(Llamada.class);
		Llamada llamada2 = Mockito.mock(Llamada.class);
		Llamada llamada3 = Mockito.mock(Llamada.class);
		Llamada llamada4 = Mockito.mock(Llamada.class);
		Llamada llamada5 = Mockito.mock(Llamada.class);
		Llamada llamada6 = Mockito.mock(Llamada.class);
		Dispatcher dispatcher  = builDispatcher();
		try {
			dispatcher.dispatchCall(llamada1);
			dispatcher.dispatchCall(llamada2);
			dispatcher.dispatchCall(llamada3);
			dispatcher.dispatchCall(llamada4);
			dispatcher.dispatchCall(llamada5);
			//dispatcher.dispatchCall(llamada6);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(Level.SEVERE, "Error en TestDispatchMasCantLlamadasQueEmpleados", e);
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, "Error en TestDispatchMasCantLlamadasQueEmpleados", e);
		}
		Assert.assertTrue(dispatcher.getDirectoresDisponibles().size() == 0);
		Assert.assertTrue(dispatcher.getSupervisoresDisponibles().size() == 0);
		//debe asignar la llamada al operador
		Assert.assertTrue(dispatcher.getOperadoresDisponibles().size() == 0);
		Assert.assertTrue(dispatcher.getCantLlamadasEnCurso() == 6);
		Assert.assertTrue(dispatcher.getLlamadasEnEspera().size() == 1);
		
	}
	
	/**
	 * @author raenjamio
	 * Testamos que desasigne una llamada y ponga al empleado en la lista de disponibles
	 */
	public void testUnDispatch() {
		
		
		Llamada llamada1 = Mockito.mock(Llamada.class);
		Empleado empleadoOperador1 = Mockito.mock(Empleado.class);
		TipoEmpleado tipoOperador = Mockito.mock(Operador.class);
		Mockito.when(empleadoOperador1.getTipoEmpleado()).thenReturn(tipoOperador);
		
		Dispatcher dispatcher  = new Dispatcher();
		Mockito.when(llamada1.getEmpleadoAsignado()).thenReturn(empleadoOperador1);
		
		try {
			dispatcher.unDispatchCall(llamada1);
			//dispatcher.dispatchCall(llamada6);
		} catch (ExcepcionDispatch e) {
			LOGGER.log(Level.SEVERE, "Error en testUnDispatch", e);
		}
		Assert.assertTrue(dispatcher.getOperadoresDisponibles().size() == 1);
		Assert.assertEquals(dispatcher.getOperadoresDisponibles().get(0), empleadoOperador1);
		
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
}
