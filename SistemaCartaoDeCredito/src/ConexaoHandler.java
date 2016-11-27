import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexaoHandler extends Thread {
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private Socket clienteSocket;
  
  public ConexaoHandler(Socket clienteSocket) {
    this.clienteSocket = clienteSocket;
  }
  
  @Override
  public void run() {
    TransacaoCartaoData transacaoCartaoData = null;
    String errorMsg = null;
    
    try {
      out = new ObjectOutputStream(clienteSocket.getOutputStream());      
      in = new ObjectInputStream(clienteSocket.getInputStream());
    } catch (IOException e) {
      errorMsg = "Erro interno!";
      System.err.println("IOException: " + e.getMessage());
    }
    
    try {
      try {
        transacaoCartaoData = (TransacaoCartaoData) in.readObject();
      } catch (ClassNotFoundException e) {
        errorMsg = "Erro interno!";
        System.err.println("ClassNotFoundException: " + e.getMessage());      
      }
      
      errorMsg = Sistema.getInstance().fazerTransacao(transacaoCartaoData.getNumeroOrigem(), 
          transacaoCartaoData.getNumeroDestino(), transacaoCartaoData.getQuantia());   
         
    } catch (EOFException e) {
      errorMsg = "Erro interno!";
      System.err.println("EOFException: " + e.getMessage());    
    } catch (IOException e) {
      errorMsg = "Erro interno!";
      System.err.println("IOException: " + e.getMessage());
    } finally {
      transacaoCartaoData.setErrorMessage(errorMsg);
      try {
        out.writeObject(transacaoCartaoData);
        clienteSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    }
    
  }

}
