package soportec;

public class LinkedList {

    Node head;

    public static LinkedList insert(LinkedList list, long dpi, String nombre, String tipo_soporte) {
        Node new_node = new Node(dpi, nombre, tipo_soporte);
        new_node.next = null;

        if (list.head == null) {
            list.head = new_node;
        } else {
            Node last = list.head;

            while (last.next != null && last.next.nombre_completo.compareTo(new_node.nombre_completo) <= 0) {
                last = last.next;
            }

            if (list.head.nombre_completo.compareTo(new_node.nombre_completo) > 0) {
                new_node.next = last;
                list.head = new_node;
                return list;
            } else if (last.next != null && last.next.nombre_completo.compareTo(new_node.nombre_completo) > 0) {
                new_node.next = last.next;
            }
            last.next = new_node;

        }

        return list;
    }
    
    public static LinkedList delete(LinkedList list, long dpi) {
        Node last = list.head;
        if (last.dpi == dpi) {
            list.head = last.next;
            return list;
        }
        
        while (last.next != null && last.next.dpi != dpi) {
            last = last.next;
        }
        
        last.next = last.next.next;
        
        return list;
    }
    
    public static LinkedList update(LinkedList list, long dpi, String new_nombre, String new_tipo_sporte) {
        Node last = list.head;
        
        if (last.dpi == dpi) {
            list = delete(list, last.dpi);
            list = insert(list, last.dpi, new_nombre, new_tipo_sporte);
            return list;
        }
        
        while (last.next != null && last.next.dpi != dpi) {
            last = last.next;
        }
        
        list = delete(list, last.next.dpi);
        list = insert(list, last.next.dpi, new_nombre, new_tipo_sporte);
        
        return list;
    }

}
