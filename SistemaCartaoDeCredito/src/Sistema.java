import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Sistema {
  
  public static final int PORTA = 55555;
  
  public void iniciar() {
    ServerSocket escuta = null;
    try {
      escuta = new ServerSocket(PORTA);
      Utils.printlnAndFlush("*** Sistema iniciado na porta " + PORTA + " ***");
      
      while (true) {
        Socket cliente = escuta.accept();
        Utils.printlnAndFlush("*** Conexao aceita de " + cliente.getRemoteSocketAddress());
        Conexao conexao = new Conexao(cliente);
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
}
