/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabajo;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javafutbol.EquiposDeFutbol;
import javafutbol.EquiposDeFutbol.Equipo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Document;

/**
 *
 * @author Ruben
 */
public class XML_JAXB {

    public static Document doc;
    Unmarshaller u;

    EquiposDeFutbol miFutbol;

    public int abrir_XML_JAXB(File ficheroXML) {
        try {
            //crea una instancia JAXB
            JAXBContext contexto = JAXBContext.newInstance(EquiposDeFutbol.class);
            //Crea un objeto Unmarshaller.
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (Unmarshaller) el fichero
            miFutbol = (EquiposDeFutbol) u.unmarshal(ficheroXML);
            return 0;
        } catch (Exception e) {
            return -1;
        }

    }

    public int guardar_XML_JAXB() {
        try {
            //Crea un fichero llamado nuevoFichero.xml
            File archivo_xml = new File("equipos_futbol.xml");
            //Creo una instancia JAXB
            JAXBContext contexto = JAXBContext.newInstance(EquiposDeFutbol.class);
            //crear objeto marshaller
            Marshaller marshaller = contexto.createMarshaller();
            //Hago que me coja todo el formato de letras(tildes,ñ...)
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //Añadir formato XML
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Guarda la estructura de objetos
            marshaller.marshal(miFutbol, archivo_xml);
            return 0;
        } catch (Exception e) {
            return -1;
        }

    }

    public String recorrerJAXB() {
        String cadena_resultado = "";

        List<EquiposDeFutbol.Equipo> lEquipo = miFutbol.getEquipo();

        for (int i = 0; i < lEquipo.size(); i++) {
            Equipo equipo_temp = lEquipo.get(i);
            cadena_resultado = cadena_resultado + "\nEstadio: " + equipo_temp.getEstadio();
            cadena_resultado = cadena_resultado + "\nPresidente: " + equipo_temp.getPresidente();
            cadena_resultado = cadena_resultado + "\nNombre del club es: " + equipo_temp.getNombre();
            cadena_resultado = cadena_resultado + "\nCiudad: " + equipo_temp.getCiudad();
            cadena_resultado = cadena_resultado + "\nFundacion: " + equipo_temp.getFundacion();
            cadena_resultado = cadena_resultado + "\nPosicionTabla: " + equipo_temp.getPosicionTabla();
            cadena_resultado = cadena_resultado + "\nJugadorFranquicia: ";
            cadena_resultado = cadena_resultado + "\nNombre jugador: " + equipo_temp.getJugadorFranquicia().getNombre();
            cadena_resultado = cadena_resultado + "\nNacionalidad del jugador: " + equipo_temp.getJugadorFranquicia().getNacionalidad();
            cadena_resultado = cadena_resultado + "\nLiga a la que pertenece el jugador: " + equipo_temp.getJugadorFranquicia().getLiga();
            cadena_resultado = cadena_resultado + "\n--------------------";
        }
        return cadena_resultado;
    }

    public int modificarPosicionTabla(String posicionAntigua, String posicionNueva) {
        int error = 0;
        if (!posicionAntigua.isEmpty() && !posicionNueva.isEmpty()) {
            List<EquiposDeFutbol.Equipo> lEquipo = miFutbol.getEquipo();
            //recorro la lista de quipos 
            for (int i = 0; i < lEquipo.size(); i++) {
                lEquipo.get(i);
                //Aquí le digo que la posicionAntigua es la posicion que le marco
                if (posicionAntigua.equals(lEquipo.get(i).getPosicionTabla())) {
                    //Aquí me cambia esa posicion que he pasado anteiormente por la posición nueva que le paso yo
                    lEquipo.get(i).setPosicionTabla(posicionNueva);
                }
            }
        } else {
            error = -1;
        }
        return error;
    }

}
