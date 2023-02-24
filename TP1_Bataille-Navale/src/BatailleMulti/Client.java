package BatailleMulti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static InetAddress host;
    public static Socket socket;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    /**
     * Fonction d'envoi de message
     *
     * @param message Tableau d'entier à double dimension à envoyer
     */
    public static void envoie(int[][] message) throws IOException{
        //Création du canal d'envoi
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        //Envoie du message
        oos.writeObject(message);

        //Fermeture du canal d'envoi
        oos.close();
        socket.close();
    }

    /**
     * Fonction d'envoi de message
     *
     * @param message Tableau d'entier à envoyer
     */
    public static void envoie(int[] message) throws IOException{
        //Création du canal d'envoi
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        //Envoie du message
        oos.writeObject(message);

        //Fermeture du canal d'envoi
        oos.close();
        socket.close();
    }

    /**
     * Fonction de réception d'un message
     *
     * @return un objet contenant le message
     */
    public static Object reception() throws IOException, ClassNotFoundException{
        //Création du canal de reception
        socket = new Socket(host.getHostName(), 9876);
        ois = new ObjectInputStream(socket.getInputStream());

        //Reception du message
        Object message = ois.readObject();

        //Fermeture du canal
        ois.close();
        socket.close();

        return message;
    }

    /**
     * <pre>
     * Initialisation du Client et de la grille
     *
     * Utilisation de https://www.digitalocean.com/community/tutorials/java-socket-programming-server-client pour les sockets
     *</pre>
     */
    public static void init() throws IOException, ClassNotFoundException{
        boolean ok = false;
        boolean okIp = false;
        Scanner entreeUtilisateur = new Scanner(System.in);
        String addrIp;

        while(!okIp) {
            while (!ok) {
                ok = true;
                okIp = true;
                System.out.println("Rentrer l'adresse ip du serveur (appuyer sur entrée pour : 127.0.0.1) : ");
                try {
                    addrIp = entreeUtilisateur.nextLine();
                    if (addrIp.isBlank()) {
                        addrIp = "127.0.0.1";
                    }
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    ok = false;
                    System.out.println("Il y a une erreur dans votre entrée, veuillez réessayer");
                }
            }

            System.out.println("Vous êtes le Client");
            host = InetAddress.getByName("127.0.0.1");

            //Attente d'un message du serveur pour indiquer qu'il a fini de remplir la grille
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Attente de remplissage du Serveur...");
            try {
                socket = new Socket(host.getHostName(), 9876);
            } catch (ConnectException e) {
                okIp = false;
                ok = false;
                System.out.println("Le serveur n'est pas crée ou il y a un problème dans l'adresse ip, veuillez réessayer");
            }
        }
        socket.close();
        int messageTempo = (int) reception();

        //Remplissage et envoie de la grille
        bataille.initGrille(bataille.grilleClient);
        envoie(bataille.grilleClient);
    }

    /**
     * Fonction de jeu qui permet de faire jouer les deux joueurs tour à tour et de vérifier s'il y a un vainqueur
     */
    public static void engagement() throws IOException, ClassNotFoundException{
        boolean fin = false;

        while(!fin){
            System.out.println("Votre grille : ");
            bataille.AfficherGrille((int[][]) reception());

            System.out.println();
            System.out.println();

            System.out.println("Grille de l'autre joueur : ");
            bataille.AfficherGrilleInterrogation((int[][]) reception());
            System.out.println();

            //Envoi de l'entrée utilisateur
            envoie(bataille.tirJoueur());

            System.out.print("\033[H\033[2J");
            System.out.flush();

            //Affichage de l'état du tir
            System.out.println("Vous avez attaqué : " + reception());
            if((int) reception() == 1){
                System.out.println("Vous avez gagné!");
                fin = true;
            }
            else {
                System.out.println("Attente de l'attaque de l'autre joueur...");
                System.out.println("L'autre joueur a attaqué : " + reception());
                System.out.println();
                if((int) reception() == 1){
                    System.out.println("L'autre joueur a gagné!");
                    fin = true;
                }
            }
        }
    }
}