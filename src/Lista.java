public class Lista {
    private Nodo cabeza;

    public Lista() {
        cabeza = null;
    }

    public void agregar(Nodo trazo) {
        if (trazo != null) {
            if (cabeza == null) {
                cabeza = trazo;
            } else {
                // ubicar el último nodo
                Nodo apuntador = cabeza;
                while (apuntador.siguiente != null) {
                    apuntador = apuntador.siguiente;
                }
                // unir el nuevo nodo
                apuntador.siguiente = trazo;
            }
            trazo.siguiente = null;
        }
    }

    public void mostrar() {
        Nodo apuntador = cabeza;
        int indice = 0;
        while (apuntador != null) {
            apuntador = apuntador.siguiente;
            indice++;
        }
    }
}
