package bataille;


import attaque.Pouvoir;
import protagoniste.*;

public class Bataille {
	private Camp<Homme> campHumains = new Camp<Homme>();
	private Camp<Monstre<? extends Pouvoir>> campMonstres = new Camp<Monstre<?>>();
	
	public void ajouter(Homme homme) {
		this.campHumains.ajouter(homme);
	}
	
	public void ajouter(Monstre<? extends Pouvoir> monstre) {
		this.campMonstres.ajouter(monstre);
	}
	
	public void eliminer(Homme homme) {
		this.campHumains.eliminer(homme);
	}
	
	public void eliminer(Monstre<? extends Pouvoir> monstre) {
		this.campMonstres.eliminer(monstre);
	}

	public Camp<Homme> getCampHumains() {
		return campHumains;
	}

	public Camp<Monstre<? extends Pouvoir>> getCampMonstres() {
		return campMonstres;
	}
}
