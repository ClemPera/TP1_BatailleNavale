package BatailleMulti;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Bataille navale Multijoueur local
 *
 * @author Clément Pera
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
    public static int[][] grilleClient = new int [10][10];
    public static int[][] grilleServeur = new int [10][10];

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
     * Procédure pour ajouter un bateau à la grille ordi
     *
     * @param l numéro de ligne
     * @param c numéro de colonne
     * @param d numéro de direction
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
        for(char s = 'A'; s <= 'J'; s++){ //Affiche une ligne avec les lettres de A à J
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

    /**
     * Affiche la grille avec des point d'interrogation partout sauf où un bateau a été touché
     *
     * @param grille Grille à utilisé
     */
    public static void AfficherGrilleInterrogation(int [][]grille){
        System.out.print("    ");
        for(char s = 'A'; s <= 'J'; s++){ //Affiche une ligne avec les lettres de A à J
            System.out.print(s);
            System.out.print(' ');
        }

        System.out.println();
        System.out.println("   --------------------");

        for(int n = 0; n < 10; n++){ //Affiche la grille avec les nombres de 0 à 9
            System.out.print(n);
            System.out.print(" | ");

            for(int i = 0; i < 10; i++){
                if(grille[n][i] == 6){
                    System.out.print(grille[n][i]);
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
     *
     * @param grille
     */
    public static void initGrille(int[][]grille){
        //Demander pour chaque bateau puis les placer avec un while dans la ligne idéal et les vérif avec posOk
        int ligne = 0;
        int colonne = 0;
        int direction = 0;
        boolean ok = false;

        for(int i = 1; i <=5; i++) {
            while (!ok) {
                AfficherGrille(grille);
                System.out.println();
                ligne = questionUtilisateur(0, i); //Quelle ligne pour un porte-avion
                colonne = questionUtilisateur(1, i); //Quelle ligne pour un porte-avion
                direction = questionUtilisateur(2, i); //Quelle ligne pour un porte-avion

                ok = posOk(grille, ligne, colonne, direction, i);
                if (!ok) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("L'entrée n'est pas correct, veuillez ressayer");
                }
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ajoutBateau(grille, ligne, colonne, direction, i);
            ok = false;
        }
    }

    /**
     * Pose une question à l'utilisateur par rapport à la lettre/colonne ou direction
     *
     * @param lct 0 = ligne, 1 = colonne, 2 = direction
     * @param type 0 = porte avion, 1 = croiseur, 2 = contre-torpilleur, 3 = sous-marin, 4 = torpilleur
     * @return retourne un entier indiquant la ligne, colonne ou direction que l'utilisateur a rentré
     */
    public static int questionUtilisateur(int lct, int type){
        String str = "";
        Scanner entreeUtilisateur = new Scanner(System.in);
        String longstr;
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
     * @param grille Sur quelle grille vérifier
     * @param l ligne
     * @param c colonne
     */
    public static String mouvement(int [][]grille, int l, int c){
        int numBateau;

        if(grille[l][c] != 0 && grille[l][c] != 6){
            numBateau = grille[l][c];
            grille[l][c] = 6;

            if(couler(grille, numBateau)){
                return "Le bateau " + numBateau + " a été coulé!";
            } else{
                return "Touché";
            }
        }
        else{
            return "A l'eau";
        }
    }

    /**
     * Retourne vrai s'il n'y a plus de bateau sur la grille envoyé
     *
     * @param grille sur quelle grille vérifier
     * @return vrai s'il n'y a plus de bateau sinon faux
     */
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
     * Fonction qui replie les deux grilles et fait jouer à tour de rôle l'ordinateur et le joueur
     *
     */
    public static void engagement(){
        boolean fin = false;
        //int[] ordiTab;
        int[] joueurTab;

        initGrille(grilleClient);
        initGrille(grilleServeur);

        System.out.print("\033[H\033[2J");
        System.out.flush();

        while(!fin) {
            //Tir de l'ordinateur
            System.out.println("L'ordinateur attaque! : ");
            //ordiTab = tirOrdinateur();
            //mouvement(grilleServeur,ordiTab[0],ordiTab[1]);

            System.out.println();
            System.out.println();
            //Vérification s'il y a un vainqueur
            if (vainqueur(grilleServeur)) {
                System.out.println("L'ordinateur a gagné!");
                fin = true;
            }else{
                System.out.println("Grille ordi");
                AfficherGrilleInterrogation(grilleClient);

                System.out.println();
                System.out.println("Grille joueur");
                AfficherGrille(grilleServeur);
                System.out.println();
                //Tir du joueur
                joueurTab = tirJoueur();
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("Vous avez attaqué! : ");
                mouvement(grilleClient, joueurTab[0], joueurTab[1]);
                System.out.println();

                //Vérification s'il y a un vainqueur
                if (vainqueur(grilleClient)) {
                    System.out.println("Vous avez gagné!");
                    fin = true;
                }
            }
        }
    }

    /**
     * Fonction principale
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        //engagement();
        int srvCli = 0;
        Scanner entreeUtilisateur = new Scanner(System.in);

        System.out.println("Rentrer 1 pour Serveur et 2 pour Client : ");
        srvCli = Integer.parseInt(entreeUtilisateur.nextLine());

        if (srvCli == 1){
            Serveur.init();
            Serveur.engagement();
        }
        else if (srvCli == 2){
            Client.init();
            Client.engagement();
        }
        else{
            System.out.println("Erreur!");
        }
    }
}