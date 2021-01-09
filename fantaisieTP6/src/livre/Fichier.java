package livre;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import attaque.Pouvoir;
import protagoniste.Monstre;

public class Fichier implements Livre{

	/*
	 * 
	 * Version 1
	 * 
	public void ecrire(String chaine) {
		try {
			String chemin = "./src/histoire.txt";
			File writer = new File(chemin);
			FileWriter fichier = new FileWriter(writer, true);
			
			try {
				fichier.write(chaine);
			}finally {
				fichier.close();
			}
		}catch(IOException ex) {
			System.out.println("Erreur Fichier");
			ex.printStackTrace();
		}
		
	}
	*/
	
	        
	public void ecrire(String chaine) {
		try(FileWriter fichier = new FileWriter(new File("./src/histoire.txt"), true)){
			fichier.write(chaine);
		}catch(IOException ex) {
			System.out.println("Erreur Fichier");
			ex.printStackTrace();
		}
	}

	@Override
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
