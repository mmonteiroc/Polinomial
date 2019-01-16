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

        if (s.length()==1||(s.length()==2 && (s.charAt(0)=='-'||s.charAt(0)=='+'))){
            this.coeficientes=new float[1];
            this.coeficientes[0]=Float.parseFloat(s);
        }


        // Rutina que elimina espacios
        StringBuilder polinomio = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)!=' '){
                polinomio.append(s.charAt(i));
            }
        }


        int mayorPotencia = 0;
        int potenciaActual=0;

        String aPasar="";

        // Encotrnamos la mayor potencia de todo el polinomo
        for (int i = 0; i < polinomio.length(); i++) {

            if (polinomio.charAt(i)=='x'){
                if (polinomio.charAt(i+1)=='^'){


                    //Este bucle lo que hace es que te convierte numeros de mas de dos digitos a integer
                    for (int j = i+2; j < polinomio.length(); j++) {

                        if (polinomio.charAt(j)=='-' || polinomio.charAt(j)=='+'){
                            i+=j-i;
                            break;
                        }

                        aPasar += polinomio.charAt(j);
                    }
                    potenciaActual = Integer.parseInt(aPasar);
                }else {
                    potenciaActual=1;
                }
            }

            if (potenciaActual > mayorPotencia){
                mayorPotencia = potenciaActual;
            }
            aPasar="";
        }



        // Creamos el array del objeto con la longitud que le toca
        this.coeficientes=new float[mayorPotencia+1];



        String monomio="";
        for (int i = 0; i <polinomio.length() ; i++) {



            if ((polinomio.charAt(i)=='-'||polinomio.charAt(i)=='+') &&i>0){
                funcion(monomio);
                monomio="";
            }

            monomio=monomio+polinomio.charAt(i);
            if (i+1>=polinomio.length()){
                funcion(monomio);
                monomio="";
            }
        }


        invertirArray();
        System.out.println(Arrays.toString(this.coeficientes));





        for (int i = 0; i < this.coeficientes.length; i++) {

            if (this.coeficientes[i]!=0){
                return;
            }
        }
        this.coeficientes=new float[1];
        this.coeficientes[0]=0;




    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {

        float[] mayor;
        float[] menor;
        if (this.coeficientes.length > p.coeficientes.length){
            mayor=this.coeficientes;
            menor=p.coeficientes;
        }else {
            mayor=p.coeficientes;
            menor=this.coeficientes;
        }


        float[] c = new float[mayor.length];

        mayor = invertirArray(mayor);
        menor = invertirArray(menor);


        for (int i = 0; i < c.length; i++) {
            if (i>=menor.length){
                c[i]=mayor[i];
            }else {
                c[i]=mayor[i]+menor[i];
            }
        }

        c= invertirArray(c);
        // Volvemos a poner los arrays en su orientacion correcta
        mayor = invertirArray(mayor);
        menor = invertirArray(menor);



        return new Polynomial(c);
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





    private void funcion(String monomio){
        String aPasar="";
        boolean simbolo=true; // TRUE POSITIVO ---- FALSE NEGATIVO

        int potencia=0;
        float coeficiente=0;

        if (monomio.charAt(0)=='-'){
            simbolo=false;
        }

        if (monomio.contains("x")){

            if (monomio.contains("^")){


                int i = monomio.length()-1;

                while (true){

                    if (monomio.charAt(i)=='^'){
                        break;
                    }
                    aPasar= monomio.charAt(i) + aPasar;
                    i--;
                }

                potencia = Integer.parseInt(aPasar);

                aPasar="";
            }else {
                potencia=1;


            }
            int i =0;
            while (true){
                if (monomio.charAt(i)=='x') break;
                aPasar=aPasar+monomio.charAt(i);
                i++;
            }

            coeficiente= Float.parseFloat(aPasar);

        }else {

            potencia=0;
            if (monomio.charAt(0)!='-' && monomio.charAt(0)!='+'){
                coeficiente= Float.parseFloat(monomio);
            }else {
                String monomio2="";
                for (int i = 1; i < monomio.length(); i++) {
                    monomio2= monomio2+monomio.charAt(i);
                }
                coeficiente=Float.parseFloat(monomio2);
            }

        }

        //multiplicacion del simbolo
        if (!simbolo){
            coeficiente=coeficiente*(-1);
        }



        this.coeficientes[potencia]+=coeficiente;


    }



    private float[] invertirArray(float coeficientes[]){
        for (int i = 0, j=coeficientes.length-1; i < j; i++,j--) {

            float swap;
            swap=coeficientes[i];
            coeficientes[i]=coeficientes[j];
            coeficientes[j]=swap;

        }
        return coeficientes;
    }

    private void invertirArray(){
        for (int i = 0, j=this.coeficientes.length-1; i < j; i++,j--) {
            float swap;
            swap=this.coeficientes[i];
            this.coeficientes[i]=this.coeficientes[j];
            this.coeficientes[j]=swap;
        }
    }


}


class main{
    public static void main(String[] args) {

        Polynomial p = new Polynomial("3x^2 + 5");
        Polynomial p1 = new Polynomial("3x-3");
        Polynomial p2 = new Polynomial();
        p2 = p.add(p1);

        System.out.println(p2.toString());

    }


}