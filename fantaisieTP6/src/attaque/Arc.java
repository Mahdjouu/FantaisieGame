package attaque;

import protagoniste.ZoneDeCombat;

public class Arc extends Arme{
	private int nbFlechesRestantes;
	
	public Arc(int nbFlechesRestantes) {
		super(50, "Arc", ZoneDeCombat.AQUATIQUE, ZoneDeCombat.AERIEN, ZoneDeCombat.TERRESTRE);
		this.nbFlechesRestantes = nbFlechesRestantes;
	}
	
	public int utiliser() {
		if(this.nbFlechesRestantes < 1) {
			System.out.println("Attention : plus de fl�ches!");
			this.operationnel = false;
		}
		
		this.nbFlechesRestantes--;
		
		if(this.nbFlechesRestantes == 0) {
			this.operationnel = false;
		}
		
		return super.getPointDeDegat();
	}
}
