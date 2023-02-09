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
        //Fonction qui vérifie si sur [l][c] il n'y a pas déjà un bateau (vérifier suivant la longueur du bateau)

        int verifLong = 0;

        if(d == 1) { //Si c'est horizontal
            if ((c + t) <= 10) {
                for (int i = 0; i < t; i++) {
                    if (grille[l][c + i] == 0) {
                        verifLong++;
                    }
                }
            }
        }
        else if (d == 2) {//Si c'est horizontal
            if ((l + t) <= 10) {
                for (int i = 1; i < t; i++) {
                    if (grille[l + i][c] == 0) {
                        verifLong++;
                    }
                }
            }
        }

        if(verifLong==t)
            return true;
        else
            return false;
    }

    /**
     * La procédure va mettre au hasard les 5 bateaux sur la grille "grilleOrdi"
     */
    public static void initGrilleOrdi() {
        //Utiliser la fonction posOk pour savoir si le tirage est correct

        int typeBateau = 3; //Nombre de case du bateau

        //Fonction à part?
        boolean ok = false;
        int ligne = 0, colonne = 0, numDirection = 0;
        while(!ok){
            ligne = randRange(0, 10);
            colonne = randRange(0, 10);
            numDirection = randRange(1, 3); //1 pour horizontal et 2 pour vertical

            ok = posOk(grilleOrdi, ligne, colonne, numDirection, typeBateau);
        }

        ajoutBateauOrdi(ligne, colonne, numDirection, typeBateau);
    }

    /**
     * Procédure pour ajouter un bateau à la grille ordi
     * @param l numero de ligne
     * @param c numero de colonne
     * @param t numero de direction
     * @param type type de bateau
     */
    public static void ajoutBateauOrdi(int l, int c, int t, int type){
       // System.out.println(l+" "+c+" "+numDir+" "+type);
        grilleOrdi[l][c] = type;

        for (int i = 1; i < type; i++) {
            if(t == 1) //Si c'est horizontal
                grilleOrdi[l][c + i] = type;
            else
                grilleOrdi[l+i][c] = type;
        }
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
        initGrilleOrdi();
        AfficherGrille(grilleOrdi);
    }
}