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
     * Fonction d'envoie de message
     *
     * @param message Tableau d'entier à double dimension à envoyer
     */
    public static void envoie(int[][] message) throws IOException{
        //Création du canal d'envoie
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        //Envoie du message
        oos.writeObject(message);

        //Fermeture du canal d'envoie
        oos.close();
        socket.close();
    }

    /**
     * Fonction d'envoie de message
     *
     * @param message Tableau d'entier à envoyer
     */
    public static void envoie(int[] message) throws IOException{
        //Création du canal d'envoie
        socket = new Socket(host.getHostName(), 9876);
        oos = new ObjectOutputStream(socket.getOutputStream());

        //Envoie du message
        oos.writeObject(message);

        //Fermeture du canal d'envoie
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
     * Initialisation du Client et de la grille
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
        int message = (int) reception();

        //Remplissage et envoie de la grille
        bataille.initGrille(bataille.grilleClient);
        envoie(bataille.grilleClient);
    }

    public static void engagement() throws IOException, ClassNotFoundException{
        boolean fin = false;
        int message;

        while(!fin){
            System.out.println("Votre grille : ");
            bataille.AfficherGrille((int[][]) reception());

            System.out.println();
            System.out.println();

            System.out.println("Grille de l'autre joueur : ");
            bataille.AfficherGrilleInterrogation((int[][]) reception());

            //Envoie de l'entrée utilisateur
            envoie(bataille.tirJoueur());

            //Affichage de l'état du tir
            System.out.println("Vous avez attaqué : " + reception());
            if((int) reception() == 1){
                System.out.println("Vous avez gagné!");
                fin = true;
            }
            else {
                System.out.println("L'autre joueur a attaqué : " + reception());
                if((int) reception() == 2){
                    System.out.println("L'autre joueur a gagné!");
                    fin = true;
                }
            }
        }
    }
}