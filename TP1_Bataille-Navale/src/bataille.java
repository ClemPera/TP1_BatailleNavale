/**
 * @author Clément Pera
 */
import java.util.Random;
import java.util.Scanner;
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
        int longueur = 0;

        if(t == 1){
            longueur = 5;
        } else if(t == 2){
            longueur = 4;
        } else if(t == 3){
            longueur = 3;
        } else if(t == 4){
            longueur = 3;
        } else if(t == 5){
            longueur = 2;
        }

        int verifLong = 0;

        if(d == 1) { //Si c'est horizontal
            if ((c + longueur) <= 10) {
                for (int i = 0; i < longueur; i++) {
                    if (grille[l][c + i] == 0) {
                        verifLong++;
                    }
                }
            }
        } else if (d == 2) {//Si c'est vertical
            if ((l + longueur) <= 10) {
                for (int i = 0; i < longueur; i++) {
                    if (grille[l + i][c] == 0) {
                        verifLong++;
                    }
                }
            }
        }

        if(verifLong==longueur)
            return true;
        else
            return false;
    }

    /**
     * La procédure va mettre au hasard les 5 bateaux sur la grille "grilleOrdi"
     */
    public static void initGrilleOrdi() {
        boolean ok = false;
        int ligne = 0, colonne = 0, numDirection = 0;
        for (int i = 1; i <= 5; i++)
        {
            while (!ok) {
                ligne = randRange(0, 10);
                colonne = randRange(0, 10);
                numDirection = randRange(1, 3); //1 pour horizontal et 2 pour vertical

                ok = posOk(grilleOrdi, ligne, colonne, numDirection, i);
            }
            ajoutBateau(grilleOrdi, ligne, colonne, numDirection, i);
            ok=false;
        }
    }

    /**
     * Procédure pour ajouter un bateau à la grille ordi
     *
     * @param l numero de ligne
     * @param c numero de colonne
     * @param d numero de direction
     * @param type type de bateau
     */
    public static void ajoutBateau(int [][]grille,int l, int c, int d, int type){
        int longueur = 0;

        if(type == 1){
            longueur = 5;
        } else if(type == 2){
            longueur = 4;
        } else if(type == 3){
            longueur = 3;
        } else if(type == 4){
            longueur = 3;
        } else if(type == 5){
            longueur = 2;
        }

        grille[l][c] = type;

        for (int i = 0; i < longueur; i++) {
            if(d == 1) { //Si c'est horizontal
                grille[l][c + i] = type;
            }
            else {
                grille[l + i][c] = type;
            }
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

    public static void initGrilleJeu(){
        //Demander pour chaque bateau puis les placer avec un while dans ligne'idéal et les vérif avec posOk
        int ligne = 0;
        int colonne = 0;
        int direction = 0;
        boolean ok = false;

        for(int i = 1; i <=5; i++) {
            while (!ok) {
                AfficherGrille(grilleJeu);
                System.out.println();
                ligne = questionUtilisateur(0, i); //Quel ligne pour un porte-avion
                colonne = questionUtilisateur(1, i); //Quel ligne pour un porte-avion
                direction = questionUtilisateur(2, i); //Quel ligne pour un porte-avion

                ok = posOk(grilleJeu, ligne, colonne, direction, i);
                if (!ok) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("L'entrée n'est pas correct, veuillez ressayer");
                }
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ajoutBateau(grilleJeu, ligne, colonne, direction, i);
            ok = false;
        }
    }

    /**
     * Pose une question à l'utilisateur par rapport à la lettre/colonne ou direction
     *
     * @param lct 0 = ligne, 1 = colonne, 2 = direction
     * @param type 0 = porte avion, 1 = croiseur, 2 = contre-torpilleur, 3 = sous-marin, 4 = torpilleur
     * @return
     */
    public static int questionUtilisateur(int lct, int type){
        String str = "";
        Scanner entreeUtilisateur = new Scanner(System.in);
        String longstr = "";
        int longint = 0;
        boolean ok = false;

        while(!ok) {
            ok = true;
            if(lct == 0){
                str = "Donner la ligne pour le ";
            } else if(lct == 1){
                str = "Donner la colonne pour le ";
            } else if(lct == 2){
                str = "Donner la direction pour le ";
            }

            if (type == 1) {
                System.out.println(str + "porte-avion : ");
            } else if (type == 2) {
                System.out.println(str + "croiseur : ");
            } else if (type == 3) {
                System.out.println(str + "contre-torpilleur : ");
            } else if (type == 4) {
                System.out.println(str + "sous-marin : ");
            } else if (type == 5) {
                System.out.println(str + "torpilleur : ");
            }

            longstr = entreeUtilisateur.nextLine();
            //Vérifier que toutes les inputs soient corrects (avec while et un ok;?)

            if (lct == 0){
                try {
                    longint = Integer.parseInt(longstr);
                }catch(NumberFormatException e){ok = false;}
                if (longint < 0 || longint > 9) { //Vérification si la valeur de longint est correct
                    ok = false;
                }
            }
            else if (lct == 1) {
                longint = Character.toUpperCase(longstr.charAt(0)) - 65;
                if (longint < 0 || longint > 9) { //Vérification si la valeur de longint est correct
                    ok = false;
                }
            }
            else if (lct == 2){
                try {
                    longint = Integer.parseInt(longstr);
                }catch(NumberFormatException e){ok = false;}
                if (longint <= 0 && longint > 2) { //Vérification si la valeur de longint est correct
                    ok = false;
                }
            }

            if(!ok){
                System.out.println("Il y a une erreur dans votre entrée, veuillez réessayer");
            }
        }

        return longint;
    }


    /**
     * Fonction qui retourne "vrai" si le bateau est coulé sinon "faux"
     *
     * @param grille Sur quel grille vérifier
     * @param type numéro du bateau compris entre 1 et 5
     */
    public static boolean couler(int [][]grille, int type) {
        for (int l = 0; l < 10; l++) {
            for (int c = 0; c < 10; c++) {
                if (grille[l][c] == type) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Affiche soit « Touché », soit « Coulé » (en indiquant de quel bateau il s’agit), soit « À l’eau ». Met aussi la grille à jour
     *
     * @param grille Sur quel grille vérifier
     * @param l ligne
     * @param c colonne
     */
    public static void mouvement(int [][]grille, int l, int c){
        int numBateau = 0;

        if(grille[l][c] != 0 && grille[l][c] != 6){
            numBateau = grille[l][c];
            grille[l][c] = 6;

            if(couler(grille, numBateau)){
                System.out.print("Le bateau " + numBateau + " a été coulé!");
            } else{
                System.out.print("Touché");
            }
        }
        else{
            System.out.print("A l'eau");
        }
    }

    /**
     * renvoie un tableau avec 2 entiers tirées au hasard entre 0 et 9
     *
     * @return un tableau avec 2 entiers tirées au hasard entre 0 et 9
     */
    public static int[] tirOrdinateur(){
        int[] intTab = new int[2];

        intTab[0] = randRange(0,10);
        intTab[1] = randRange(0,10);

        return intTab;
    }


    public static boolean vainqueur(int [][]grille){
        for (int l = 0; l < 10; l++) {
            for (int c = 0; c < 10; c++) {
                if (grille[l][c] != 0 && grille[l][c] != 6) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int[] tirJoueur(){
        int[] intTab = new int[2];
        boolean ok = false;
        Scanner entreeUtilisateur = new Scanner(System.in);

        while(!ok) {
            ok = true;

            System.out.println("Rentrer la ligne où attaquer : ");
            try {
                intTab[0] = Integer.parseInt(entreeUtilisateur.nextLine());
            } catch (NumberFormatException e) {
                ok = false;
            }
            if (intTab[0] < 0 || intTab[0] > 9) { //Vérification si la valeur de longint est correct
                ok = false;
            }
            if(ok){
                System.out.println("Rentrer la colonne où attaquer : ");
                try {
                    intTab[1] = Character.toUpperCase(entreeUtilisateur.nextLine().charAt(0)) - 65;
                } catch (NumberFormatException e) {
                    ok = false;
                }
                if (intTab[1] < 0 || intTab[1] > 9) { //Vérification si la valeur de longint est correct
                    ok = false;
                }
            }

            if (!ok) {
                System.out.println("Il y a une erreur dans votre entrée, veuillez réessayer");
            }

        }

        return intTab;
    }

    public static void engagement(){
        boolean fin = false;
        int[] ordiTab = new int[0];
        int[] joueurTab = new int[0];

        initGrilleOrdi();
        initGrilleJeu();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        while(!fin) {
            //Tir de l'ordinateur
            System.out.println("L'ordinateur attaque! : ");
            ordiTab = tirOrdinateur();
            mouvement(grilleJeu,ordiTab[0],ordiTab[1]);

            System.out.println();
            System.out.println();
            //Vérification s'il y a un vainqueur
            if (vainqueur(grilleJeu)) {
                System.out.println("L'ordinateur a gagné!");
                fin = true;
            }else{
                System.out.println("Grille ordi");
                AfficherGrille(grilleOrdi);

                System.out.println();
                System.out.println("Grille joueur");
                AfficherGrille(grilleJeu);
                System.out.println();
                //Tir du joueur
                joueurTab = tirJoueur();
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("Vous avez attaqué! : ");
                mouvement(grilleOrdi, joueurTab[0], joueurTab[1]);
                System.out.println();

                //Vérification s'il y a un vainqueur
                if (vainqueur(grilleOrdi)) {
                    System.out.println("Vous avez gagné!");
                    fin = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        engagement();
    }
}