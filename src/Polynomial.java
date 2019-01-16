import sun.awt.X11.XColor;

import java.util.Arrays;

public class Polynomial {

    //Atributos
    private float[] coeficientes;







    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
        coeficientes = new float[1];
        coeficientes[0]=0;
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] coeficientes) {

        //Lo que hago aqui es crear un nuevo array para no referenciar al
        // que nos pasan ya que el usuario podria modificar ese array
        //entonces usaremos una copia


        int x = 0;
        for (int i = 0; i < coeficientes.length; i++) {
            if (coeficientes[i]==0){
                x++;
            }else {
                break;
            }
        }

        if (x==coeficientes.length){

            this.coeficientes=new float[1];
            this.coeficientes[0]=0;
        }else {
            this.coeficientes=new float[coeficientes.length-x];
            for (int i = x; i < coeficientes.length; i++) {
                this.coeficientes[i] = coeficientes[i];
            }
        }

    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        int diferencia=0;
        char negativo ='-';
        char positivo ='+';





        // Rutina que elimina espacios
        StringBuilder polinomio = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)!=' '){
                polinomio.append(s.charAt(i));
            }
        }


        // Cuenta numero de monomios en mi polinomio
        int contador=1;
        for (int i = 2; i < polinomio.length() ; i++) {
            if (polinomio.charAt(i)==positivo || polinomio.charAt(i)==negativo){
                contador++;
            }
        }




        double[] coeficientes   = new double[contador];
        int[] potencia          = new int[contador];
        boolean[] Xencontrada = new boolean[contador];

        //Todas Xencontrada a false
        for (int i = 0; i < Xencontrada.length; i++) {
            Xencontrada[i]=false;
        }


        contador=0;


        // Guardamos todas las potencias en nuestro array de potencias
        for (int i = 1; i < polinomio.length(); i++) {

            if (polinomio.charAt(i)=='x'|| polinomio.charAt(i)=='X'){
                Xencontrada[contador]=true;

                if (polinomio.charAt(i+1)=='^'){

                    // BUCLE PARA ENCONTRAR SIGUIENTE SIMBOLO

                    String StringApasar = "";
                    for (int j = i+2; j <polinomio.length() ; j++) {

                        if (polinomio.charAt(j)==positivo||polinomio.charAt(j)==negativo){
                            break;
                        }
                        StringApasar = StringApasar+polinomio.charAt(j);

                    }

                    potencia[contador]= Integer.parseInt(StringApasar);



                }else {
                    potencia[contador]=1;
                }



            }


            if ((polinomio.charAt(i)==negativo || polinomio.charAt(i)==positivo) && !Xencontrada[contador]){
                potencia[contador]=0;

                Xencontrada[contador]=true;
            }


            if (polinomio.charAt(i)==negativo || polinomio.charAt(i)==positivo){
                contador++;
            }


        }



        //Elimina todo lo que no son coeficientes
        StringBuilder polinomio1 = new StringBuilder();

        boolean comprovacionX = false;
        for (int i = 0; i < polinomio.length(); i++) {

            if (polinomio.charAt(i)!='x' && comprovacionX==false && polinomio.charAt(i)!=negativo && polinomio.charAt(i)!=positivo){
                polinomio1.append(polinomio.charAt(i));
            }else{
                comprovacionX=true;
            }


            if ((polinomio.charAt(i)==positivo||polinomio.charAt(i)==negativo)&& i>0){
                comprovacionX=false;
                polinomio1.append(polinomio.charAt(i));
            }else if (polinomio.charAt(i)==negativo && i==0){
                comprovacionX=false;
                polinomio1.append(polinomio.charAt(i));

            }
        }


        System.out.println(polinomio1 );


        // Esta bien
        String stringAdevolver="";
        contador = 0;
        int multiplicador=1;
        positivo='+';
        negativo='-';
        for (int i = 0; i < polinomio1.length(); i++) {
            if ((polinomio1.charAt(i)==positivo||polinomio1.charAt(i)==negativo)&& i>0){
                coeficientes[contador]= (Float.parseFloat(stringAdevolver)*multiplicador);
                stringAdevolver="";
                contador++;
            }
            if (polinomio1.charAt(i)==positivo){
                multiplicador=1;
            }else if (polinomio1.charAt(i)==negativo){
                multiplicador=-1;
            }
            if (polinomio1.charAt(i)!= positivo && polinomio1.charAt(i)!=negativo){
                stringAdevolver= stringAdevolver + polinomio1.charAt(i);
            }
        }
        coeficientes[contador]= (Float.parseFloat(stringAdevolver))*multiplicador;


        System.out.println("Desordenados\n" +
                Arrays.toString(coeficientes) + "\n" + Arrays.toString(potencia));

        //Ordenamos los arrays con orden de las potencias
        ordenarArrayDoble(potencia,coeficientes);

        System.out.println("Ordenados\n" +
                Arrays.toString(coeficientes) + "\n" + Arrays.toString(potencia));


        this.coeficientes = new float[potencia[potencia.length-1]+1];

        System.out.println(this.coeficientes.length);



    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {





        return null;
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {






        return null;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {






        return null;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {







        return null;
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polynomial p1 = (Polynomial) o;
        if (p1.coeficientes.length != this.coeficientes.length)return false;

        for (int i = 0; i < this.coeficientes.length; i++) {
            if (this.coeficientes[i]!=p1.coeficientes[i]){
                return false;
            }
        }
        return true;
    }




    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        String devolver = "";
        int elevacion = 0;
        String simbolo = "";
        String coef = "";
        String devolver1="";

        if (coeficientes.length==1){
            int cofi1=(int) coeficientes[0];
            double cofi2= coeficientes[0];

            if (cofi1==cofi2){
                 devolver1 = String.format("%.0f", coeficientes[0]);
            }else {
                 devolver1 = String.format("%.1f", coeficientes[0]);
            }
            return devolver1;
        }
        for (int i = this.coeficientes.length-1; i>=0 ; i--) {

            if (this.coeficientes[i]==0){
                simbolo="";
                elevacion++;
                continue;
            }


            double coefActual = coeficientes[i];
            int coefActual1 =(int) coefActual;

            if (coefActual>=0){
                simbolo="+";
            }else {
                simbolo="-";

                coefActual=coefActual*(-1);
                coefActual1=coefActual1*(-1);
            }
            if (coefActual== coefActual1){
                coef="";

                if (i==0){
                    if (coefActual1==1&&elevacion>0){
                        if (simbolo.equals("+")){

                        }else {
                            coef += simbolo;
                        }
                    }else {
                        coef += simbolo+""+coefActual1;
                    }
                }else {
                    if (coefActual1==1&&elevacion>0){
                        if (simbolo.equals("+")){

                        }else {
                            coef += simbolo;
                        }
                    }else {
                        coef += simbolo+" "+coefActual1;
                    }
                }

            }else{

                coef="";
                coef+= String.format(simbolo+" %.1f",coefActual);
            }

            System.out.println(coef);



            if (elevacion==0){
                devolver=  ""+coef;
            }else if(elevacion==1){
                devolver= coef+"x"+" "+ devolver;
            }else {
                devolver= coef+"x^"+elevacion+" " + devolver;
            }

            simbolo="";
            elevacion++;

        }
        return devolver;
    }





    public static void ordenarArrayDoble(int[] arrayPotencias, double[]arrayCoeficientes) {

        for (int i=arrayPotencias.length, x=0; i>x; i--, x++) {


            for (int j = 1; j < i; j++) {
                if (arrayPotencias[j - 1] > arrayPotencias[j]) {
                    int Swap1 = arrayPotencias[j];
                    double Swap2 = arrayCoeficientes[j];

                    arrayPotencias[j] = arrayPotencias[j - 1];
                    arrayCoeficientes[j]=arrayCoeficientes[j-1];

                    arrayPotencias[j - 1] = Swap1;
                    arrayCoeficientes[j-1]=Swap2;
                }
            }


            for (int k = (i-1) ; k > x ; k--) {
                if (arrayPotencias[k-1] > arrayPotencias[k]){
                    int Swap3 = arrayPotencias[k];
                    double Swap4 = arrayCoeficientes[k];

                    arrayPotencias[k] = arrayPotencias[k-1];
                    arrayCoeficientes[k]=arrayCoeficientes[k-1];

                    arrayPotencias[k-1] = Swap3;
                    arrayCoeficientes[k-1]=Swap4;
                }
            }

        }

    }



}


class main{
    public static void main(String[] args) {

        Polynomial p = new Polynomial("-4x^4+4-456x^90+5x-6x^3-3");



    }
}