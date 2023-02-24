import java.util.Random;
import java.util.Scanner;

/**
 * Nom du programme : Bataille Navale
 * @author Clément Pera
 * Date : 06 Mars 2023
 * Résumé : Programme qui permet de jouer à la bataille navale contre un ordinateur
 * L'ordinateur et le joueur disposent d’une grille de 10
 * cases sur 10 cases, les colonnes de cette grille sont indiquées par une lettre de A à J et les
 * lignes sont numérotées de 1 à 10. Sur cette grille sont placés 5 bateaux en horizontal ou en
 * vertical. Le but de chaque joueur est de couler tous les bateaux de l’autre joueur
 */
public class bataille {
    public static Random rand = new Random();

    /**
     * Tire des entiers aléatoire entre a inclus et b exclu
     * @param a à partir de ce nombre
     * @param b à jusqu'à ce nombre b exclu
     * @return retourne un nombre entier aléatoire entre a et b
     */
    public static int randRange(int a, int b){
        return rand.nextInt(b-a)+a;
    }
    public static int[][] grilleOrdi = new int [10][10];
    public static int[][] grilleJeu = new int [10][10];

    /**
     * Vérifie si la position de placement du bateau est correct
     *
     * @param grille une grille
     * @param ligne un numéro de ligne
     * @param colonne un numéro de colonne (entre 0 et 9)
     * @param direction un entier codant une direction (1 pour horizontal et 2 pour vertical)
     * @param type donnant le type du bateau
     *
     * @return Renvoie "vraie" si on peut mettre le bateau sur les cases correspondantes et sinon renvoie "faux"
     */
    public static boolean posOk(int [][]grille, int ligne, int colonne, int direction, int type){
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

        int verifLong = 0;

        if(direction == 1) { //Si c'est horizontal
            if ((colonne + longueur) <= 10) {
                for (int i = 0; i < longueur; i++) {
                    if (grille[ligne][colonne + i] == 0) {
                        verifLong++;
                    }
                }
            }
        } else if (direction == 2) {//Si c'est vertical
            if ((ligne + longueur) <= 10) {
                for (int i = 0; i < longueur; i++) {
                    if (grille[ligne + i][colonne] == 0) {
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
     * La procédure va placer au hasard les 5 bateaux sur la grille "grilleOrdi"
     */
    public static void initGrilleOrdi() {
        boolean ok = false;
        int ligne = 0, colonne = 0, numDirection = 0;
        for (int type = 1; type <= 5; type++)
        {
            while (!ok) {
                ligne = randRange(0, 10);
                colonne = randRange(0, 10);
                numDirection = randRange(1, 3); //1 pour horizontal et 2 pour vertical

                ok = posOk(grilleOrdi, ligne, colonne, numDirection, type);
            }
            ajoutBateau(grilleOrdi, ligne, colonne, numDirection, type);
            ok=false;
        }
    }

    /**
     * Procédure pour ajouter un bateau à la grille spécifié
     *
     * @param grille Quelle grille ajouter un bateau
     * @param ligne numéro de ligne
     * @param colonne numéro de colonne
     * @param direction numéro de direction
     * @param type type de bateau
     */
    public static void ajoutBateau(int [][]grille,int ligne, int colonne, int direction, int type){
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

        grille[ligne][colonne] = type;

        for (int i = 0; i < longueur; i++) {
            if(direction == 1) { //Si c'est horizontal
                grille[ligne][colonne + i] = type;
            }
            else {
                grille[ligne + i][colonne] = type;
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
        for(char lettre = 'A'; lettre <= 'J'; lettre++){ //Affiche une ligne avec les lettres de A à J
            System.out.print(lettre);
            System.out.print(' ');
        }

        System.out.println();
        System.out.println("   --------------------");

        for(int ligne = 0; ligne < 10; ligne++){ //Affiche la grille avec les nombres de 0 à 9
            System.out.print(ligne);
            System.out.print(" | ");

            for(int colonne = 0; colonne < 10; colonne++){
                System.out.print(grille[ligne][colonne]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Affiche la grille avec des point d'interrogation partout sauf où un bateau a été touché
     *
     * @param grille Grille à utilisé
     */
    public static void AfficherGrilleInterrogation(int [][]grille){
        System.out.print("    ");
        for(char lettre = 'A'; lettre <= 'J'; lettre++){ //Affiche une ligne avec les lettres de A à J
            System.out.print(lettre);
            System.out.print(' ');
        }

        System.out.println();
        System.out.println("   --------------------");

        for(int ligne = 0; ligne < 10; ligne++){ //Affiche la grille avec les chiffres de 0 à 9
            System.out.print(ligne);
            System.out.print(" | ");

            for(int colonne = 0; colonne < 10; colonne++){
                if(grille[ligne][colonne] == 6){
                    System.out.print(grille[ligne][colonne]);
                }
                else{
                    System.out.print("?");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Initialise la grille de jeu de l'utilisateur
     */
    public static void initGrilleJeu(){
        int ligne = 0;
        int colonne = 0;
        int direction = 0;
        boolean ok = false;

        for(int i = 1; i <=5; i++) {
            while (!ok) {
                AfficherGrille(grilleJeu);
                System.out.println();
                ligne = questionUtilisateur(0, i); //Quelle ligne pour un porte-avion
                colonne = questionUtilisateur(1, i); //Quelle ligne pour un porte-avion
                direction = questionUtilisateur(2, i); //Quelle ligne pour un porte-avion

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
     * Pose une question à l'utilisateur rapport à la lettre, colonne ou direction
     *
     * @param lcd 0 = Ligne, 1 = Colonne, 2 = Direction
     * @param type 0 = porte avion, 1 = croiseur, 2 = contre-torpilleur, 3 = sous-marin, 4 = torpilleur
     * @return retourne un entier indiquant la ligne, colonne ou direction que l'utilisateur a rentré
     */
    public static int questionUtilisateur(int lcd, int type){
        String str = "";
        Scanner entreeUtilisateur = new Scanner(System.in);
        String longstr;
        int longint = 0;
        boolean ok = false;

        while(!ok) {
            ok = true;
            if(lcd == 0){
                str = "Donner la ligne pour le ";
            } else if(lcd == 1){
                str = "Donner la colonne pour le ";
            } else if(lcd == 2){
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

            if (lcd == 0){
                try {
                    longint = Integer.parseInt(longstr);
                }catch(NumberFormatException e){ok = false;}
                if (longint < 0 || longint > 9) { //Vérification si la valeur de longint est correct
                    ok = false;
                }
            }
            else if (lcd == 1) {
                try {
                    longint = Character.toUpperCase(longstr.charAt(0)) - 65;
                }catch(StringIndexOutOfBoundsException e){ok = false;}
                if (longint < 0 || longint > 9) { //Vérification si la valeur de longint est correct
                    ok = false;
                }
            }
            else if (lcd == 2){
                try {
                    longint = Integer.parseInt(longstr);
                }catch(NumberFormatException e){ok = false;}
                if (longint <= 0 || longint > 2) { //Vérification si la valeur de longint est correct
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
     * @param grille Sur quelle grille vérifier
     * @param type numéro du bateau compris entre 1 et 5
     */
    public static boolean couler(int [][]grille, int type) {
        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                if (grille[ligne][colonne] == type) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Affiche soit « Touché », soit « Coulé » (en indiquant de quel bateau il s’agit), soit « À l’eau ». Met aussi la grille à jour
     *
     * @param grille Sur quelle grille vérifier
     * @param ligne la ligne
     * @param colonne la colonne
     */
    public static void mouvement(int [][]grille, int ligne, int colonne){
        int numBateau;

        if(grille[ligne][colonne] != 0 && grille[ligne][colonne] != 6){
            numBateau = grille[ligne][colonne];
            grille[ligne][colonne] = 6;

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
     * Renvoie un tableau avec 2 entiers tiré au hasard entre 0 et 9 pour indiquer où l'ordinateur va tirer
     *
     * @return un tableau avec 2 entiers tiré au hasard entre 0 et 9
     */
    public static int[] tirOrdinateur(){
        int[] intTab = new int[2];

        intTab[0] = randRange(0,10);
        intTab[1] = randRange(0,10);

        return intTab;
    }

    /**
     * Retourne vrai s'il n'y a plus de bateau sur la grille envoyé
     *
     * @param grille sur quelle grille vérifier
     * @return vrai s'il n'y a plus de bateau sinon faux
     */
    public static boolean vainqueur(int [][]grille){
        for (int ligne = 0; ligne < 10; ligne++) {
            for (int colonne = 0; colonne < 10; colonne++) {
                if (grille[ligne][colonne] != 0 && grille[ligne][colonne] != 6) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * <pre>
     * Fonction qui demande au joueur où il veut tirer et renvoie les coordonnées
     *
     * Pour intTab[] la valeur 0 correspond à la ligne et la valeur 1 à la colonne
     * </pre>
     * @return tableau de 2 entiers pour indiquer où le joueur va tirer
     */
    public static int[] tirJoueur(){
        int[] intTab = new int[2];
        boolean ok = false;
        Scanner entreeUtilisateur = new Scanner(System.in);
        String texteEntree;

        while(!ok) {
            ok = true;

            System.out.println("Rentrer la ligne et la colonne où attaquer (ex: B3) : ");
            try {
                texteEntree = entreeUtilisateur.nextLine();

                intTab[0] = Integer.parseInt(String.valueOf(texteEntree.charAt(1)));
                intTab[1] = Character.toUpperCase(texteEntree.charAt(0)) - 65;
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                ok = false;
            }

            if (intTab[0] < 0 || intTab[0] > 9 || intTab[1] < 0 || intTab[1] > 9) { //Vérification si la valeur de longint est correct
                ok = false;
            }

            if (!ok) {
                System.out.println("Il y a une erreur dans votre entrée, veuillez réessayer");
            }
        }

        return intTab;
    }

    /**
     * Fonction qui remplie les deux grilles et fait jouer à tour de rôle l'ordinateur et le joueur. Elle vérifie aussi s'il y a un vainqueur
     */
    public static void engagement(){
        boolean fin = false;
        int[] ordiTab;
        int[] joueurTab;

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
                AfficherGrilleInterrogation(grilleOrdi);

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

    /**
     * Fonction principale
     */
    public static void main(String[] args) {
        engagement();
    }
}