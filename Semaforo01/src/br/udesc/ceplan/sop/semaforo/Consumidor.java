/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceplan.sop.semaforo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Alberto Cipriano Korovsky <carlos.korovsky at gmail.com>
 */
public class Consumidor implements Runnable {

    private final int id;
    private final int runMax;
    private final Random runTimeGenerator;

    public Consumidor(int id, int runMax) {
        this.id = id;
        this.runMax = runMax;
        this.runTimeGenerator = new Random();
    }
    
    @Override
    public void run() {
        System.out.println("HELLO I'm: " + this.toString() + " and I'll run " + this.runMax + " times!");
        int runCount = 0;
        Connection connection = null;
        while (runCount < runMax) {
            runCount++;
            try {
                long runTime = this.runTimeGenerator.nextInt(500) + 500; //cria um tempo randômico de processamento
                connection = ConnectionManager.getInstance().popConnection(); // pega uma conexão
                System.out.println(this.toString() + " using " + connection + " for " + runTime + "ms on run " + runCount + "/" + this.runMax + " connections avaliable " + ConnectionManager.getInstance().getAvaliableConnections());
                Thread.sleep(runTime); //simula um processamento longo
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ConnectionManager.getInstance().pushConnection(connection); //devolve a conexão
                System.out.println(this.toString() + " released " + connection + " on run " + runCount + "/" + this.runMax + " connections avaliable " + ConnectionManager.getInstance().getAvaliableConnections());
                connection = null;
            }
        }
        
    }

    @Override
    public String toString() {
        return "Consumidor {" + "id=" + id + '}';
    }
    
}
