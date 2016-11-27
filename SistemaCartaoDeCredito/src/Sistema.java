import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Sistema {
  /////////////////////////////////////////
  public static final int PORTA = 55555;
  /////////////////////////////////////////
 
  private static final Sistema INSTANCE = new Sistema();
  private List<CartaoCredito> cartoes;
  
  private Sistema() {
    cartoes = new ArrayList<>();
  }

  public static Sistema getInstance() {
    return INSTANCE;
  }
   
  public void iniciar() {
    
    ServerSocket escuta = null;
    try {
      escuta = new ServerSocket(PORTA);
      Utils.printlnAndFlush("*** Sistema de cartão iniciado na porta " + PORTA + " ***");
      
      while (true) {
        Socket cliente = escuta.accept();
        Utils.printlnAndFlush("*** Conexao aceita de " + cliente.getRemoteSocketAddress());
        ConexaoHandler conexao = new ConexaoHandler(cliente);
        conexao.start();
      }
    } catch (IOException e) {
      System.err.println("Error na escuta: " + e.getMessage());
    } finally {
      if (escuta != null) {
        try {
          escuta.close();
        } catch (IOException e) {
          System.err.println("Erro ao fechar socket: " + e.getMessage());
        }
      }
    }
    
  }
    
  public List<CartaoCredito> getCartoes() {
    return cartoes;
  }
  
  public boolean cartaoExiste(String numero) {
    for (CartaoCredito cartao : Sistema.getInstance().getCartoes()) {
      if (cartao.getNumero().equals(numero)) {
        return true;
      }
    }
    
    return false;
  } 
  
  public CartaoCredito getCartaoByNumero(String numero) {
    for (CartaoCredito cartao : Sistema.getInstance().getCartoes()) {
      if (cartao.getNumero().equals(numero)) {
        return cartao;
      }
    }
    
    return null;
  } 

  synchronized public String fazerTransacao(String numeroDe, String numeroPara, double quantia) {
    if (numeroDe.equals(numeroPara)) {
      return "Transação não permitida!";
    }
    
    CartaoCredito cartaoDe = Sistema.getInstance().getCartaoByNumero(numeroDe);
    CartaoCredito cartaoPara = Sistema.getInstance().getCartaoByNumero(numeroPara);
    
    if (cartaoDe == null) {
      return "Número do cartão inválido!";  
    } 
    
    if (cartaoPara == null) {
      return "Número do cartão inválido!";  
    }
    
    if (quantia <= 0.0) {
      return "Quantia inválida!";  
    } 
    
    if (cartaoDe.getLimite() < quantia) {
      return "Limite do cartão insuficiente. Limite disponível: $" + cartaoDe.getLimite();  
    }
    
    cartaoDe.setLimite(cartaoDe.getLimite() - quantia);    
    cartaoPara.setLimite(cartaoDe.getLimite() + quantia);
    
    return null;  
  }  
}
