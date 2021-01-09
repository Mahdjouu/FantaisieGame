package protagoniste;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import attaque.Arme;
import attaque.Pouvoir;
import bataille.*;

public class Homme extends EtreVivant{
	private Arme armeChoisie;
	private Map<ZoneDeCombat, List<Arme>> listArme = new EnumMap<>(ZoneDeCombat.class);
	private NavigableSet<Arme> armesTriees = new TreeSet<Arme>();
	
	public Homme(String nom) {
		super(nom, 70);
	}
	
	public void rejointBataille(Bataille bataille) {
		super.rejointBataille(bataille);
		this.bataille.ajouter(this);
	}
	
	public void mourir() {
		this.bataille.eliminer(this);
	}

	public String toString() {
		return "Homme [nom=" + getNom() + ", forceDeVie=" + getForceDeVie() + "]";
	}
	
	public void ajouterArmes(Arme... arme) {
		for(Arme a : arme) {
			Set<ZoneDeCombat> zdc = a.getZoneDeCombat();
			for(ZoneDeCombat zoneDeCombat : zdc){
				List<Arme> list;
				if(listArme.containsKey(zoneDeCombat)) {
					list = listArme.get(zoneDeCombat);
				}else {
					list = new ArrayList<>();
				}
				list.add(a);

				this.listArme.put(zoneDeCombat, list);
			}
		}
	}
	
	public void supprimerArme(Arme arme) {
		for(Iterator<ZoneDeCombat> iter = arme.getZoneDeCombat().iterator(); iter.hasNext() ;) {
			iter.next();
			List<Arme> list;
			if(listArme.containsKey(iter)) {
				list = listArme.get(iter);
			}else {
				list = new ArrayList<>();
			}
			list.remove(arme);
			this.listArme.get(iter).remove(arme);
		}
	}
	
	public Arme getArmeChoisie() {
		return armeChoisie;
	}
	
	public Arme choisirArme(Monstre<? extends Pouvoir> monstre){
		ZoneDeCombat zoneMonstre = monstre.getZoneDeCombat();
		//System.out.println(listArme + "\n");
		if(listArme.containsKey(zoneMonstre)) {
			List<Arme> list = this.listArme.get(zoneMonstre);
			for(Arme a : list) {
				this.armesTriees.add(a);
			}
			if(!this.armesTriees.isEmpty()) {
				NavigableSet<Arme> armesAdaptees = this.armesTriees.tailSet(new KeyArme(monstre.getForceDeVie()), false);
				if(!armesAdaptees.isEmpty()) {
					armeChoisie = armesAdaptees.first();
				}else {
					armeChoisie = armesTriees.last();
				}
				return armeChoisie;
			}
		}
		//System.out.println(this + " n'a pas pris d'arme\n");
		return null;
	}
}
