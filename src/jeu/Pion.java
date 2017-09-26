/**
* Cette classe represente un pion de tout type, ainsi que les contraintes de deplacement et le joueur associes
*
* @author Marina Montfort et Gilles Lacemon
*@version 1.0
*/

package jeu;

import liste.Liste;

public class Pion {

	protected boolean predateur;
	protected Joueur joueur;


/**constructeur de la classe Pion
*@param joueur qui represente le joueur associe au pion
*/	
	public Pion(Joueur joueur) {
		this.joueur = joueur;
	}


/** selecteur qui permet de connaitre le joueur associe a un pion
@return le pseudo du joueur
*/
	public Joueur getJoueur() {
		return joueur;
	}
	

/** methode qui permet de savoir si un pion est un tigre ou une chevre
*/
	public boolean isPredateur() {
		return predateur;
	}


/** modifieur qui permet d'associer un joueur a un pion
@return le pseudo du joueur
*/
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}


/** selecteur qui permet de connaitre tous les deplacmements d'un pion
@return la liste des coordonnees de toutes les intersections par lesquelles un pion est passé
*/	
	public Liste getDeplacement(Coordonnee depart, Coordonnee arrivee, boolean diagonalAutorise) {
		if (depart.getX() == arrivee.getX() && depart.getY() != arrivee.getY()) { 
			return getCheminHorizontal(depart, arrivee); // definition d'un deplacement horizontal
		} else if (depart.getX() != arrivee.getX() && depart.getY() == arrivee.getY()) {
			return getCheminVertical(depart, arrivee); // definition d'un deplacement vertical
		} else if (depart.getX() != arrivee.getX() && depart.getY() != arrivee.getY() && diagonalAutorise) {
			return getCheminDiagonal(depart, arrivee); // definition d'un deplacement diagonal
		}
		return null;
	}


/** selecteur qui permet de connaitre tous les deplacements diagonals d'un pion
@return la liste des coordonnees de toutes les intersections par lesquelles un pion est passé en faisant un deplacement diagonal
*/
	private Liste getCheminDiagonal(Coordonnee depart, Coordonnee arrivee) {
		if (!this.predateur) { //cas chevre
			if (longueurDeplacement(depart,arrivee) == 1) {
				Liste liste = new Liste();
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			}
		} else if (this.predateur) { //cas tigre
			if (longueurDeplacement(depart,arrivee) == 1) { //cas sans capture de chevre
				Liste liste = new Liste(); 
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			} else if (longueurDeplacement(depart,arrivee) == 2) {  //cas capture de chevre
				Liste liste = new Liste();
				liste.add(new Coordonnee((depart.getX() + arrivee.getX())/2, (depart.getY() + arrivee.getY())/2));
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			}
		}

		return null;
	}


/** selecteur qui permet de connaitre tous les deplacements verticaux d'un pion
@return la liste des coordonnees de toutes les intersections par lesquelles un pion est passé en faisant un deplacement vertical
*/
	private Liste getCheminVertical(Coordonnee depart, Coordonnee arrivee) {
		if (!this.predateur) { //cas chevre
			if (longueurDeplacement(depart,arrivee) == 1) {
				Liste liste = new Liste();
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			}
		} else { //cas tigre
			if (longueurDeplacement(depart,arrivee) == 2) { //cas capture de chevre
				Liste liste = new Liste();
				liste.add(new Coordonnee((depart.getX() + arrivee.getX())/2, arrivee.getY()));
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			} else if (longueurDeplacement(depart,arrivee) == 1){ //cas sans capture de chevre
				Liste liste = new Liste();
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			}
		}
		return null;
	}



/** selecteur qui permet de connaitre tous les deplacements horizontaux d'un pion
@return la liste des coordonnees de toutes les intersections par lesquelles un pion est passé en faisant un deplacement horizontal
*/
	private Liste getCheminHorizontal(Coordonnee depart, Coordonnee arrivee) {
		if (!this.predateur) { //cas chevre
			if (longueurDeplacement(depart,arrivee) == 1) {
				Liste liste = new Liste();
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			}
		} else { //cas tigre
			if (longueurDeplacement(depart,arrivee) == 2) { //cas capture de chevre
				Liste liste = new Liste();
				liste.add(new Coordonnee(arrivee.getX(), (depart.getY() + arrivee.getY())/2));
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			} else if (longueurDeplacement(depart,arrivee) == 1){ //cas sans capture de chevre
				Liste liste = new Liste();
				liste.add(arrivee); //la liste prend les nouvelles coordonnees d'arrivee du deplacement
				return liste;
			}
		}
		return null;
	}
	


/** methode qui permet de connaitre la longueur d'un deplacement selon le nombre d'intersections parcourues au cours d'un deplacement
@return a longueur du deplacement (1 ou 2)
*/
	public int longueurDeplacement(Coordonnee depart, Coordonnee arrivee) {
		if (depart.getX() == arrivee.getX() && depart.getY() != arrivee.getY()) { //chemin vertical (seul Y est modifié)
			return Math.abs(depart.getY() - arrivee.getY());  // Y est modifie de +1, -1, +2 ou -2
		} else if (depart.getX() != arrivee.getX() && depart.getY() == arrivee.getY()) { // chemin horizontal (seul X est modifié)
			return Math.abs(depart.getX() - arrivee.getX()); // X est modifie de +1, -1, +2 ou -2
		} else if (depart.getX() != arrivee.getX() && depart.getY() != arrivee.getY()) { // chemin diagonal
			return deplacementDiagonal(depart,arrivee) ? Math.abs(depart.getY() - arrivee.getY()) : -1; // si c'est un deplacement diagonal alors la longueur du deplacement est equivalente à celle d'un simple deplacement vertical ou horizontal 
		}
		return 0;
	}
	

/** methode qui permet de savoir si un deplacement est diagonal
@return true si le deplacement est diagonal
*/
	public boolean deplacementDiagonal(Coordonnee depart, Coordonnee arrivee) {
		int absX = Math.abs(depart.getX() - arrivee.getX());
		int absY = Math.abs(depart.getY() - arrivee.getY());
		return (absX == absY); // si la longueur du deplacement entre le X d'arrivee et le X de depart est égale à celle entre le Y d'arrivee et le Y de depart alors c'est un deplacement diagonal
	}
	
}
