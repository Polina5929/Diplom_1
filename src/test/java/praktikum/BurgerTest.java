package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BurgerTest {

    @Mock
    private Bun bunMock;

    @Mock
    private Ingredient ingredientMock;

    private Burger burger;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBunsShouldAssignBun() {
        burger.setBuns(bunMock);
        assertSame(bunMock, burger.bun);
    }

    @Test
    public void addIngredientShouldAddToList() {
        burger.addIngredient(ingredientMock);
        assertEquals(1, burger.ingredients.size());
        assertSame(ingredientMock, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldRemoveFromList() {
        burger.addIngredient(ingredientMock);
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientShouldSwapElements() {
        Ingredient second = mock(Ingredient.class);
        burger.addIngredient(ingredientMock);
        burger.addIngredient(second);

        burger.moveIngredient(1, 0);

        assertSame(second, burger.ingredients.get(0));
        assertSame(ingredientMock, burger.ingredients.get(1));
    }

    @Test
    public void getPriceWithoutIngredients() {
        when(bunMock.getPrice()).thenReturn(120f);
        burger.setBuns(bunMock);

        assertEquals(240f, burger.getPrice(), 0.01f);
    }

    @Test
    public void getReceiptWithoutIngredients() {
        when(bunMock.getName()).thenReturn("Sesame");
        when(bunMock.getPrice()).thenReturn(50f);
        burger.setBuns(bunMock);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Sesame"));
        assertTrue(receipt.contains("Price:"));
    }

    @Test
    public void getReceiptWithIngredient() {
        when(bunMock.getName()).thenReturn("Rye");
        when(bunMock.getPrice()).thenReturn(40f);
        when(ingredientMock.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredientMock.getName()).thenReturn("cheese sauce");
        when(ingredientMock.getPrice()).thenReturn(15f);

        burger.setBuns(bunMock);
        burger.addIngredient(ingredientMock);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Rye"));
        assertTrue(receipt.contains("sauce"));
        assertTrue(receipt.contains("cheese sauce"));
        assertTrue(receipt.contains("Price:"));
    }
}