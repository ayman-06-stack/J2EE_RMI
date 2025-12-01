package com.example.banquermi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CompteInterface extends Remote {
    int getId() throws RemoteException;
    String getProprietaire() throws RemoteException;
    double getSolde() throws RemoteException;
    void verser(double montant) throws RemoteException, IllegalArgumentException;
    void retirer(double montant) throws RemoteException, IllegalArgumentException;
    double getDecouvert() throws RemoteException;
    void setDecouvert(double decouvert) throws RemoteException;
}