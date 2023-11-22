import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Simplex {
    private boolean ehMaximizacao;
    private int numeroVariaveis;
    private int numeroRestricoes;
    private float[][] matriz;
    private int[] base;

    public Simplex(String nomeArquivo) {
        lerArquivoEntrada(nomeArquivo);
    }

    public void lerArquivoEntrada(String nomeArquivo) {
        Scanner reader;
        try {
            reader = new Scanner(new File(nomeArquivo));
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
            e.printStackTrace();
            return;
        }

        this.ehMaximizacao = Integer.parseInt(reader.nextLine()) > 0;
        this.numeroVariaveis = Integer.parseInt(reader.nextLine());
        this.numeroRestricoes = Integer.parseInt(reader.nextLine());
        this.base = new int[this.numeroVariaveis + this.numeroRestricoes];
        for (int i = 0; i < this.base.length; i++)
            this.base[i] = (i < this.numeroVariaveis) ? 0 : 1;

        this.matriz = new float[this.numeroRestricoes + 1][this.numeroVariaveis + this.numeroRestricoes + 1];

        for (int i = 0; i < this.numeroRestricoes + 1; i++) {
            String data = reader.nextLine();
            String[] numeros = data.split(" ");

            for (int j = 0; j < this.matriz[i].length; j++) {
                if (j < this.numeroVariaveis) {
                    this.matriz[i][j] = Float.parseFloat(numeros[j]);
                    if (i == 0)
                        this.matriz[i][j] = this.matriz[i][j] * -1;
                } else {
                    if (i != 0)
                        this.matriz[i][this.numeroVariaveis + i - 1] = 1;
                    this.matriz[i][j] = 0;
                }
                if (i != 0)
                    this.matriz[i][this.matriz[i].length - 1] = Float.parseFloat(numeros[numeros.length - 1]);
            }
        }
        reader.close();
    }

    public int pegarColunaPivo() {
        int column = -1;
        float valor = 0;
        for (int i = 0; i < matriz[0].length; i++) {
            if (ehMaximizacao) {
                if (matriz[0][i] < 0 && i == 0) {
                    column = i;
                    valor = matriz[0][i];
                }

                if (column != -1 && matriz[0][i] < valor) {
                    column = i;
                    valor = matriz[0][i];
                }
            } else {
                if (matriz[0][i] < 0 && i == 0) {
                    column = i;
                    valor = matriz[0][i];
                }

                if (column != -1 && matriz[0][i] > valor && matriz[0][i] < 0) {
                    column = i;
                    valor = matriz[0][i];
                }
            }
        }
        return column;
    }

    @Override
    public String toString() {
        String print = "";
        for (int i = 0; i < this.matriz.length; i++) {
            print += Arrays.toString(this.matriz[i]) + "\n";
        }
        return print;
    }

    public static void main(String[] args) {
        Simplex simplex = new Simplex(args[0]);
        System.out.print(simplex);
    }
}
