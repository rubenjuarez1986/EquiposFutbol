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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ruben
 */
public class XML_DOM {

    Document doc;

    public int abrir_XML_DOM(File fichero) {
        doc = null;

        try {
            //Se crea un objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Indica que el modelo DOM no debe contemplar los comentarios que tenga el XML
            factory.setIgnoringComments(true);
            //Ignora los espacios en blanco que tenga el objeto
            factory.setIgnoringElementContentWhitespace(true);
            //Se crea un objeto DocumentBuilder para cargar en él la estructura de árbol DOM a partir del XML seleccionado
            DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse(fichero);

            return 0;
        } catch (Exception e) {
            return -1;
        }

    }

    String recorrer_DOM_y_mostrar() {
        String salida = "";
        Node node;
        String datos_nodo[] = null;
        //Obtiene el primer nodo del DOM(primer hijo)
        Node raiz = doc.getFirstChild();
        //Obtiene una lista de nodos con todos los nodos hijos de raiz
        NodeList nodeList = raiz.getChildNodes();
        //procesa los nodos hijo
        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Es un nodo equipo
                datos_nodo = procesarEquipos(node);
                salida += "\r\n " + "El estadio es: " + datos_nodo[0];
                salida += "\r\n " + "El nombre del club es: " + datos_nodo[1];
                salida += "\r\n " + "La ciudad es: " + datos_nodo[2];
                salida += "\r\n " + "Su fundación fue en: " + datos_nodo[3];
                salida += "\r\n " + "La posición en la tabla es: " + datos_nodo[4];
                salida += "\r\n " + "La posición en la tabla es: " + datos_nodo[5];
                salida += "\r\n " + "La posición en el campo es: " + datos_nodo[6];
                salida += "\r\n " + "El nombre del jugador es: " + datos_nodo[7];
                salida += "\r\n " + "La nacionalidad es: " + datos_nodo[8];
                salida += "\r\n " + "La liga en la que juega es: " + datos_nodo[9];
                salida += "\r\n --------------------";
            }
        }
        return salida;
    }

    public String[] procesarEquipos(Node n) {
        String datos[] = new String[10];
        Node ntemp = null;
        Node ntemp2 = null;
        int contador = 1;
        //Obtiene el valor del primer atributo del nodo
        datos[0] = n.getAttributes().item(0).getNodeValue();
        //Obtengo el valor del segundo atributo
        datos[contador] = n.getAttributes().item(1).getNodeValue();
        contador++;

        //Obtiene los hijos del equipo
        NodeList nodos = n.getChildNodes();

        for (int i = 0; i < nodos.getLength(); i++) {

            ntemp = nodos.item(i);
            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                //Detecta si el elemento tiene hijos
                if (ntemp.getFirstChild().getNodeValue().trim().equals("")) {
                    //Sacare el atributo de jugador franquicia
                    datos[contador] = ntemp.getAttributes().item(0).getNodeValue();
                    contador++;
                    //Saco los hijos de jugador franquicia
                    NodeList nodos2 = ntemp.getChildNodes();
                    //hago otro bucle for para que me de los valores de los elementos hijos
                    for (int j = 0; j < nodos2.getLength(); j++) {
                        ntemp2 = nodos2.item(j);
                        if (ntemp2.getNodeType() == Node.ELEMENT_NODE) {
                            datos[contador] = ntemp2.getFirstChild().getNodeValue();
                            contador++;
                        }
                    }
                } else {//Saco el resto de elementos que no tienen hijos
                    datos[contador] = ntemp.getFirstChild().getNodeValue();
                    contador++;
                }
            }
        }
        return datos;
    }

    public int annadirDom(String estadio, String presidente, String nombre, String ciudad, String fundacion, String posicion_tabla,
            String posicion, String nombre_jugador, String nacionalidad, String liga) {

        int error = 0;
        if (!estadio.isEmpty()
                && !presidente.isEmpty()
                && !nombre.isEmpty()
                && !ciudad.isEmpty()
                && !fundacion.isEmpty()
                && !posicion_tabla.isEmpty()
                && !posicion.isEmpty()
                && !nombre_jugador.isEmpty()
                && !nacionalidad.isEmpty()
                && !liga.isEmpty()) {
            //se crea un nodo tipo Element con nombre(<Nombre>)
            Node nnombre = doc.createElement("nombre");
            //Se crea un nodo tipo texto con el titulo del nombre
            Node nnombre_text = doc.createTextNode(nombre);
            //Se añade el nodo de texto con el nombre como hijo del elemento nombre
            nnombre.appendChild(nnombre_text);

            //Se hace lo mismo que con nombre a ciudad(<Ciudad>)
            Node nciudad = doc.createElement("ciudad");
            Node nciudad_text = doc.createTextNode(ciudad);
            nciudad.appendChild(nciudad_text);

            Node nfundacion = doc.createElement("fundacion");
            Node nfundacion_text = doc.createTextNode(fundacion);
            nfundacion.appendChild(nfundacion_text);

            Node nposicion_tabla = doc.createElement("posicion_tabla");
            Node nposicion_tabla_text = doc.createTextNode(posicion_tabla);
            nposicion_tabla.appendChild(nposicion_tabla_text);

            //Se crea un nodo de tipo elemento (<equipo>)
            Node nequipo = doc.createElement("equipo");
            //Al nuevo nodo libro se le añade un atributo publicado_en
            ((Element) nequipo).setAttribute("presidente", presidente);
            ((Element) nequipo).setAttribute("estadio", estadio);

            Node njugador_franquicia = doc.createElement("jugador_franquicia");
            ((Element) njugador_franquicia).setAttribute("posicion", posicion);

            Node nnombre_jugador = doc.createElement("nombre_jugador");
            Node nnombre_jugador_text = doc.createTextNode(ciudad);
            nnombre_jugador.appendChild(nnombre_jugador_text);
            njugador_franquicia.appendChild(nnombre_jugador);

            Node nnacionalidad = doc.createElement("nacionalidad");
            Node nnacionalidad_text = doc.createTextNode(nacionalidad);
            nnacionalidad.appendChild(nnacionalidad_text);
            njugador_franquicia.appendChild(nnacionalidad);

            Node nliga = doc.createElement("liga");
            Node nliga_text = doc.createTextNode(liga);
            nliga.appendChild(nliga_text);
            njugador_franquicia.appendChild(nliga);

            //Se añade a equipo los nodos nombre, ciudad, fundacion, posicion_tabla, jugador_franquicia creados antes
            nequipo.appendChild(nnombre);
            nequipo.appendChild(nciudad);
            nequipo.appendChild(nfundacion);
            nequipo.appendChild(nposicion_tabla);
            nequipo.appendChild(njugador_franquicia);

            //Añado todos los elementos a la raiz
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nequipo);
        } else {
            error = -1;
        }
        return error;
    }

    public boolean guardarDOM(File archivo_xml) {

        try {
            OutputFormat format = new OutputFormat(doc);
            format.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);
            serializer.serialize(doc);

            return true;

        } catch (Exception e) {

            return false;

        }

    }
}
