/**
* Cette classe concerne les coordonnees utilisees pour toutes les autres classes 
*
* @author Marina Montfort et Gilles Lacemon
*@version 1.0
*/

package jeu;

public class Coordonnee {

		private int x;
		private int y;
		

/**constructeur de la classe Coordonnee
*@param x qui represente l'indice vertical 
*@param y qui represente l'indice horizontal
*/
		public Coordonnee(int x, int y) {
			this.x = x;
			this.y = y;
		}


/** methode qui permet tester si deux Coordonnees sont identiques
@return true si les deux Coordonnees sont identiques ou false dans le cas contraire
*/		
		public boolean equals(Object o) {
			if(o instanceof Coordonnee) { //compare les coordonnees à d'autres coordonnes entrees en parametre
				if (this.x == ((Coordonnee)o).getX() && this.y == ((Coordonnee)o).getY()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}


/** selecteur qui permet d'acceder a l'indice vertical
@return l'indice vertical
*/
		public int getX() {
			return x;
		}

/** selecteur qui permet d'acceder a l'indice horizontal
@return l'indice horizontal
*/
		public int getY() {
			return y;
		}


/** methode qui permet d'afficher les coordonnees
@return une chaine de caractere contenant les coordonnees
*/
		public String toString() {
			return "Coordonnee [x=" + x + ", y=" + y + "]";
		}
}
