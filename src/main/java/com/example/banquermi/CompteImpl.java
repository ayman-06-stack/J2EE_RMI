package com.example.banquermi;


import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.io.Serializable;

public class CompteImpl extends UnicastRemoteObject implements CompteInterface, Serializable {
    private final int id;
    private final String proprietaire;
    private double solde;
    private double decouvert; // autorisé (peut être 0 ou négatif)

    protected CompteImpl(int id, String proprietaire) throws RemoteException {
        super(); // export object
        this.id = id;
        this.proprietaire = proprietaire;
        this.solde = 0.0;
        this.decouvert = 0.0;
    }

    @Override
    public synchronized int getId() throws RemoteException {
        return id;
    }

    @Override
    public synchronized String getProprietaire() throws RemoteException {
        return proprietaire;
    }

    @Override
    public synchronized double getSolde() throws RemoteException {
        return solde;
    }

    @Override
    public synchronized void verser(double montant) throws RemoteException, IllegalArgumentException {
        if (montant <= 0) throw new IllegalArgumentException("Montant doit être > 0");
        solde += montant;
        System.out.println("Compte " + id + " versement: " + montant + " -> solde: " + solde);
    }

    @Override
    public synchronized void retirer(double montant) throws RemoteException, IllegalArgumentException {
        if (montant <= 0) throw new IllegalArgumentException("Montant doit être > 0");
        if (solde - montant < -decouvert) {
            throw new IllegalArgumentException("Retrait impossible : dépassement du découvert autorisé");
        }
        solde -= montant;
        System.out.println("Compte " + id + " retrait: " + montant + " -> solde: " + solde);
    }

    @Override
    public synchronized double getDecouvert() throws RemoteException {
        return decouvert;
    }

    @Override
    public synchronized void setDecouvert(double decouvert) throws RemoteException {
        this.decouvert = decouvert;
    }
}