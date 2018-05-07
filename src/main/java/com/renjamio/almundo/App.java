package com.renjamio.almundo;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.renjamio.almundo.controller.HiloLlamada;
import com.renjamio.almundo.dispatcher.Dispatcher;
import com.renjamio.almundo.exception.ExcepcionDispatch;
import com.renjamio.almundo.model.Director;
import com.renjamio.almundo.model.Empleado;
import com.renjamio.almundo.model.Llamada;
import com.renjamio.almundo.model.Operador;
import com.renjamio.almundo.model.Supervisor;
/**
 * @author raenjamio
 * 
 */
public class App {
	private final static Logger LOGGER = Logger.getLogger("App");

	public static void main(String[] args) {
		LOGGER.info("Comienza la ejecución concurrente de 12 llamadas");
		
		Llamada llamada1 = new Llamada();
		Llamada llamada2 = new Llamada();
		Llamada llamada3 = new Llamada();
		Llamada llamada4 = new Llamada();
		Llamada llamada5 = new Llamada();
		Llamada llamada6 = new Llamada();
		Llamada llamada7 = new Llamada();
		Llamada llamada8 = new Llamada();
		Llamada llamada9 = new Llamada();
		Llamada llamada10 = new Llamada();
		Llamada llamada11 = new Llamada();
		Llamada llamada12 = new Llamada();
		
		Dispatcher dispatcher = buildDispatcher();

		HiloLlamada tarea1 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada1);
		HiloLlamada tarea2 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada2);
		HiloLlamada tarea3 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada3);
		HiloLlamada tarea4 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada4);
		HiloLlamada tarea5 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada5);
		HiloLlamada tarea6 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada6);
		HiloLlamada tarea7 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada7);
		HiloLlamada tarea8 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada8);
		HiloLlamada tarea9 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada9);
		HiloLlamada tarea10 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada10);
		HiloLlamada tarea11 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada11);
		HiloLlamada tarea12 = new HiloLlamada(getTiempoLlamada(), dispatcher, llamada12);

		tarea1.start();
		tarea2.start();
		tarea3.start();
		tarea4.start();
		tarea5.start();
		tarea6.start();
		// TimeUnit.SECONDS.sleep(3);
		tarea7.start();
		tarea8.start();
		tarea9.start();
		tarea10.start();
		tarea11.start();
		tarea12.start();

		LOGGER.info("Finaliza App");

	}
	
	public static Dispatcher buildDispatcher() {
		Empleado empleadoOperador1 = new Empleado(new Operador(), "juan", "perez");
		Empleado empleadoOperador2 = new Empleado(new Operador(), "carlos", "sanchez");
		Empleado empleadoOperador3 = new Empleado(new Operador(), "juan3", "perez");
		Empleado empleadoOperador4 = new Empleado(new Operador(), "carlos4", "sanchez");
		Empleado empleadoOperador5 = new Empleado(new Operador(), "juan5", "perez");
		Empleado empleadoOperador6 = new Empleado(new Operador(), "carlos6", "sanchez");
		Empleado empleadoOperador7 = new Empleado(new Operador(), "carlo77", "sanchez");
		Empleado empleadoOperador8 = new Empleado(new Operador(), "carlo77", "sanchez");
		Empleado empleadoSupervisor1 = new Empleado(new Supervisor(), "maria", "sanchez");
		Empleado empleadoSupervisor2 = new Empleado(new Supervisor(), "lorena", "ortega");
		Empleado empleadoDirector1 = new Empleado(new Director(), "juana", "ortega");


		Dispatcher dispatcher = new Dispatcher();
		dispatcher.agregarEmpleadoDisponible(empleadoOperador1);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador2);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador3);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador4);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador5);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador6);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador7);
		dispatcher.agregarEmpleadoDisponible(empleadoOperador8);
		dispatcher.agregarEmpleadoDisponible(empleadoSupervisor1);
		dispatcher.agregarEmpleadoDisponible(empleadoSupervisor2);
		dispatcher.agregarEmpleadoDisponible(empleadoDirector1);
		
		return dispatcher;
	}

	public static int getTiempoLlamada() {
		Random aleatorio = new Random();
		return aleatorio.nextInt(10 - 5 + 1) + 5;
	}
}
