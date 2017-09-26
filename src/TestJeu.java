
import jeu.Chevre;
import jeu.Coordonnee;
import jeu.Joueur;
import jeu.Partie;

public class TestJeu {

	public static void main(String[] args) {
		
		
		System.out.println("\nBONJOUR ET BIENVENUE AU JEU DU BAGH CHAL!!!\n");
		System.out.print("Pseudo du dresseur de Tigres: ");
		String j1 = Clavier.readString(); //le joueur qui veut jouer les tigres tape son pseudo
		System.out.print("Pseudo de l'éleveur de Chèvres: ");
		String j2 = Clavier.readString(); //le joueur qui veut jouer les chevres tape son pseudo
		
		Joueur joueur1 = new Joueur(j1); //un nouveau joueur est cree, son pseudo sera alors affiche quand ce sera a son tour
		Joueur joueur2 = new Joueur(j2);
		
		Partie partie = new Partie(joueur1,joueur2); //la partie est creee avec les deux joueurs precedemment crees
		
		jouer(joueur1, joueur2, partie);
	}
	
	public static void jouer(Joueur joueur1, Joueur joueur2, Partie partie) {
		
		Joueur actuel = joueur2; //l'eleveur de chevres commence en placant une chevre
		
		while(partie.getJoueurGagnant() == null) { // tant qu'il n'y pas de gagnant, les tours s'enchainent
			
			System.out.println("NbChCapt: "+partie.getNombreChevresCapturees()); //le nombre de chevres capturees est mis à jour a chaque debut de tour
			System.out.println("NbTigEnc: "+partie.getNombreTigresEncercles());//le nombre de tigres encercles est mis à jour a chaque debut de tour
			
			partie.afficherPlateau(); //affiche le plateau avec les intersections et les tigres deja places
			
			if (actuel == joueur1) { //Si c'est au tour des tigres
				System.out.println("Au tour des tigres:");
				Coordonnee depart;
				Coordonnee arrivee;
				
				do {
					System.out.println();
					System.out.print("Depart X:"); 
					int departX = Clavier.readInt(); // lit l'indice vertical du tigre a deplacer
					System.out.print("Depart Y:");
					int departY = Clavier.readInt(); // lit l'indice horizontal du tigre a deplacer
					System.out.print("Arrivee X:");
					int arriveeX = Clavier.readInt(); // lit l'indice vertical de l'intersection de destination
					System.out.print("Arrivee Y:");
					int arriveeY = Clavier.readInt(); // lit l'indice horizontal de l'intersection de destination
					
					depart = new Coordonnee(departX,departY);
					arrivee = new Coordonnee(arriveeX,arriveeY);
				} while(!partie.deplacer(depart, arrivee, actuel));
			}
			else { //si c'est au tour des chevres
				System.out.println("Au tour des chèvres ("+Chevre.getNbRestant()+" restantes):"); // on affiche le nombre de chevres a placer
				Coordonnee coord;
				Coordonnee depart;
				Coordonnee arrivee;
				
				if (Chevre.getNbRestant() > 0) { //tant qu'il y a des chevres a placer = phase placement
					do {
						System.out.println();
						System.out.print("Coord X:");
						int coordX = Clavier.readInt();// lit l'indice vertical de l'intersection de placement
						System.out.print("Coord Y:");
						int coordY = Clavier.readInt(); // lit l'indice horizontal de l'intersection de placement
						
						coord = new Coordonnee(coordX,coordY);
					
					} while (!partie.placer(new Chevre(actuel), coord)); // place une chevre sur l'intersection dont les coordonnes ont ete saisies
					Chevre.decNbRestant(); //le nombre de chevres est decremente
				} else { //quand il n'y a plus de chevres a placer = phase deplacement
					do {
						System.out.println();
						System.out.print("Depart X:");
						int departX = Clavier.readInt(); // lit l'indice vertical de la chevre a deplacer
						System.out.print("Depart Y:");
						int departY = Clavier.readInt(); // lit l'indice horizontal de la chevre a deplacer
						System.out.print("Arrivee X:");
						int arriveeX = Clavier.readInt(); // lit l'indice vertical de l'intersection de destination
						System.out.print("Arrivee Y:"); 
						int arriveeY = Clavier.readInt(); // lit l'indice horizontal de l'intersection de destination
						
						depart = new Coordonnee(departX,departY);
						arrivee = new Coordonnee(arriveeX,arriveeY);
					} while(!partie.deplacer(depart, arrivee, actuel));
				}
			}
			
			actuel = actuel == joueur1 ? joueur2 : joueur1; //changement de joueur pour passer au tour suivant
		}
		
		partie.afficherPlateau(); // Afficher le plateau gagnant
		System.out.println("");
		System.out.println("");
		
		if (partie.getJoueurGagnant() == joueur1) { //si le joueur gagnant est le dompteur de tigres
			System.out.println("LES TIGRES ONT DOMINE GRÂCE A LEUR FEROCITE!!!");
		} else { //si le joueur gagnant est l'eleveur de chevres
			System.out.println("LES CHEVRES SONT VICTORIEUSES: L'UNION FAIT LA FORCE!!!");
		}
	}

}
