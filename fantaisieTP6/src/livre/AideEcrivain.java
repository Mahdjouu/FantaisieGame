package livre;

import java.awt.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.TreeSet;

import attaque.*;
import bataille.*;
import protagoniste.Domaine;
import protagoniste.Homme;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;

public class AideEcrivain {
	private NavigableSet<Monstre<?>> monstresDomaineSet = new TreeSet<Monstre<?>>(new MonstresDomaineSet());
	private NavigableSet<Monstre<?>> monstresZoneSet = new TreeSet<Monstre<?>>(new MonstresZoneSet());
	private NavigableSet<Monstre<?>> monstresDeFeu;
	private NavigableSet<Monstre<?>> monstresDeGlace;
	private NavigableSet<Monstre<?>> monstresTranchants;
	
	public NavigableSet<Monstre<?>> getMonstresDeFeu() {
		this.updateDomaineSet();
		return monstresDeFeu;
	}

	public NavigableSet<Monstre<?>> getMonstresDeGlace() {
		this.updateDomaineSet();
		return monstresDeGlace;
	}

	public NavigableSet<Monstre<?>> getMonstresTranchants() {
		this.updateDomaineSet();
		return monstresTranchants;
	}

	private Bataille bataille;
	
	public AideEcrivain(Bataille bataille) {
		this.bataille = bataille;
	}
	
	public LinkedList<Homme> visualiserForcesHumaines() {
		LinkedList<Homme> listeTriee = new LinkedList<Homme>();
		int compteurHeros = 0;
		for(Homme h : bataille.getCampHumains())
			if(h.getForceDeVie() == 100) {
				listeTriee.add(compteurHeros, h);
				compteurHeros++;
			}else
				listeTriee.add(h);
		return listeTriee;
	}
	
	public String ordreNaturelMonstre() {
		String tri = "";
		NavigableSet<Monstre<?>> list = new TreeSet<Monstre<?>>();
		
		for(Monstre<?> m : bataille.getCampMonstres()) {
			list.add(m);
		}
		
		
		for(Monstre<?> m : list) {
			if(tri != "") {
				tri += ", ";
			}
			tri = tri + m.getNom();
			
		}
		
		return tri;
	}
	
	//Domaine
	
	private class MonstresDomaineSet implements Comparator<Monstre<?>> {

		public int compare(Monstre<?> arg0, Monstre<?> arg1) {
			if(arg0.getDomaine() == arg1.getDomaine()) {
				return arg0.compareTo(arg1);
			}else {
				return arg0.getDomaine().ordinal() - arg1.getDomaine().ordinal();
			}
		}
	}
	
	public NavigableSet<Monstre<?>> updateDomaineSet() {
		for(Iterator<Monstre<?>> iter = bataille.getCampMonstres().iterator() ; iter.hasNext();) {
			this.monstresDomaineSet.add(iter.next());
		}
		return this.monstresDomaineSet;
	}
	
	public String navToString(NavigableSet<Monstre<?>> list) {
		String tri = "";
		for(Monstre<?> m : list) {
			if(tri != "") {
				tri += ", ";
			}
			tri = tri + m.getNom();
		}
		return tri;
	}
	
	public String ordreMonstresDomaine() {
		this.updateDomaineSet();
		String retour = "FEU :\n";
		retour += navToString(this.monstresDomaineSet.headSet(new Monstre<Glace>("A", Integer.MIN_VALUE, null, Domaine.GLACE), false));
		retour += "\nGLACE :\n";
		retour += navToString(this.monstresDomaineSet.subSet(new Monstre<Glace>("A", Integer.MIN_VALUE, null, Domaine.GLACE), true, new Monstre<Tranchant>("A", Integer.MIN_VALUE, null, Domaine.TRANCHANT), false));
		retour += "\nTRANCHANT :\n";
		retour += navToString(this.monstresDomaineSet.tailSet(new Monstre<Tranchant>("A", Integer.MIN_VALUE, null, Domaine.TRANCHANT), true));
		retour += "\n";
		return retour;
	}
	
	//Zone
	
	private class MonstresZoneSet implements Comparator<Monstre<?>> {

		public int compare(Monstre<?> arg0, Monstre<?> arg1) {
            if (arg0.getZoneDeCombat().ordinal() != arg1.getZoneDeCombat().ordinal())
                return arg0.getZoneDeCombat().ordinal() - arg1.getZoneDeCombat().ordinal();
            else if(arg0.getForceDeVie() != arg1.getForceDeVie())
                return arg1.getForceDeVie() - arg0.getForceDeVie();
            else
                return arg0.compareTo(arg1);
		}
	}
	
	public NavigableSet<Monstre<?>> updateMonstresZone() {
		for(Iterator<Monstre<?>> iter = bataille.getCampMonstres().iterator() ; iter.hasNext();) {
			this.monstresZoneSet.add(iter.next());
		}
		return this.monstresZoneSet;
	}
	
	public String navToStringDom(NavigableSet<Monstre<?>> list) {
		String tri = "";
		for(Monstre<?> m : list) {
			if(tri != "") {
				tri += ", ";
			}
			tri = tri + m.getNom() + " : " + m.getForceDeVie();
		}
		return tri;
	}
	
	public String ordreMonstresZone() {
		this.updateMonstresZone();
		String retour = "AERIEN :\n";
		retour += navToStringDom(this.monstresZoneSet.headSet(new Monstre<Pouvoir>("A", Integer.MAX_VALUE, ZoneDeCombat.AQUATIQUE, null), false));
		retour += "\nAQUATIQUE :\n";
		retour += navToStringDom(this.monstresZoneSet.subSet(new Monstre<Pouvoir>("A", Integer.MAX_VALUE, ZoneDeCombat.AQUATIQUE, null), true, new Monstre<Pouvoir>("A", Integer.MAX_VALUE, ZoneDeCombat.TERRESTRE, null), false));
		retour += "\nTERRESTRE :\n";
		retour += navToStringDom(this.monstresZoneSet.tailSet(new Monstre<Pouvoir>("A", Integer.MAX_VALUE, ZoneDeCombat.TERRESTRE, null), true));
		retour += "\n";
		return retour;
	}
	
	
	//Vue
	
	/*
	public Monstre<?> firstMonstreDomaine(Domaine domaine){
		this.updateDomaineSet();
		return this.monstresDomaineSet.ceiling(new Monstre<Pouvoir>("A", Integer.MIN_VALUE, null, domaine));
	}
	
	public void initMonstresDeFeu() {
		this.updateDomaineSet();
		this.monstresDeFeu = this.monstresDomaineSet.headSet(firstMonstreDomaine(Domaine.GLACE), false);
	}
	
	*/
	
	public void initMonstresDeFeu() {
		this.updateDomaineSet();
		this.monstresDeFeu = this.monstresDomaineSet.headSet(new Monstre<Pouvoir>("A", Integer.MIN_VALUE, null, Domaine.GLACE), false);
	}
	
	public void initMonstresDeGlace() {
		this.updateDomaineSet();
		this.monstresDeGlace = this.monstresDomaineSet.subSet(new Monstre<Glace>("A", Integer.MIN_VALUE, null, Domaine.GLACE), true, new Monstre<Tranchant>("A", Integer.MIN_VALUE, null, Domaine.TRANCHANT), false);
	}
	
	public void initMonstresTranchant() {
		this.updateDomaineSet();
		this.monstresTranchants = this.monstresDomaineSet.tailSet(new Monstre<Tranchant>("A", Integer.MIN_VALUE, null, Domaine.TRANCHANT), true);
	}
	
	
}
