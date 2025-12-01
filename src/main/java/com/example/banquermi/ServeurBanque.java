package com.example.banquermi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

public class ServeurBanque {
    public static final String NOM_SERVICE = "BanqueService";

    public static void main(String[] args) {
        try {
            // Optionnel : créer un registry local sur le port 1099 si non lancé séparément
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("Registry RMI démarré sur le port 1099");
            } catch (RemoteException e) {
                System.out.println("Registry déjà présent : " + e.getMessage());
            }

            BanqueImpl banque = new BanqueImpl();
            // Enregistrement dans le registry
            Naming.rebind(NOM_SERVICE, banque);
            System.out.println("Serveur Banque prêt et lié comme '" + NOM_SERVICE + "'");
            System.out.println("Attente des clients...");
        } catch (RemoteException | MalformedURLException ex) {
            System.err.println("Erreur serveur RMI : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}