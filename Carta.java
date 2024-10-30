public class Carta {
 private String figura;
 private int valor;

 public Carta(String figura, int valor) {
  this.figura = figura;
  this.valor = valor;
 }

 public String getFigura() {
  return figura;
 }

 public void setFigura(String figura) {
  this.figura = figura;
 }

 public int getValor() {
  return valor;
 }

 public void setValor(int valor) {
  this.valor = valor;
 }

 @Override
 public String toString() {
  return valor + " de " + figura;
 }
}
