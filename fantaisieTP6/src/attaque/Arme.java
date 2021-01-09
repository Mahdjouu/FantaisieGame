package attaque;

import java.util.HashSet;
import java.util.Set;

import protagoniste.*;

public abstract class Arme extends ForceDeCombat implements Orderable<Arme>{
	private Set<ZoneDeCombat> zoneDeCombat = new HashSet<ZoneDeCombat>();
	
	public Arme(int pointDeDegat, String nom, ZoneDeCombat... combat) {
		super(pointDeDegat, nom);
		if(combat != null) {
			for(ZoneDeCombat zdc : combat) {
				this.zoneDeCombat.add(zdc);
			}
		}
	}

	public Set<ZoneDeCombat> getZoneDeCombat() {
		return zoneDeCombat;
	}
	
	public boolean equals(Arme arme) {
		return this.getNom() == arme.getNom();
	}
	
	@Override
	public int compareTo(Arme arme) {
		if(this.isOperationnel() && !arme.isOperationnel()) {
			return -1;
		}else if(arme.isOperationnel() && !this.isOperationnel()) {
			return 1;
		}else {
			if(this.getPointDeDegat() > arme.getPointDeDegat()) {
				return -1;
			}else if(this.getPointDeDegat() < arme.getPointDeDegat()) {
				return 1;
			}else {
				if(this.getNom() == null) {
					return 1;
				}else if(arme.getNom() == null) {
					return -1;
				}
				return this.getNom().compareToIgnoreCase(arme.getNom());
			}
		}
	}
}
