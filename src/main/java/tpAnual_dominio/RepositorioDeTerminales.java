package tpAnual_dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uqbar.geodds.Polygon;

public class RepositorioDeTerminales {
	
	private List<Terminal> terminales;

	public RepositorioDeTerminales() {
		super();
		terminales = new ArrayList<Terminal>();
	}

	public void agregarTerminal(Terminal nuevaTerminal) {
		terminales.add(nuevaTerminal);
	}
	
	public void eliminarTerminal(Terminal terminalABorrar) {
		terminales.remove(terminalABorrar);
	}
	
	public Map<String, Long> obtenerCantidadResultadosPorTerminal() {
		Map<String, Long> registro = new HashMap<String, Long>();
		terminales.forEach(terminal -> terminal.registrarCantidadResultados(registro));
		return registro;
	}
	
	public Map<String, List<Long>> obtenerCantidadResultadosParcialesPorTerminal() {
		Map<String, List<Long>> registro = new HashMap<String, List<Long>>();
		terminales.forEach(terminal -> terminal.registrarResultadosParciales(registro));
		return registro;
	}
	
	public Map<LocalDate, Long> obtenerCantidadBusquedasPorFecha() {
		Map<LocalDate, Long> registro = new HashMap<LocalDate,Long>();
		terminales.stream().forEach(terminal -> terminal.registrarCantidadBusquedasPorFecha(registro));
		return registro;
	}

	public List<Terminal> obtenerTerminalesDentroDe(Polygon comuna) {
		return terminales.stream().filter(terminal -> terminal.estaDentroDe(comuna)).collect(Collectors.toList());
	}

	public List<Terminal> obtenerTerminales() {
		return terminales;
	}

}