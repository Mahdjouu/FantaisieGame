package testsfonctionnels;

import attaque.*;
import protagoniste.*;

public class TestGestionAttaque {
	public static void main(String args[]){
		Monstre<Feu> dragotenebre = new Monstre<Feu>("dragotenebre", 200, ZoneDeCombat.AERIEN, Domaine.FEU, new Lave(2), new Eclair(2), new BouleDeFeu(2));
		
		System.out.println(dragotenebre.toString());
		//System.out.println(dragonetenebre_tab.length);
		
		dragotenebre.entreEnCombat();
		
		Feu atk;
		//System.out.println(atk = dragotenebre.attaque());
		while((atk = dragotenebre.attaque()) != null) {
			atk.utiliser();
			System.out.println(atk);
		}
	}
}
