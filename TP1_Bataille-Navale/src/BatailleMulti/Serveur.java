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
     * Initialisation du Serveur et des grilles
     */
    public static void init() throws IOException, ClassNotFoundException {
        System.out.println("Vous êtes le Serveur");

        int port = 9876;
        server = new ServerSocket(port);

        System.out.println("Waiting for the client request");

        //Création du socket et attente de la connexion du client
        socket = server.accept();

        //Création des flux d'entrée et de sortie
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());


        //Création de la grille serveur
        bataille.initGrille(bataille.grilleServeur);

        //Envoie au client de 0 indiquer qu'on a fini de remplir la grille
        oos.writeObject(0);

        //Attente que le client remplisse la grille et l'assigner à la grille locale
        System.out.println("Attente de remplissage du Client");
        bataille.grilleClient = (int[][]) ois.readObject();
    }

    /**
     * Arrêt du serveur
     */
    public static void arret() throws IOException{
        ois.close();
        oos.close();
        socket.close();
    }

    public static void engagement() throws IOException, ClassNotFoundException{
        boolean fin = false;
        int[] tabTir;
        int[] message;

        while(!fin){
            //System.out.println("L'autre joueur attaque! : ");
            //Envoie au client qu'on est prêt à recevoir
            oos.writeObject(0);

            System.out.println("Grille serveur: ");
            int[][] tmpGrille = bataille.grilleServeur;
            bataille.AfficherGrille(tmpGrille);

            //System.out.println("Grille serveur : ");
            //bataille.AfficherGrille(bataille.grilleServeur);
            //Envoie de la grille Client
            oos.writeObject(tmpGrille);

            //Envoie de la grille Serveur
            //oos.writeObject(bataille.grilleServeur);

            //Attente de l'entrée utilisateur
            message = (int[]) ois.readObject();

            //Renvoie si le tir a touché ou non
            oos.writeObject(bataille.mouvement(bataille.grilleServeur,message[0],message[1]));
        }
    }
}
