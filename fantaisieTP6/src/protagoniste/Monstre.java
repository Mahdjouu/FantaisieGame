package protagoniste;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import attaque.Pouvoir;
import bataille.Bataille;

public class Monstre <A extends Pouvoir> extends EtreVivant {
	private A attaque[];
	private ZoneDeCombat zoneDeCombat;
	private Domaine domaine;
	private GestionAttaque gestionAttaque;
	
	public Monstre(String nom, int forceDeVie, ZoneDeCombat zoneDeCombat, Domaine domaine, A... attaque) {
		super(nom, forceDeVie);
		this.attaque = attaque;
		this.zoneDeCombat = zoneDeCombat;
		this.domaine = domaine;
	}
	
	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}
	
	public Domaine getDomaine() {
		return domaine;
	}

	public String toString() {
		return "Monstre [nom=" + this.getNom() + ", attaques=" + Arrays.toString(attaque) + ", zoneDeCombat="
				+ this.getZoneDeCombat() + ", forceDeVie=" + forceDeVie + "]";
	}

	public void entreEnCombat() {
		
		for(A atk : this.attaque) {
			atk.regenererPouvoir();
		}
		gestionAttaque = new GestionAttaque();
	}
	
	public A attaque() {
		if(gestionAttaque.hasNext()) {
			return gestionAttaque.next();
		}else {
			return null;
		}
	}
	
	public void rejointBataille(Bataille bataille) {
		super.rejointBataille(bataille);
		this.bataille.ajouter(this);
	}
	
	public void mourir() {
		this.bataille.eliminer(this);
	}
	
	public class GestionAttaque implements Iterator<A>{
		private A attaquePossibles[] =  attaque;
		private int nbAttaquesPossibles = attaque.length;
		
		
		public boolean hasNext() {
			
			for (int iterateur = 0; iterateur<this.nbAttaquesPossibles ; iterateur++){
				if(!(this.attaquePossibles[iterateur].isOperationnel())) {
					this.attaquePossibles[iterateur] = this.attaquePossibles[this.nbAttaquesPossibles-1];
					this.nbAttaquesPossibles--;
				}
			}
			
			return (this.nbAttaquesPossibles > 0);
		}

		public A next() {
			Random rand = new Random();
			return this.attaquePossibles[rand.nextInt(this.nbAttaquesPossibles)];
		}
		
	}
}
