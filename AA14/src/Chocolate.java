
public class Chocolate extends Golosina {
	
	
	private String tipo;
	private int cantidadProducida;
	
	public Chocolate(String marca, double precio, String tipo, int cantidadProducida) {
		super(marca, precio);
		this.tipo = tipo;
		this.cantidadProducida = cantidadProducida;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCantidadProducida() {
		return cantidadProducida;
	}

	public void setCantidadProducida(int cantidadProducida) {
		this.cantidadProducida = cantidadProducida;
	}

	@Override
	public String toString() {
		return "Chocolate [tipo=" + tipo + ", cantidadProducida=" + cantidadProducida + "]";
	}

	
}
