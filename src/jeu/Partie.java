/**
* Cette classe represente le plateau de jeu ainsi que le deroulement et la finalite du jeu
*
* @author Marina Montfort et Gilles Lacemon
*@version 1.0
*/

package jeu;
import liste.Liste;

public class Partie {

	private Joueur j1;
	private Joueur j2;
	
	private int nombreChevresCapturees = 0;
	
	private Intersection[][] plateau;


/**constructeur de la classe Partie
*@param joueur1 qui represente le premier joueur (tigres dans la classe test)
*@param joueur2 qui represente le deuxième joueur (chevres dans la classe test)
*/	
	public Partie(Joueur joueur1, Joueur joueur2) {
		this.j1 = joueur1;
		this.j2 = joueur2;
		
		plateau = new Intersection[5][5]; // definit le nombre d'intersections en longueur et en hauteur
		
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				plateau[i][j] = new Intersection(); //chaque case de la matrice est une intersection
			}
		}
		
		definirIntersectionDiagonal(); //les intersections accessibles en diagonal sont definies des le debut de la partie
		

		//place les tigres dans les coins en début de partie
		plateau[0][0].setPion(new Tigre(joueur1)); 
		plateau[0][4].setPion(new Tigre(joueur1));
		plateau[4][0].setPion(new Tigre(joueur1));
		plateau[4][4].setPion(new Tigre(joueur1));
	}
	

/** methode qui permet d'afficher le plateau avec les intersections et les liens qui representent les deplacements possibles dans le *terminal
*/
	public void afficherPlateau() {
		System.out.println();
		for (int i = 0; i < 5; i++) {
			for (int j = 0;  j < 5; j++) {
				if (j != 4)
					System.out.print(plateau[i][j].toString()+"  -  "); //affiche les liens horizontaux entre intersections
				else
					System.out.print(plateau[i][j].toString());
			}
			System.out.println();
			if (i % 2 == 0 && i != 4) // affiche les diagonales entre le trois premieres lignes
				System.out.println("\n|  \\  |  /  |  \\  |  /  |");
			else if (i != 4) // affiche les diagonales entre les deux dernieres lignes
				System.out.println("\n|  /  |  \\  |  /  |  \\  |");
			System.out.println();
		}
	}
	

/** methode qui permet de savoir quelles intersections permettent de se deplacer en diagonal
*/
	public void definirIntersectionDiagonal() {
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				plateau[i][j].setAccesDiagonal(((i+j) % 2) == 0); //si la somme de l'indice horizontal et de l'indice vertical d'une intersection est paire, alors un deplacement diagonal vers une autre intersection est possible (exemple : (1;3) est peut mener vers une intersection par deplacement diagonal
			}
		}
	}
	

/** methode qui permet de savoir si un deplacement est possible selon les possibilités de deplacement de chaque type de pion, en tenant *compte des intersections existantes sur le plateau
*/
	public boolean deplacementPossible(Coordonnee depart, Coordonnee arrivee, Joueur joueur) {
		if (depart.getX() >= 0 && depart.getX() <= 4 && depart.getY() >= 0 && depart.getY() <= 4 && arrivee.getX() >= 0 && arrivee.getX() <= 4 && arrivee.getY() >= 0 && arrivee.getY() <= 4 ) { //determination des limites du plateau

			Intersection inter = plateau[depart.getX()][depart.getY()];
			if (!inter.estLibre()) {
				Pion pion = inter.getPion();
				
				if (pion.getJoueur() == joueur) {

					Liste deplacements = pion.getDeplacement(depart,arrivee,inter.getAccesDiagonal());
					
					if (pion.longueurDeplacement(depart, arrivee) == 1 && plateau[arrivee.getX()][arrivee.getY()].estLibre()) { //deplacement de longueur 1
						return true;
					} else if (pion.longueurDeplacement(depart, arrivee) == 2 && plateau[arrivee.getX()][arrivee.getY()].estLibre() && pion.isPredateur() && inter.getAccesDiagonal()){ //deplacement de longueur 2 uniquement pour un tigre
						
						//System.out.println("["+depart.getX()+":"+depart.getY()+"] ["+arrivee.getX()+":"+arrivee.getY()+"]");
						Coordonnee coordMid = (Coordonnee) deplacements.get(0); 
						Intersection inter2 = plateau[coordMid.getX()][coordMid.getY()];
						//System.out.println(coordMid);
						if (!inter2.estLibre() && inter2.getPion() instanceof Chevre) { //seulement si une chevre est presente sur l'intersection intermediaire
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	


/** methode qui permet d'effectuer des deplacements et de retirer les chevres si elles sont capturees par un tigre
*/
	public boolean deplacer(Coordonnee depart, Coordonnee arrivee, Joueur joueur) {
		if (deplacementPossible(depart,arrivee,joueur)) { // si les preconditions sont validees
			Intersection interDepart = plateau[depart.getX()][depart.getY()];
			Intersection interArrivee = plateau[arrivee.getX()][arrivee.getY()];
			Pion pion = interDepart.getPion();
			
			interArrivee.setPion(pion);
			interDepart.setPion(null);
			
			if (pion.longueurDeplacement(depart, arrivee) == 2) {
				Liste deplacements = pion.getDeplacement(depart,arrivee,interDepart.getAccesDiagonal());
				Coordonnee coordMid = (Coordonnee) deplacements.get(0);
				Intersection interMilieu = plateau[coordMid.getX()][coordMid.getY()];
				interMilieu.setPion(null); // la chevre est capturee, l'intersection du milieu devient nulle
				nombreChevresCapturees++;
			}
			
			return true;
		} else {
			return false;
		}
	}


/** selecteur qui permet de connaitre le joueur qui gagne la partie 
@return le joueur gagnant ou null si la partie n'est pas fini
*/	
	public Joueur getJoueurGagnant() {
		if (this.getNombreChevresCapturees() == 7) // le dompteur de tigres gagne si 7 chevres sont capturees  
			return j1;
		if (this.getNombreTigresEncercles() == 4) // l'eleveur de chevres gagne si les 4 tigres sont encercles
			return j2;
		return null;
	}


/** selecteur qui permet de connaitre le nombre de chevres capturees par un tigre depuis le debut de la partie
@return le nombre de chevres capturees
*/	
	public int getNombreChevresCapturees() {
		return nombreChevresCapturees;
	}
	

/** selecteur qui permet de connaitre le nombre de tigres encercles par des chevres depuis le debut de la partie
@return le nombre de tigres encercles
*/
	public int getNombreTigresEncercles() {
		int nbTigresE = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Intersection inter = plateau[i][j];
				
				if (!inter.estLibre()) {
					Pion pion = inter.getPion();
					
					if (pion instanceof Tigre) {
						boolean possible = false;
						
						for (int x = i - 2; x <= i + 2; x++) {
							for (int y = j - 2; y <= j + 2; y++) {
								//System.out.println("["+x+":"+y+"]");
								possible |= this.deplacementPossible(new Coordonnee(i,j), new Coordonnee(x,y), j1); // possible devient faux si il n'y a plus de deplacements possibles
							}
						}
						
						if (!possible) nbTigresE ++; // si il n'y a plus de deplacements possibles pour un tigre, celui-ci est encercle
					}
				}
			}
		}
		return nbTigresE;
	}


/** methode qui permet de placer des pions (uniquement des chevres dans le test) sur une intersection choisie
@return le nombre de tigres encercles
*/
	public boolean placer(Pion pion, Coordonnee coord) {
		if (coord.getX() >= 0 && coord.getX() <= 4 && coord.getY() >= 0 && coord.getY() <= 4) {
			if (plateau[coord.getX()][coord.getY()].estLibre()) { 
				plateau[coord.getX()][coord.getY()].setPion(pion); // une intersection prend un nouveau pion si celle-ci existe sur le plateau et qu'elle est libre
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "Partie";
	}
}
