/**
* Cette classe represente un joueur qui est concerne par un type de pion
*
* @author Marina Montfort et Gilles Lacemon
*@version 1.0
*/

package jeu;

public class Joueur {
	
	private String pseudo;

/**constructeur de la classe Joueur
*@param pseudo qui represente le pseudo associe 
*/
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
	}


/** methode qui permet de savoir si deux joueurs ont le meme pseudo
@ return true si les deux pseudos sont égaux et false dans le cas contraire
*/	
	public boolean equals(Object o) {
		if (o instanceof Joueur) {
			if (this.pseudo.equals(((Joueur)o).getPseudo())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


/** selecteur qui permet de connaitre le pseudo d'un joueur 
@return le pseudo du joueur
*/
	public String getPseudo() {
		return pseudo;
	}

}
