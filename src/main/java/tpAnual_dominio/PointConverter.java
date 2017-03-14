package tpAnual_dominio;



import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.uqbar.geodds.Point;

@Converter

public class PointConverter implements AttributeConverter<Point, String>{
	
	 private static final String SEPARADOR = ",";

	 
	 @Override
	 public String convertToDatabaseColumn(Point punto) {
	  StringBuilder constructorDeString = new StringBuilder();
	  
	  constructorDeString.append(punto.longitude()).append(SEPARADOR)
	     .append(punto.latitude());
	  return constructorDeString.toString();
	 }

	 @Override
	 public Point convertToEntityAttribute(String coordenadas) {
	  String[] punto = coordenadas.split(SEPARADOR);
	  return new Point(Double.parseDouble(punto[0]), 
	      Double.parseDouble(punto[1]));
	 }

}
