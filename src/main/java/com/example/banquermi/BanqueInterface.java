package com.example.banquermi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BanqueInterface extends Remote {
    CompteInterface creerCompte(String proprietaire) throws RemoteException;
    CompteInterface getCompte(int id) throws RemoteException;
    List<Integer> listerComptes() throws RemoteException;
    boolean supprimerCompte(int id) throws RemoteException;
}