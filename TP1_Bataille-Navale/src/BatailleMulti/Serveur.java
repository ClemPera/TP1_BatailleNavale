package BatailleMulti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static ServerSocket server;
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
        socket = server.accept();
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
     * @param message Entier à envoyer
     */
    public static void envoie(int message) throws IOException{
        //Création du canal d'envoi
        socket = server.accept();
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
     * @param message Chaine de caractère à envoyer
     */
    public static void envoie(String message) throws IOException{
        //Création du canal d'envoi
        socket = server.accept();
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
     *
     * Utilisation de https://www.digitalocean.com/community/tutorials/java-socket-programming-server-client pour les sockets
     */
    public static void init() throws IOException, ClassNotFoundException {
        System.out.print("\033[H\033[2J");
        System.out.flush();

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
    }

    /**
     * Fonction de jeu qui permet de faire jouer les deux joueurs tour à tour et de vérifier s'il y a un vainqueur
     */
    public static void engagement() throws IOException, ClassNotFoundException{
        boolean fin = false;
        int[] tabTir;
        int[] message;
        String attaqueCli;
        String attaqueSrv;

        while(!fin){
            envoie(bataille.grilleClient);

            System.out.println("Grille serveur : ");
            bataille.AfficherGrille(bataille.grilleServeur);

            System.out.println();

            System.out.println("Grille client : ");
            bataille.AfficherGrilleInterrogation(bataille.grilleClient);
            System.out.println();

            envoie(bataille.grilleServeur);

            System.out.println("Attente de l'attaque de l'autre joueur...");
            message = (int[]) reception();

            //Renvoie et affiche si le tir a touché ou non
            attaqueCli = bataille.mouvement(bataille.grilleServeur,message[0],message[1]);
            envoie(attaqueCli);
            System.out.println("L'autre joueur a attaqué : " + attaqueCli);
            System.out.println();

            //Client a gagné, envoie de 1 sinon 0
            if (bataille.vainqueur(bataille.grilleServeur)) {
                System.out.println("L'autre joueur a gagné!");

                envoie(1);

                fin = true;
            }
            else {
                envoie(0);

                //Attaque
                tabTir = bataille.tirJoueur();
                attaqueSrv = bataille.mouvement(bataille.grilleClient, tabTir[0], tabTir[1]);

                envoie(attaqueSrv);

                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("Vous avez attaqué : " + attaqueSrv);
                System.out.println();

                //Client a gagné, envoie de 1 sinon 0
                if(bataille.vainqueur(bataille.grilleClient)){
                    System.out.println("Vous avez gagné!");

                    envoie(1);

                    fin = true;
                }
                else{
                    envoie(0);
                }
            }
        }
    }
}