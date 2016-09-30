/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceplan.sop.semaforo;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Carlos Alberto Cipriano Korovsky <carlos.korovsky at gmail.com>
 */
public class ConnectionManager {
    
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private static final int AVALIABLE_CONNECTIONS = 10;
    private static final int TOTAL_CONNECTIONS = AVALIABLE_CONNECTIONS; //Número total de conexões disponíveis

    private final Queue<Connection> connections;
    private final Semaphore semaforo;
    
    private ConnectionManager() {
        this.connections = new ArrayDeque<>();
        for(int id = 0; id < TOTAL_CONNECTIONS; id++) {
            this.connections.add(new Connection(id)); // Cria as conexões
        }
        semaforo = new Semaphore(AVALIABLE_CONNECTIONS);
        System.out.println("TOTAL CONNECTIONS/AVALIABLE CONNECTIONS: " + TOTAL_CONNECTIONS + "/" + semaforo.availablePermits());
        System.out.println("Connection Manager initiated");
    }
    
    public static ConnectionManager getInstance() {
        return ConnectionManager.INSTANCE;
    }
    
    //retorna o número de conexões disponíveis
    public synchronized int getAvaliableConnections() {
        return this.semaforo.availablePermits();
    }
    
    //Retorna uma conexão
    public Connection popConnection() throws InterruptedException {
        Connection cnx;
        semaforo.acquire();
        synchronized(this) {
            cnx = this.connections.poll();
        }
        return cnx;
    }
    
    //Devolve uma conexão
    public void pushConnection(Connection cnx) {
        synchronized(this) {
            if (cnx != null) {
                this.connections.add(cnx);
            }
        }
        semaforo.release();
    }
    
}
