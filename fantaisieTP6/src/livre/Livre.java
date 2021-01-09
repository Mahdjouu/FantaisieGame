package livre;

import java.util.List;
import java.util.Map;

import attaque.Pouvoir;
import bataille.Grotte;
import protagoniste.Monstre;

public interface Livre {
	public default void ecrire(String chaine) {
		System.out.println(chaine + "\n");
	}
	
	public void afficherLesMonstres(Map<String, List<Monstre<? extends Pouvoir>>> monstres);
}
