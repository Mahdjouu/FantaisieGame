package attaque;

public abstract class Pouvoir extends ForceDeCombat{
	private int nbUtilisationPouvoir;
	private int nbUtilisationPouvoirInitial;
	
	public Pouvoir(int pointDeDegat, String nom, int nbUtilisationPouvoir) {
		super(pointDeDegat, nom);
		this.nbUtilisationPouvoir = nbUtilisationPouvoir;
		this.nbUtilisationPouvoirInitial = nbUtilisationPouvoir;
	}
	
	public void regenererPouvoir() {
		this.operationnel = true;
		this.nbUtilisationPouvoir = this.nbUtilisationPouvoirInitial;
	}
	
	public int utiliser() {
		if(this.nbUtilisationPouvoir < 1) {
			System.out.println("Attention : plus d'utilisation!");
			this.operationnel = false;
		}
		
		this.nbUtilisationPouvoir--;
		
		if(this.nbUtilisationPouvoir == 0) {
			this.operationnel = false;
		}
		
		return super.utiliser();
	}
}
