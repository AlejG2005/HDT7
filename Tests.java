import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests {
    private BinarySearchTree<String, String> bst;

//Antes de cada test crea un nuevo BST vacío.

    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree<>();
    }

    /*Primero inserta 2 productos con su respectivo SKU y nombre. Luego los busca y confirma que existen. Por último intenta buscar
    un número de SKU no existente, lo que debería de retornar null.*/
    @Test
    void testInsertAndSearch() {
        bst.insert("123", "Jersey deportivo");
        bst.insert("456", "Chumpa impermeable");

        assertEquals("Jersey deportivo", bst.search("123"), "El BST debería encontrar el producto con SKU 123");
        assertEquals("Chumpa impermeable", bst.search("456"), "El BST debería encontrar el producto con SKU 456");
        assertNull(bst.search("789"), "El BST no debería encontrar un SKU inexistente");
    }
}
