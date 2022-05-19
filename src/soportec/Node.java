/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soportec;

/**
 *
 * @author CMast
 */
public class Node {

    long dpi;
    String nombre_completo;
    String tipo_soporte;
    Node next;

    Node(long d, String n, String p) {
        dpi = d;
        nombre_completo = n;
        tipo_soporte = p;
        next = null;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getTipo_soporte() {
        return tipo_soporte;
    }

    public void setTipo_soporte(String tipo_soporte) {
        this.tipo_soporte = tipo_soporte;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    
    
}
