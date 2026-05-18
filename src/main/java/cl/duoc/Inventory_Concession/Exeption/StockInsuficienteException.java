package cl.duoc.Inventory_Concession.Exeption;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String message) {
        super(message);
    }
}