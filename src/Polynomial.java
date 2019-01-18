
import java.lang.management.PlatformLoggingMXBean;
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
            for (int i = 0, j=x; i < this.coeficientes.length; i++, j++) {
                this.coeficientes[i] = coeficientes[j];
            }
        }

    }

    // Constructor a partir d'un string
    public Polynomial(String s) {

        if (s.length()==1||(s.length()==2 && (s.charAt(0)=='-'||s.charAt(0)=='+'))){
            this.coeficientes=new float[1];
            if (s.charAt(0)=='x'){
                this.coeficientes[0]=1;
            }else {

                this.coeficientes[0]=Float.parseFloat(s);
            }
            return;
        }
        if (s.charAt(0)!='x'&&s.charAt(0)!='-'&&s.charAt(0)!='+'){
            s = "+"+s;
        }


        // Rutina que elimina espacios
        StringBuilder polinomio = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i)!=' '){
                polinomio.append(s.charAt(i));
            }
        }

        int mayorPotencia = buscarMayorPotencia(polinomio.toString());


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




        if (this.coeficientes.length>1){

            //Comprovacion fallo 0 a la izq
            int comp=0;
            while (true) {
                if (this.coeficientes[comp]==0){
                    if (comp+1==this.coeficientes.length){
                        this.coeficientes=new float[1];
                        this.coeficientes[0]=0;
                        return;
                    }

                    comp++;
                    continue;

                }else {
                    break;
                }
            }

            if (comp!=0){
                int longi =this.coeficientes.length;

                float[] copia = new float[longi];
                for (int i = 0; i < longi; i++) {
                    copia[i]=this.coeficientes[i];
                }

                this.coeficientes = new float[longi-comp];
                for (int i = 0; i < this.coeficientes.length; i++) {
                    this.coeficientes[i]=copia[comp];
                    comp++;
                }
            }

        }


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

        int mayorPotencia1 = this.coeficientes.length-1;
        int mayorPotencia2 = p2.coeficientes.length-1;
        int longitud = mayorPotencia1+mayorPotencia2;


        float[] array ;
        if (mayorPotencia1 > mayorPotencia2){
            array = multiplicar(p2.coeficientes,this.coeficientes,longitud);
        }else {
            array = multiplicar(this.coeficientes,p2.coeficientes,longitud);
        }




        Polynomial aDevolver = new Polynomial(array);

        return new Polynomial(array);
    }


    private float[] multiplicar(float[] coef1, float[] coef2, int longitud){
        float[] array = new float[longitud+1];


        //invertimos los arrays
        coef1 = invertirArray(coef1);
        coef2 = invertirArray(coef2);

        for (int i = 0; i < coef1.length; i++) {

            for (int j = 0; j < coef2.length; j++) {
            array[i+j]+=coef1[i]*coef2[j];
            }

        }

        array = invertirArray(array);
        return array;
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
        if (coeficientes.length==1 && coeficientes[0]==0){
            return "0";
        }

        String devolver = "";

        float[] coeficientesInvertidos = invertirArray(this.coeficientes);


        String simbolo="";
        int potencia=0;
        int cof1=0;
        float cof2=0;

        for (int i = 0; i < coeficientesInvertidos.length; i++) {

            cof1 = (int) coeficientesInvertidos[i];
            cof2 = coeficientesInvertidos[i];

            if (coeficientesInvertidos[i]==0){
                continue;
            }
            if (coeficientesInvertidos[i]>=0){
                simbolo="+";
            }else {
                simbolo="-";
                cof1 = (int) coeficientesInvertidos[i]*(-1);
                cof2 = coeficientesInvertidos[i]*(-1);
            }


            if (cof1==cof2){
                // SE USARA NUMEROS ENTEROS
                if (i==0){
                    // Posicion 0 ira sin X

                    devolver = cof1 + devolver;


                }else if (i==1){
                    // Posicion 1 ira con X sin capellan

                    if (cof1==1){
                        devolver = "x" + devolver;
                    }else {
                        devolver = cof1 + "x" + devolver;
                    }

                }else{
                    //Todas las demas posiciones iran con X y con ^ donde la potencia sera la I
                    if (cof1==1||cof1==-1){
                        devolver ="x^" + i + devolver;
                    }else {
                            devolver = cof1 + "x^" + i + devolver;
                    }

                }


            }else{
                // SE USARA NUMEROS DECIMALES

                if (i==0){
                    // Posicion 0 ira sin X

                    devolver = coeficientesInvertidos[i] + devolver;


                }else if (i==1){
                    // Posicion 1 ira con X sin capellan
                    if (coeficientesInvertidos[i]==1){
                        devolver = "x" + devolver;

                    }else {
                        devolver = coeficientesInvertidos[i] + "x" + devolver;
                    }

                }else{
                    //Todas las demas posiciones iran con X y con ^ donde la potencia sera la I
                    if (cof1==1){
                        devolver ="x^" + i + devolver;
                    }else {
                        devolver = coeficientesInvertidos[i] + "x^" + i + devolver;
                    }

                }

            }








            if (i+1 != coeficientesInvertidos.length){
                devolver = " "+simbolo+" "+devolver;
            }else if (simbolo.equals("-")){
                devolver = simbolo+""+devolver;
            }





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
            int i =1;
            while (true){
                if (monomio.charAt(i)=='x') break;
                aPasar=aPasar+monomio.charAt(i);
                i++;
            }

            if  (aPasar.equals("")){
                aPasar=""+1;
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




    private int buscarMayorPotencia(String polinomio){
        int mayorPotencia = 0;
        int potenciaActual=0;

        String aPasar="";

        // Encotrnamos la mayor potencia de todo el polinomo
        for (int i = 0; i < polinomio.length(); i++) {

            if (polinomio.charAt(i)=='x'){

                if  (i+1>=polinomio.length()){
                    potenciaActual=1;
                }else {
                    if (polinomio.charAt(i+1)=='^' ){


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

            }

            if (potenciaActual > mayorPotencia){
                mayorPotencia = potenciaActual;
            }
            aPasar="";
        }
        return mayorPotencia;
    }








    private float[] invertirArray(float coeficientes[]){

        //te devuelve una copia
        float[] nuevoCoeficiente = new float[coeficientes.length];
        for (int i = 0; i < coeficientes.length; i++) {
            nuevoCoeficiente[i] = coeficientes[i];
        }

        for (int i = 0, j=nuevoCoeficiente.length-1; i < j; i++,j--) {

            float swap;
            swap=nuevoCoeficiente[i];
            nuevoCoeficiente[i]=nuevoCoeficiente[j];
            nuevoCoeficiente[j]=swap;

        }
        return nuevoCoeficiente;
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

        Polynomial p1;
        Polynomial p2;


        p1 = new Polynomial("x^4 - 6x^2 + 8");
        p2 = new Polynomial("-6x^6 - 91x + 12");
        //assertEquals("-6x^10 + 36x^8 - 48x^6 - 91x^5 + 12x^4 + 546x^3 - 72x^2 - 728x + 96", );

        System.out.println(p1.mult(p2).toString());
    }


}