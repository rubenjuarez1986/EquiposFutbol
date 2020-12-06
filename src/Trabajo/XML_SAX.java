/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabajo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Ruben
 */
public class XML_SAX {

    SAXParser parser;
    ManejadorSAX sh;

    public int abrir_XML_SAX(File fichero) {
        try {
            //Se crea un objeto SAXParser para interpretar el documento XML.
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();

            //Se crea una instancia del manejador que será el que recorra el documento XML secuencialmente
            sh = new ManejadorSAX();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    String recorrerSAX(File fichero) {
        try {
            sh.cadena_resultado = "";
            parser.parse(fichero, sh);
            return sh.cadena_resultado;
        } catch (SAXException ex) {
            return "Error al parsear con SAX";
        } catch (IOException ex) {
            return "Error al parsear con SAX";
        }
    }

}

class ManejadorSAX extends DefaultHandler {

    String cadena_resultado = "";

//Cuando se detecta una cadena de texto guarda ese texto en la variable de salida
    @Override

    public void characters(char[] ch, int start, int length) throws SAXException {

        for (int i = start; i < length + start; i++) {
            cadena_resultado = cadena_resultado + ch[i];
        }
        cadena_resultado = cadena_resultado.trim() + "\n";

    }

//Cuando ese detaceta el final de un elemento <libro> se pone una line discontinua en la salida.
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("equipo")) {
            cadena_resultado = cadena_resultado + "------------------------\n";
        }
    }

    @Override // detecta el principio de los elementos del xml y al encontrar uno de los elementos puestos en el xml pone en el String cadenaResultado lo que quieres
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //me voy a la raiz <Equipos> para poder mostrar el texto y utilizo el string.format para darle valor a ese objeto <equipos de futbol>
        if (qName.equals("equipos_de_futbol")) {
            cadena_resultado = String.format(" Se van a mostrar los datos de este equipo de futbol \n-------------------------------------\n", cadena_resultado);
        } //aqui hago lo mismo que arriba pero ahora con <equipo> y lo que hago es colocarle el texto y luego los --- antes de cada Publicado en:
        // con el %s lo que hago es pasarle una cadena de caracteres que termina en '\0'
        if (qName.equals("equipo")) {
            cadena_resultado = String.format("%s El estadio es: %s", cadena_resultado, attributes.getValue(attributes.getQName(0).trim()));
            cadena_resultado = String.format("%s El presidente es: %s", cadena_resultado, attributes.getValue(attributes.getQName(0).trim()));

        } else if (qName.equals("nombre")) {
            cadena_resultado = cadena_resultado + "El nombre del club es: ".trim();
        } else if (qName.equals("ciudad")) {
            cadena_resultado = cadena_resultado + "La ciudad es: ".trim();
        } else if (qName.equals("fundacion")) {
            cadena_resultado = cadena_resultado + "La fundacion es: ".trim();
        } else if (qName.equals("posicion_tabla")) {
            cadena_resultado = cadena_resultado + "La posicion en la tabla es: ".trim();
        }
        if (qName.equals("jugador_franquicia")) {
            cadena_resultado = String.format("%s Su posicion es: %s", cadena_resultado, attributes.getValue(attributes.getQName(0).trim()));
        } else if (qName.equals("nombre_jugador")) {
            cadena_resultado = cadena_resultado + "El nombre es: ".trim();
        } else if (qName.equals("nacionalidad")) {
            cadena_resultado = cadena_resultado + "La nacionalidad es: ".trim();
        } else if (qName.equals("liga")) {
            cadena_resultado = cadena_resultado + "La liga es: ".trim();
        }
    }
}
