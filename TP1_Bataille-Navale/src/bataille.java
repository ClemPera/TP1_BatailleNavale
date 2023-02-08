/**
 * @author Clément Pera
 *
 */
import java.util.Random;
public class bataille {
    public static Random rand = new Random();

    /**
     * Tire des entiers aléatoire entre a inclus et b exclu
     * @param a à partir de se nombre
     * @param b à jusqu'à ce nombre b exclu
     * @return retourne un nombre entier aléatoire entre a et b
     */
    public static int randRange(int a, int b){
        return rand.nextInt(b-a)+a;
    }
    public static int[][]grilleOrdi = new int [10][10];
    public static int[][]grilleJeu = new int [10][10];

    /**
     * Vérifie si la position de placement du bateau est correct
     *
     * @param grille une grille
     * @param l un numéro de ligne
     * @param c un numéro de colonne (entre 0 et 9)
     * @param d un entier codant une direction (1 pour horizontal et 2 pour vertical)
     * @param t donnant le nombre de cases d'un bateau
     *
     * @return Renvoie "vraie" si on peut mettre le bateau sur les cases correspondantes et sinon renvoie "faux"
     */
    public static boolean posOk(int [][]grille, int l, int c, int d, int t){
        if(d == 1) { //Si horizontal
            if(l+t < 10)
                return true;
        }
        else if(d == 2) { //Si vertical
            if(c+t < 10)
                return true;
        }
        return false;
    }

    /**
     * La procédure va mettre au hasard les 5 bateaux sur la grille "grilleOrdi"
     */
    public static void initGrilleOrdi() {
        int ligne = randRange(0,10);
        int colonne = randRange(0,10);
        int numDirection = randRange(1,3); //1 pour horizontal et 2 pour vertical

        //Utiliser la fonction posOk pour savoir si le tirage est correct
        boolean ok = posOk(grilleOrdi,ligne,colonne,numDirection,5);
        while(!ok)
            ok = posOk(grilleOrdi,ligne,colonne,numDirection,5);

    }

    /**
     * Affiche une grille de jeu
     *
     * @param grille grille à afficher
     */
    public static void AfficherGrille(int [][]grille){
        System.out.print("    ");
        for(char s = 'A'; s <= 'J'; s++){ //Affiche une ligne avec les lettre de A à J
            System.out.print(s);
            System.out.print(' ');
        }

        System.out.println();
        System.out.println("   --------------------");

        for(int n = 0; n < 10; n++){ //Affiche la grille avec les nombres de 0 à 9
            System.out.print(n);
            System.out.print(" | ");

            for(int i = 0; i < 10; i++){
                System.out.print(grille[n][i]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        AfficherGrille(grilleOrdi);
    }
}