
public class CartaoCredito {
  private String titular;
  private String numero;
  private double limite;
   
  public CartaoCredito(String titular, String numero, double limite) {
    this.titular = titular;
    this.limite = limite;
    this.numero = numero;
  }
  
  public String getTitular() {
    return titular;
  }
  public void setTitular(String titular) {
    this.titular = titular;
  }
  public String getNumero() {
    return numero;
  }
  public void setNumero(String numero) {
    this.numero = numero;
  }
  
  synchronized public double getLimite() {
    return limite;
  }
  synchronized public void setLimite(double limite) {
    this.limite = limite;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((numero == null) ? 0 : numero.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CartaoCredito other = (CartaoCredito) obj;
    if (numero == null) {
      if (other.numero != null)
        return false;
    } else if (!numero.equals(other.numero))
      return false;
    return true;
  }
  
  
  
}
