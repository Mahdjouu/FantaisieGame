package attaque;

public abstract class ForceDeCombat{
	private int pointDeDegat;
	private String nom;
	protected boolean operationnel = true;
	
	public ForceDeCombat(int pointDeDegat, String nom) {
		this.pointDeDegat = pointDeDegat;
		this.nom = nom;
	}
	
	public int getPointDeDegat() {
		return this.pointDeDegat;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public boolean isOperationnel() {
		return this.operationnel;
	}
	
	
	@Override
	public String toString() {
		return "ForceDeCombat [pointDeDegat=" + pointDeDegat + ", nom=" + nom  + "]";
	}

	public int utiliser() {
		return this.pointDeDegat;
	}
}
