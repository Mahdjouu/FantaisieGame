package bataille;

import protagoniste.ZoneDeCombat;

public class Salle {
	private int numSalle;
	private ZoneDeCombat zoneDeCombat;
	
	public Salle(int numSalle, ZoneDeCombat zoneDeCombat) {
		this.numSalle = numSalle;
		this.zoneDeCombat = zoneDeCombat;
	}

	public int getNumSalle() {
		return numSalle;
	}

	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}

	
	public String toString() {
		return "Salle num " + numSalle + " de type combat " + zoneDeCombat + "\n";
	}
	
	public int hashCode() {
		return 13*this.numSalle;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Salle) {
			return ((Salle) o).getNumSalle() == this.numSalle;
		}
		return false;
	}
}
