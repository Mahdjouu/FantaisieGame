package livre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attaque.Pouvoir;
import bataille.Grotte;
import bataille.Salle;
import protagoniste.Monstre;

public class Ecran implements Livre {

	@Override
	public void ecrire(String chaine) {
		System.out.println(chaine);
	}

	public void afficherLesMonstres(Map<String, List<Monstre<? extends Pouvoir>>> monstres) {
		String affichage = "";
		int taille = monstres.size();
		for(String key : monstres.keySet()) {
			affichage += key.toString();
			taille--;
			if(taille == 0) {
				affichage += ".\n";
			}else {
				affichage += ", ";
			}
		}
		ecrire(affichage);
	}

}
