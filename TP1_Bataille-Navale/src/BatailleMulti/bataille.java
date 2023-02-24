package BatailleMulti;

import java.io.IOException;
import java.util.Scanner;

/**
 * Nom du programme : Bataille Navale version multijoueur
 * @author Clément Pera
 * Date : 06 Mars 2023
 * Résumé : Programme qui permet de jouer à la bataille navale contre un ordinateur
 * L'ordinateur et le joueur disposent d’une grille de 10
 * cases sur 10 cases, les colonnes de cette grille sont indiquées par une lettre de A à J et les
 * lignes sont numérotées de 1 à 10. Sur cette grille sont placés 5 bateaux en horizontal ou en
 * vertical. Le but de chaque joueur est de couler tous les bateaux de l’autre joueur
 * Cette version est faite en multijoueur.
 */
public class bataille {
    public static int[][] grilleClient = new int [10][10];
    public static int[][] grilleServeur = new int [10][10];

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
     * @param grille Grille a utilisé
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
     * Initialise la grille de jeu
     *
     * @param grille Quelle grille initialiser
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
     * @param ligne la ligne
     * @param colonne la colonne
     */
    public static String mouvement(int [][]grille, int ligne, int colonne){
        int numBateau;

        if(grille[ligne][colonne] != 0 && grille[ligne][colonne] != 6){
            numBateau = grille[ligne][colonne];
            grille[ligne][colonne] = 6;

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
     * Fonction principale
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        //engagement();
        int srvCli = 0;
        boolean ok = false;
        Scanner entreeUtilisateur = new Scanner(System.in);

        while(!ok){
            ok = true;
            System.out.println("Rentrer 1 pour Serveur et 2 pour Client : ");
            try {
                srvCli = Integer.parseInt(entreeUtilisateur.nextLine());
            }catch (NumberFormatException | StringIndexOutOfBoundsException e){
                ok = false;
            }

            if(srvCli < 1 || srvCli > 2)
            {
                ok = false;
            }

            if(!ok){
                System.out.println("Il y a une erreur dans votre entrée, veuillez réessayer");
            }
        }
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