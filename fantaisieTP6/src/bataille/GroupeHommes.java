package bataille;

import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import protagoniste.*;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import attaque.*;
import bataille.Bataille;
import bataille.GroupeHommes.ComparateurArmes;

public class GroupeHommes {
	Set<Homme> groupe = new TreeSet<Homme>();
	
	public GroupeHommes() {
	}
	
	public void ajouterHommes(Homme... homme) {
		for(Homme h : homme) {
			//System.out.println("On ajoute " + h + "\n");
			this.groupe.add(h);
		}
	}
	
	/*
	public class ComparateurHommes implements Comparator<Homme> {
		public ComparateurHommes() {
		}

		@Override
		public int compare(Homme h1, Homme h2) {
			int forceHomme1 = h1.getForceDeVie();
			int forceHomme2 = h2.getForceDeVie();
			int comparaison = forceHomme2 - forceHomme1;
			if (comparaison == 0) {
				comparaison = h1.compareTo(h2);
			}
			return comparaison;
		}
	}
	*/
	
	
	public class ComparateurArmes implements Comparator<Arme> {
		Monstre<?> monstre;
		
		public ComparateurArmes(Monstre<?> monstre) {
			this.monstre = monstre;
		}

		@Override
		public int compare(Arme a1, Arme a2) {
			int comparaison = 0;
			int forceMonstre = monstre.getForceDeVie();
			int forceAttaqueArme1 = a1.getPointDeDegat();
			int forceAttaqueArme2 = a2.getPointDeDegat();
			if (forceAttaqueArme1 != forceAttaqueArme2) {
				NavigableMap<Integer, Arme> classementForce = new TreeMap<>();
				classementForce.put(forceAttaqueArme1, a1);
				classementForce.put(forceAttaqueArme2, a2);
				Entry<Integer, Arme> bestArme;
				bestArme = classementForce.floorEntry(forceMonstre);
				if (bestArme == null) {
					bestArme = classementForce.ceilingEntry(forceMonstre);
				}
				if (bestArme.getValue().equals(a2)) {
					comparaison = 1;
				} else {
					comparaison = -1;
				}
			} else {
				return a1.compareTo(a2);
			}
			return comparaison;
		}
	}
	
	public List<Homme> choixParticipants(Bataille bataille){
		List<Homme> choix = new ArrayList<>();
		Monstre<? extends Pouvoir> monstre = bataille.getCampMonstres().selectionner();
		NavigableMap<Arme, NavigableSet<Homme>> hommesArmes = new TreeMap<>(new ComparateurArmes(monstre));
		
		firstPart(monstre, hommesArmes);
		
		/* 
		 * 
		 * Ancienne Version
		 * 
		 * 
		for(Arme arme : hommesArmes.keySet()) {
			for(Homme homme : hommesArmes.get(arme)) {
				if(choix.size() < 3) {
					choix.add(homme);
				}
			}
		}
		*/
		
		do {
			Entry<Arme, NavigableSet<Homme>> entry = hommesArmes.pollFirstEntry();
			if (entry != null) {
				NavigableSet<Homme> liste = entry.getValue();
				for (Iterator<Homme> iterator = liste.iterator(); choix.size() < 3 && iterator.hasNext();) {
					Homme homme = iterator.next();
					choix.add(homme);
				}
			}
		}while(choix.size() < 3 && !hommesArmes.isEmpty());
		
		
		for(Homme h : choix) {
			h.rejointBataille(bataille);
		}
		
		return choix;
	}


	public List<Homme> choixCampHomme(Bataille bataille){
		List<Homme> choix = new ArrayList<Homme>();
		Monstre<?> monstre = bataille.getCampMonstres().selectionner();
		NavigableMap<Arme, NavigableSet<Homme>> hommesArmes = new TreeMap<>();
		
		firstPart(monstre, hommesArmes);
		//System.out.println("Construction list hommes\n");
		for(Arme arme : hommesArmes.keySet()) {
			for(Homme homme : hommesArmes.get(arme)) {
				//System.out.println("On regarde " + homme.getClass() + "\n");
				if(choix.size() < 3) {
					//System.out.println("On ajoute " + homme + "\n");
					choix.add(homme);
				}
			}
		}
		for(Homme h : choix) {
			h.rejointBataille(bataille);
		}
		
		return choix;
	}

	
	 private void firstPart(Monstre<?> monstre, NavigableMap<Arme, NavigableSet<Homme>> hommesArmes) {
		Arme arme;
		for(Homme h : this.groupe) {
			NavigableSet<Homme> listeHommes;
			arme = h.choisirArme(monstre);
			if(arme != null ) {
				if(hommesArmes.containsKey(arme)) {
					listeHommes = hommesArmes.get(arme);
				}else {
					listeHommes = new TreeSet<>((h1,h2) -> {
							if ((h1.getForceDeVie() - h2.getForceDeVie()) == 0) {
								return h1.compareTo(h2);
							}else {
								return h1.getForceDeVie() - h2.getForceDeVie();
							}
						});
					hommesArmes.put(arme, listeHommes);
				}
				listeHommes.add(h);
			}
		}
	}
	
}
