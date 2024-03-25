package onlineshopping;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class WestminsterShoppingManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddNewElectronicsProduct() {
        String input = "1\nE1\nETest\n10\n25\nApple\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addNewProduct();
    }

    @Test
    public void testAddNewClothingProduct() {
        String input = "2\nC1\nCTest\n10\n23\nM\nRed\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.addNewProduct();
    }

    @Test
    public void testDeleteProduct() {
        String addInput = "2\nE001\n";
        System.setIn(new ByteArrayInputStream(addInput.getBytes()));
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        String deleteInput = "E001\n";
        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        manager.deleteProduct();
    }
}



