import java.util.Map;

public class Producto {
    public String sku;
    public String nombre;
    public String descripcion;
    public Map<String, Integer> tallas;

    public Producto(String sku, String nombre, String descripcion, Map<String, Integer> tallas){
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tallas = tallas;
    }
}