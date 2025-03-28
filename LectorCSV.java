/*Se importa java.util que es dónde está contenida la clase BufferedReader para leer el CSV. */
 import java.io.*;
 import java.util.*;
 public class LectorCSV {
    private String direccion = "inventario_ropa_deportiva_30.csv";
    private BinarySearchTree<String, Producto> bstSKU;
    private BinarySearchTree<String, Producto> bstNombre;
 
    public LectorCSV(){
        this.bstSKU = new BinarySearchTree<>();
        this.bstNombre = new BinarySearchTree<>();
    }

    /*Método que leer el archivo CSV, dentro de una lista va colocando en el indice 0 el SKU, en el 1 el nombre, en el 2 la descripción
     * y en el 3 manda a llamar a parsearTallas() para colocar su resultado.
     */
 
    public void cargarCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(direccion))) {
            String linea;
            boolean primeraLinea = true;
    
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { 
                    primeraLinea = false; // Saltar la primera línea (encabezados)
                    continue;
                }
    
                String[] partes = linea.split(",");
                if (partes.length < 4) continue;
    
                String sku = partes[0];
                String nombre = partes[1];
                String descripcion = partes[2];
                Map<String, Integer> tallas = parsearTallas(partes[3]);
    
                Producto producto = new Producto(sku, nombre, descripcion, tallas);
                agregarProducto(producto);
            }
            System.out.println("Datos cargados desde el CSV.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV.");
            e.printStackTrace();
        }
    }

    /*A continuación vienen todos los métodos necesarios para editar el inventario. */

    /** Agrega un producto a los BST */
    public void agregarProducto(Producto producto) {
        bstSKU.insert(producto.getSku(), producto);
        bstNombre.insert(producto.getNombre(), producto);
        guardarCSV();
    }

    /** Busca un producto por SKU */
    public Producto buscarPorSKU(String sku) {
        return bstSKU.search(sku);
    }

    /** Busca un producto por Nombre */
    public Producto buscarPorNombre(String nombre) {
        return bstNombre.search(nombre);
    }

    /** Lista los productos ordenados por SKU */
    public List<Producto> listarPorSKU() {
        return bstSKU.inOrderTraversal();
    }

    /** Lista los productos ordenados por Nombre */
    public List<Producto> listarPorNombre() {
        return bstNombre.inOrderTraversal();
    }

    /*Este método se encarga de que los cambios realizados en la ejecución del programa se vean reflejados en el csv. Esto utilizando
     * una instancia de BufferedWriter que primero escribe los encabezados y luego hace un constructor de producto basandose en la lista
     * dónde están guardados.
     */

    public void guardarCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(direccion))) {
            // Escribir encabezados
            bw.write("SKU,Nombre,Descripción,Cantidad por talla");
            bw.newLine();
    
            // Obtener lista ordenada por SKU
            List<Producto> productos = listarPorSKU();
            for (Producto p : productos) {
                bw.write(p.getSku() + "," + p.getNombre() + "," + p.getDescripcion() + "," + formatearTallas(p.getTallas()));
                bw.newLine();
            }
            System.out.println("\nDatos guardados en el CSV.");
        } catch (IOException e) {
            System.out.println("\nError al guardar en el archivo CSV.");
            e.printStackTrace();
        }
    }
    
    /* Método que hace posible leer esa forma rara de escribir tallas. Este devuelve un hashmap dónde se encuentra la talla y la cantidad
     * de esa talla disponibles. Lo  que hace es separar cada vez que encuentra un '|' para el nombre de talla. Y separa cada ':' para
     * la cantidad. Luego las une dentro del mapa.
     */

    private Map<String, Integer> parsearTallas(String tallasStr) {
        Map<String, Integer> mapaTallas = new HashMap<>();
        String[] tallasSeparadas = tallasStr.split("\\|");

        for (String tallaCantidad : tallasSeparadas) {
            String[] partes = tallaCantidad.split(":");
            if (partes.length == 2) {
                mapaTallas.put(partes[0], Integer.parseInt(partes[1]));
            }
        }
        return mapaTallas;
    }

    /*Método que se utiliza en GuardarCSV, lo que hace es pasar el formato {tallas} a "tallas" para poder ser guardado en el CSV. */
    private String formatearTallas(Map<String, Integer> tallas) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : tallas.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("|");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }
    
 }  