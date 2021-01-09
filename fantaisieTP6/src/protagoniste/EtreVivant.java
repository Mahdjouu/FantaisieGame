package protagoniste;

import bataille.Bataille;

public abstract class EtreVivant implements Comparable<EtreVivant>{
	protected String nom;
	protected int forceDeVie;
	protected Bataille bataille;
	
	public EtreVivant(String nom, int forceDeVie) {
		this.nom = nom;
		this.forceDeVie = forceDeVie;
	}
	
	public String getNom() {
		return nom;
	}
	
	public int getForceDeVie() {
		return forceDeVie;
	}

	public String toString() {
		return "EtreVivant [nom=" + nom + ", forceDeVie=" + forceDeVie + "]";
	}
	
	public void rejointBataille(Bataille bataille) {
		this.bataille = bataille;
	}
	
	public abstract void mourir() ;
	
	public boolean equals(EtreVivant etreVivant) {
		return this.getNom() == etreVivant.getNom();
	}
	
	public int compareTo(EtreVivant etreVivant){
		return this.nom.compareToIgnoreCase(etreVivant.nom);
	}
}
