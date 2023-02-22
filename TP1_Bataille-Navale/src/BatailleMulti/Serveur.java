package BatailleMulti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static ServerSocket server;
    public static int port = 9876;
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
        socket = server.accept();
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
     * @param message Entier à envoyer
     */
    public static void envoie(int message) throws IOException{
        //Création du canal d'envoie
        socket = server.accept();
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
     * @param message Chaine de caractère à envoyer
     */
    public static void envoie(String message) throws IOException{
        //Création du canal d'envoie
        socket = server.accept();
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
        socket = server.accept();
        ois = new ObjectInputStream(socket.getInputStream());

        //Reception du message
        Object message = ois.readObject();

        //Fermeture du canal
        ois.close();
        socket.close();

        return message;
    }

    /**
     * Initialisation du Serveur et des grilles
     */
    public static void init() throws IOException, ClassNotFoundException {
        System.out.println("Vous êtes le Serveur");

        //Ouverture du port
        int port = 9876;
        server = new ServerSocket(port);

        //Attente de la connexion du client
        socket = server.accept();
        socket.close();

        //Création de la grille serveur
        bataille.initGrille(bataille.grilleServeur);

        //Envoie au client de 0 indiquer qu'on a fini de remplir la grille
        envoie(0);

        //Attente que le client remplisse la grille et l'assigner à la grille locale
        System.out.println("Attente de remplissage du Client");
        bataille.grilleClient = (int [][]) reception();

        /* Affichage grilles
        System.out.println("Grille Client : ");
        bataille.AfficherGrille(bataille.grilleClient);
        System.out.println("Grille Serveur : ");
        bataille.AfficherGrille(bataille.grilleServeur);
        */
    }

    public static void engagement() throws IOException, ClassNotFoundException{
        boolean fin = false;
        int[] tabTir;
        int[] message;

        while(!fin){
            //Envoie au client qu'on est prêt à recevoir
            envoie(0);

            System.out.println("Grille serveur: ");
            bataille.AfficherGrille(bataille.grilleServeur);

            //System.out.println("Grille serveur : ");
            //bataille.AfficherGrille(bataille.grilleServeur);
            //Envoie de la grille Client
            envoie(bataille.grilleServeur);

            //Envoie de la grille Serveur
            //oos.writeObject(bataille.grilleServeur);

            //Attente de l'entrée utilisateur
            message = (int[]) reception();

            //Renvoie si le tir a touché ou non
            envoie(bataille.mouvement(bataille.grilleServeur,message[0],message[1]));
        }
    }
}