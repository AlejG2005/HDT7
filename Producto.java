import java.util.Map;

/* Clase que representa un producto en el programa. Contiene su código SKU, nombre, descripción y las tallas con la cantidad disponible. */

public class Producto {
    public String sku;
    public String nombre;
    public String descripcion;
    public Map<String, Integer> tallas;

    //Constructor de un objeto producto.

    public Producto(String sku, String nombre, String descripcion, Map<String, Integer> tallas){
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tallas = tallas;
    }

    // Getters

    public String getSku() { return sku; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Map<String, Integer> getTallas() { return tallas; }

    // Setters
    
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setTallas(Map<String, Integer> tallas) { this.tallas = tallas; }

    // Método para actualizar una cantidad específica de una talla
    public void actualizarCantidadTalla(String talla, int cantidad) {
        if (tallas.containsKey(talla)) {
            tallas.put(talla, cantidad);
        }
    }
}