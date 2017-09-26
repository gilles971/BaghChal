/**
* Cette classe concerne la chevre, classe fille de pion, permattant de la distinguer d'un tigre
*
* @author Marina Montfort et Gilles Lacemon
*@version 1.0
*/

package jeu;

public class Chevre extends Pion {
	
	private static int nbRestant = 20; // nombre de chevres qu'il reste a placer au debut de la partie


/**constructeur de la classe Chevre
*@param joueur qui represente l'eleveur de chevres
*/
	public Chevre(Joueur joueur) {
		super(joueur);
		
		this.predateur = false; // la chevre n'est pas un predateur
	}


/** selecteur qui permet de connaitre le nombre de chevres qu'il reste à placer avant la phase deplacement du jeu 
@return le nombre de chevres restantes 
*/
	public static int getNbRestant() {
		return nbRestant;
	}


/** methode qui permet de decrementer le nombre de chevres restantes à chaque tour jusqu'au debut de la phase de deplacement 
*/
	public static void decNbRestant() {
		Chevre.nbRestant = nbRestant-1;
	}

}
