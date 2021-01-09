package bataille;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import attaque.Pouvoir;

import java.util.Set;

import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;
import protagoniste.ZoneDeCombatNonCompatibleException;

public class Grotte {
	private LinkedHashMap<Salle, ArrayList<Salle>> planGrotte = new LinkedHashMap<Salle, ArrayList<Salle>>();
	private HashMap<Salle, Bataille> batailles = new HashMap<Salle, Bataille>();
	private Set<Salle> sallesExplorees = new HashSet<>();
	private int numeroSalleDecisive;
	
	

	public LinkedHashMap<Salle, ArrayList<Salle>> getPlanGrotte() {
		return planGrotte;
	}
	
	public void setNumeroSalleDecisive(int numeroSalleDecisive) {
		this.numeroSalleDecisive = numeroSalleDecisive;
	}



	public void ajouterSalle(ZoneDeCombat zoneDeCombat, Monstre<? extends Pouvoir>...monstres) throws ZoneDeCombatNonCompatibleException {
		int numSalle = this.planGrotte.size()+1;
		Salle salle = new Salle(numSalle, zoneDeCombat);
		Bataille bataille = new Bataille();
		
		for(Monstre<? extends Pouvoir> m : monstres) {
			
			if(zoneDeCombat != m.getZoneDeCombat()) {
				throw new ZoneDeCombatNonCompatibleException("La zone de combat de la salle et de type " + zoneDeCombat + ", alors que celle du monstre est " + m.getZoneDeCombat() + "\n");
			}
			
			
			bataille.ajouter(m);
		}
		
		this.planGrotte.put(salle, new ArrayList<Salle>());
		this.batailles.put(salle, bataille);
	}
	


	public HashMap<Salle, Bataille> getBatailles() {
		return batailles;
	}



	public void setBatailles(HashMap<Salle, Bataille> batailles) {
		this.batailles = batailles;
	}



	public String afficherPlanGrotte() {
	  StringBuilder affichage = new StringBuilder();
	  for (Entry<Salle, ArrayList<Salle>> entry : planGrotte.entrySet()) {
	   Salle salle = entry.getKey();
	   List<Salle> acces = planGrotte.get(salle);
	   affichage.append("La " + salle + ".\nelle possede " + acces.size() + " acces : ");
	   for (Salle access : acces) {
	    affichage.append(" vers la salle " + access);
	   }
	   Bataille bataille = batailles.get(salle);
	   Camp<Monstre<? extends Pouvoir>> camp = bataille.getCampMonstres();
	   Monstre<? extends Pouvoir> monstre = camp.selectionner();
	   if (camp.nbCombattants() > 1) {
	    affichage.append("\n" + camp.nbCombattants() + " monstres de type ");
	   } else {
	    affichage.append("\nUn monstre de type ");
	   }
	   affichage.append(monstre.getNom() + " la protege.\n");
	   if (salle.getNumSalle() == numeroSalleDecisive) {
	    affichage.append("C'est dans cette salle que se trouve la pierre de sang.\n");
	   }
	   affichage.append("\n");
	  }
	  return affichage.toString();
	}

	
	private Salle trouverSalle(int numeroSalle) {
		int indice = 1;
		
		for(Iterator<Salle> iter = planGrotte.keySet().iterator(); iter.hasNext(); indice++) {
			if(indice == numeroSalle) {
				return iter.next();
			}
			iter.next();
		}
		return null;
	}
	
	public void configurerAcces(int numSalleDepart, int... salles) {
		Salle salleDepart = trouverSalle(numSalleDepart);
		ArrayList<Salle> salleAcces = new ArrayList<Salle>();
		
		for(int indice : salles) {
			salleAcces.add(trouverSalle(indice));
		}
		
		this.planGrotte.put(salleDepart, salleAcces);
	}
	
	public boolean salleDecisive(Salle salle) {
		return salle.getNumSalle() == this.numeroSalleDecisive;
	}
	
	public Salle premiereSalle() {
		Salle premiereSalle = this.planGrotte.keySet().iterator().next();
		
		this.sallesExplorees.add(premiereSalle);
		return premiereSalle;
	}
	
	public Salle salleSuivante(Salle salleActuel) {
		Salle retour;
		List<Salle> acces = new ArrayList<Salle>(planGrotte.get(salleActuel));
		acces = remove_acces(acces);
		
		Random rand = new Random();
		
		if(acces.isEmpty()) {
			acces = planGrotte.get(salleActuel);
		}

		retour = acces.get(rand.nextInt(acces.size()));
		this.sallesExplorees.add(retour);
		return retour;
	}



	private List<Salle> remove_acces(List<Salle> acces) {
		if(acces.isEmpty()) {
			return acces;
		}
		Salle supp;
		for(Iterator<Salle> iter = acces.iterator() ; iter.hasNext();){
			supp = iter.next();
			
			if(this.sallesExplorees.contains(supp)){
				iter.remove();
			}
		}
		return acces;
	}
}
