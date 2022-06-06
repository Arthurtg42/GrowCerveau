package mi1.todolist.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Calcul implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "operation")
    private String operation;

    @ColumnInfo(name = "operand1")
    private Integer operand1;

    @ColumnInfo(name = "operand2")
    private Integer operand2;

    @ColumnInfo(name = "resultat")
    private Integer resultat;

    // construit un calcul
    public Calcul(String operation) {
        // operand1 aléatoire entre 1 et 99
        operand1 = (int) (Math.random() * (99 - 1)) + 1;
        if (operation.compareTo("+") == 0) {
            // addtion
            this.operation = operation;
            // operand2 aléatoire entre 1 et 99
            operand2 = (int) (Math.random() * (99 - 1)) + 1;
            resultat = operand1 + operand2;
        } else if (operation.compareTo("-") == 0) {
            // soustraction
            this.operation = operation;
            // operand2 aléatoire entre 1 et operand1, car les nombres négatifs ne sont pas vu en primaire
            operand2 = (int) (Math.random() * (operand1 - 1)) + 1;
            resultat = operand1 - operand2;
        } else if (operation.compareTo("*") == 0) {
            // multiplication
            this.operation = operation;
            // operand2 aléatoire entre 1 et 10, pour ne pas générer de calcul trop complexe
            operand2 = (int) (Math.random() * (10 - 1)) + 1;
            resultat = operand1 * operand2;
        } else if (operation.compareTo("/") == 0) {
            // division
            this.operation = operation;
            // resultat aléatoire entre 1 et 10, pour ne pas générer de calcul trop complexe
            resultat = (int) (Math.random() * (10 - 1)) + 1;
            // on redéfinit operand2 et operand2 pour avoir une division entière
            operand2 = operand1;
            operand1 = operand2 * resultat;
        } else {
            // opération non conforme
            this.operation = null;
        }
    }

    /*
     * Getters and Setters
     * */

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Integer getResultat() {
        return resultat;
    }

    public String getEnonce(){
        return operation!=null ? operand1+" "+operation+" "+operand2+" = " : null;
    }

    // renvoi le Cacul sous forme de Qas
    public Qas toQas(){
        Qas qas = new Qas();
        qas.setEnonce(getEnonce());
        qas.setReponse(getResultat().toString());
        return qas;
    }

}