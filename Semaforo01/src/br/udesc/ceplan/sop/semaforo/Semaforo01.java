/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceplan.sop.semaforo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Alberto Cipriano Korovsky <carlos.korovsky at gmail.com>
 */
public class Semaforo01 {
    private static final int MAX_NUM_THREADS = 20;
    private final List<Thread> consumidores;
    private final Random maxRunGenerator;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Semaforo01 app = new Semaforo01();
        app.runApplication();
    }
    
    public Semaforo01() {
        this.consumidores = new ArrayList<>();
        this.maxRunGenerator = new Random();
    }
    
    public void runApplication() {
        init();
        execute();
        finish();
    }
    
    //Inicia as threads
    private void init() {
        Consumidor consumidor;
        for (int id = 0; id < MAX_NUM_THREADS; id++) {
            int maxRun = maxRunGenerator.nextInt(10) + 1; //Gera o número maximo de execuções
            consumidor = new Consumidor(id, maxRun); //Cria um novo consumidor
            this.consumidores.add(new Thread(consumidor)); //Cria uma nova thread para o consumidor
        }
    }
    
    //Executa as threads
    private void execute() {
        for (Thread consumidor: this.consumidores) {
            consumidor.start(); //inicia a thread do consumidor
        }
    }
    
    //Aguarda a finalização de todas as threads
    private void finish() {
        for (Thread consumidor: this.consumidores) {
            try {
                consumidor.join(); //Aguarda a finalização do consumidor
            } catch (InterruptedException ex) {
                Logger.getLogger(Semaforo01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
