import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Conexao extends Thread {
  private DataInputStream in;
  private DataOutputStream out;
  private Socket clienteSocket;
  
  public Conexao(Socket clienteSocket) {
    this.clienteSocket = clienteSocket;
    try {
      in = new DataInputStream(clienteSocket.getInputStream());
      out = new DataOutputStream(clienteSocket.getOutputStream());
    } catch (IOException e) {
      System.err.println("Erro IO de conexao: " + e.getMessage());
    }
  }
  
  @Override
  public void run() {
    String recebido;
    try {
      recebido = in.readUTF();
      out.writeUTF(recebido.toUpperCase());
    } catch (EOFException e) {
      System.err.println("Erro EOFconexao: " + e.getMessage());      
    } catch (IOException e) {
      System.err.println("Erro IO de conexao: " + e.getMessage());
    } finally {
      try {
        clienteSocket.close();
      } catch (IOException e) {
        System.err.println("Erro ao fechar socket cliente: " + e.getMessage());
      }
    }
    
  }
  
}
