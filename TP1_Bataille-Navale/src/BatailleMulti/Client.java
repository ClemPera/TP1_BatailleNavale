package BatailleMulti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
        System.out.println("Vous êtes le Client");
        host = InetAddress.getByName("127.0.0.1");

        //Attente d'un message du serveur pour indiquer qu'il a fini de remplir la grille
        System.out.println("Attente de remplissage du Serveur");
        socket = new Socket(host.getHostName(), 9876);
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
            //Attente que le serveur soit prêt à recevoir
            message = (int) reception();

            //Attendre l'envoie de la grille du client depuis le serveur
            System.out.println("Grille serveur : ");
            bataille.AfficherGrille((int[][]) reception());

            //Attendre l'envoie de la grille du client depuis le serveur
            //System.out.println("La grille de l'autre joueur : ");
            //bataille.AfficherGrille((int[][]) ois.readObject());

            //Envoie de l'entrée utilisateur
            envoie(bataille.tirJoueur());

            //Affichage de l'état du tir
            System.out.println((String) reception());
        }
    }
}