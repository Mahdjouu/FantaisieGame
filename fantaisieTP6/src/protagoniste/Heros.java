package protagoniste;

public class Heros extends Homme{
	public String toString() {
		return "Heros [nom=" + nom + ", forceDeVie=" + forceDeVie + "]";
	}

	public Heros(String nom) {
		super(nom);
		this.forceDeVie = 100;
	}
	
	
}
