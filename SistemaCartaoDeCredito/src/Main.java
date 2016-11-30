
public class Main {

  public static void main(String[] args) {
    CartaoCredito cartaoCliente = new CartaoCredito("Cliente", "1111", 40.0);
    CartaoCredito cartaoProdutora = new CartaoCredito("Produtora", "2222", 0.0);

    Sistema.getInstance().getCartoes().add(cartaoCliente);
    Sistema.getInstance().getCartoes().add(cartaoProdutora);
    
    Sistema.getInstance().iniciar();
  }


}
