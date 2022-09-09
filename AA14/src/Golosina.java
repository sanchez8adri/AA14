
public class Golosina {
	
	private String marca;
	private double precio;
	
	public Golosina(String marca, double precio) {
		this.marca = marca;
		this.precio = precio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Golosina [marca=" + marca + ", precio=" + precio + "]";
	}
	
	

}
