

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
        this.coeficientes=new float[coeficientes.length];
        for (int i = 0; i < coeficientes.length; i++) {
            this.coeficientes[i] = coeficientes[i];
        }


    }

    // Constructor a partir d'un string
    public Polynomial(String s) {




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




        return false;
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        String devolver = "";
        int elevacion = 0;
        String simbolo = "";
        String coef = "";
        String devolver1="";
        int comprovacion;

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

        if (devolver.equals("")){
            return "0";
        }
        return devolver;
    }



}


class main{
    public static void main(String[] args) {

        Polynomial p = new Polynomial(new float[]{0,0,0});
        System.out.println(p.toString());

    }
}