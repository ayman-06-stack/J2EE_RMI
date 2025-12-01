package com.example.banquermi;


import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;

public class BanqueImpl extends UnicastRemoteObject implements BanqueInterface {
    private final ConcurrentHashMap<Integer, CompteImpl> comptes = new ConcurrentHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(1);

    protected BanqueImpl() throws RemoteException {
        super();
    }

    @Override
    public CompteInterface creerCompte(String proprietaire) throws RemoteException {
        int id = sequence.getAndIncrement();
        CompteImpl c = new CompteImpl(id, proprietaire);
        comptes.put(id, c);
        System.out.println("Compte créé id=" + id + " propr=" + proprietaire);
        return c;
    }

    @Override
    public CompteInterface getCompte(int id) throws RemoteException {
        return comptes.get(id);
    }

    @Override
    public List<Integer> listerComptes() throws RemoteException {
        return new ArrayList<>(comptes.keySet());
    }

    @Override
    public boolean supprimerCompte(int id) throws RemoteException {
        return comptes.remove(id) != null;
    }
}