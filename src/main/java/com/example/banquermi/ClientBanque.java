package com.example.banquermi;

import java.rmi.Naming;
import java.util.Scanner;
import java.util.List;

public class ClientBanque {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            BanqueInterface banque = (BanqueInterface) Naming.lookup("rmi://localhost/" + ServeurBanque.NOM_SERVICE);
            System.out.println("Connecté au service banque.");

            boolean quit = false;
            while (!quit) {
                System.out.println("\n=== Menu ===");
                System.out.println("1) Créer compte");
                System.out.println("2) Lister comptes");
                System.out.println("3) Consulter compte");
                System.out.println("4) Verser");
                System.out.println("5) Retirer");
                System.out.println("6) Supprimer compte");
                System.out.println("0) Quitter");
                System.out.print("Choix: ");
                String line = sc.nextLine().trim();
                switch (line) {
                    case "1" -> {
                        System.out.print("Nom du propriétaire: ");
                        String prop = sc.nextLine();
                        CompteInterface c = banque.creerCompte(prop);
                        System.out.println("Compte créé id=" + c.getId());
                    }
                    case "2" -> {
                        List<Integer> ids = banque.listerComptes();
                        System.out.println("Comptes existants: " + ids);
                    }
                    case "3" -> {
                        System.out.print("Id compte: ");
                        int id = Integer.parseInt(sc.nextLine());
                        CompteInterface c = banque.getCompte(id);
                        if (c == null) System.out.println("Compte non trouvé");
                        else System.out.printf("Id=%d, Propriétaire=%s, Solde=%.2f, Découvert=%.2f%n",
                                c.getId(), c.getProprietaire(), c.getSolde(), c.getDecouvert());
                    }
                    case "4" -> {
                        System.out.print("Id compte: ");
                        int id = Integer.parseInt(sc.nextLine());
                        CompteInterface c = banque.getCompte(id);
                        if (c == null) { System.out.println("Compte non trouvé"); break; }
                        System.out.print("Montant à verser: ");
                        double m = Double.parseDouble(sc.nextLine());
                        c.verser(m);
                        System.out.printf("Nouveau solde: %.2f%n", c.getSolde());
                    }
                    case "5" -> {
                        System.out.print("Id compte: ");
                        int id = Integer.parseInt(sc.nextLine());
                        CompteInterface c = banque.getCompte(id);
                        if (c == null) { System.out.println("Compte non trouvé"); break; }
                        System.out.print("Montant à retirer: ");
                        double m = Double.parseDouble(sc.nextLine());
                        try {
                            c.retirer(m);
                            System.out.printf("Nouveau solde: %.2f%n", c.getSolde());
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Erreur: " + ex.getMessage());
                        }
                    }
                    case "6" -> {
                        System.out.print("Id compte: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean ok = banque.supprimerCompte(id);
                        System.out.println(ok ? "Compte supprimé" : "Compte introuvable");
                    }
                    case "0" -> quit = true;
                    default -> System.out.println("Choix invalide");
                }
            }
            System.out.println("Client arrêté.");
        } catch (Exception ex) {
            System.err.println("Erreur client: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}