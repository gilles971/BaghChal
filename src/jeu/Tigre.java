/**
* Cette classe represente un tigre, classe fille de pion
*
* @author Marina Montfort et Gilles Lacemon
*@version 1.0
*/

package jeu;

public class Tigre extends Pion {


/**constructeur de la classe Tigre
*@param joueur qui represente le dompteur de tigres
*/
	public Tigre(Joueur joueur) {
		super(joueur);
		
		this.predateur = true; // le tigre est un predateur
	}

}
