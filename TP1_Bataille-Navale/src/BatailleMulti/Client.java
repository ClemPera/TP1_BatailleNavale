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
     * Initialisation du Client et de la grille
     */
    public static void init() throws IOException, ClassNotFoundException{
        System.out.println("Vous êtes le Client");
        host = InetAddress.getByName("192.168.0.200");

        //Connexion au serveur
        socket = new Socket(host.getHostName(), 9876);

        //Création des flux d'entrée et de sortie
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        //Attente d'un message du serveur pour indiquer qu'il a fini de remplir la grille
        System.out.println("Attente de remplissage du Serveur");
        int message = (int) ois.readObject();

        //Remplissage et envoie de la grille
        bataille.initGrille(bataille.grilleClient);
        oos.writeObject(bataille.grilleClient);

    }

    /**
     * Arrêt du client
     */
    public static void arret() throws IOException{
        ois.close();
        oos.close();
        socket.close();
    }

    public static void engagement() throws IOException, ClassNotFoundException{
        boolean fin = false;
        int message;

        while(!fin){
            //Attente que le serveur soit prêt à recevoir
            message = (int) ois.readObject();

            //Attendre l'envoie de la grille du client depuis le serveur
            System.out.println("grille serveur : ");
            int[][] tmpGrille2 =(int[][]) ois.readObject();
            bataille.AfficherGrille(tmpGrille2);

            //Attendre l'envoie de la grille du client depuis le serveur
            //System.out.println("La grille de l'autre joueur : ");
            //bataille.AfficherGrille((int[][]) ois.readObject());

            //Envoie de l'entrée utilisateur
            oos.writeObject(bataille.tirJoueur());

            //Affichage de l'état du tir
            System.out.println((String) ois.readObject());
        }
    }
}
