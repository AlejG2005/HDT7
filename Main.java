import java.util.*;

public class Main {
    private static LectorCSV lectorCSV = new LectorCSV();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        lectorCSV.cargarCSV(); 
        int opcion;

        do {
            System.out.println("\n===== Sistema de Inventario =====");
            System.out.println("1. Agregar nuevo producto");
            System.out.println("2. Editar producto");
            System.out.println("3. Buscar producto por SKU");
            System.out.println("4. Buscar producto por nombre");
            System.out.println("5. Listar productos ordenados por SKU");
            System.out.println("6. Listar productos ordenados por nombre");
            System.out.println("7. Salir");
            System.out.print("\nSeleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    editarProducto();
                    break;
                case 3:
                    buscarPorSKU();
                    break;
                case 4:
                    buscarPorNombre();
                    break;
                case 5:
                    listarPorSKU();
                    break;
                case 6:
                    listarPorNombre();
                    break;
                case 7:
                    System.out.println("\nSaliendo del sistema...");
                    break;
                default:
                    System.out.println("\nOpción inválida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    private static void agregarProducto() {
        System.out.println("\n=== Agregar Nuevo Producto ===");
        System.out.print("\nIngrese SKU: ");
        String sku = scanner.nextLine();
        System.out.print("\nIngrese Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("\nIngrese Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.println("\nIngrese las tallas y cantidades (Ejemplo: xs:10|s:15|m:25): ");
        String tallasStr = scanner.nextLine();
        Map<String, Integer> tallas = parsearTallas(tallasStr);

        Producto nuevoProducto = new Producto(sku, nombre, descripcion, tallas);
        lectorCSV.agregarProducto(nuevoProducto);

        System.out.println("\nProducto agregado exitosamente.");
        lectorCSV.guardarCSV();
    }

    private static void editarProducto() {
        System.out.println("\n=== Editar Producto ===");
        System.out.print("\nIngrese SKU del producto a editar: ");
        String sku = scanner.nextLine();
        Producto producto = lectorCSV.buscarPorSKU(sku);

        if (producto != null) {
            System.out.println("\nProducto encontrado: " + producto.getNombre());
            System.out.println("\n1. Editar Descripción");
            System.out.println("\n2. Editar Tallas y Cantidades");
            System.out.print("\nSeleccione opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            if (opcion == 1) {
                System.out.print("\nIngrese nueva descripción: ");
                String nuevaDescripcion = scanner.nextLine();
                producto.setDescripcion(nuevaDescripcion);
                lectorCSV.guardarCSV();
            } else if (opcion == 2) {
                System.out.print("\nIngrese nuevas tallas y cantidades (Ejemplo: xs:10|s:15|m:25): ");
                String tallasStr = scanner.nextLine();
                producto.setTallas(parsearTallas(tallasStr));
                lectorCSV.guardarCSV();
            } else {
                System.out.println("\nOpción inválida.");
            }
        } else {
            System.out.println("\nProducto no encontrado.");
        }
    }

    private static void buscarPorSKU() {
        System.out.print("\nIngrese SKU a buscar: ");
        String sku = scanner.nextLine();
        Producto producto = lectorCSV.buscarPorSKU(sku);

        if (producto != null) {
            mostrarProducto(producto);
        } else {
            System.out.println("\nProducto no encontrado.");
        }
    }

    private static void buscarPorNombre() {
        System.out.print("\nIngrese Nombre a buscar: ");
        String nombre = scanner.nextLine();
        Producto producto = lectorCSV.buscarPorNombre(nombre);

        if (producto != null) {
            mostrarProducto(producto);
        } else {
            System.out.println("\nProducto no encontrado.");
        }
    }

    private static void listarPorSKU() {
        System.out.println("\n=== Listado de Productos (Ordenados por SKU) ===");
        List<Producto> productos = lectorCSV.listarPorSKU();
        for (Producto p : productos) {
            mostrarProducto(p);
        }
    }

    private static void listarPorNombre() {
        System.out.println("\n=== Listado de Productos (Ordenados por Nombre) ===");
        List<Producto> productos = lectorCSV.listarPorNombre();
        for (Producto p : productos) {
            mostrarProducto(p);
        }
    }

    private static void mostrarProducto(Producto producto) {
        System.out.println("----------------------------");
        System.out.println("\nSKU: " + producto.getSku());
        System.out.println("\nNombre: " + producto.getNombre());
        System.out.println("\nDescripción: " + producto.getDescripcion());
        System.out.println("\nTallas y Cantidades: " + producto.getTallas());
        System.out.println("----------------------------");
    }

    private static Map<String, Integer> parsearTallas(String tallasStr) {
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
}
