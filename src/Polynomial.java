import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {
    //Atributos
    private float[] coeficientes;


    /**
     * Constructor per defecte. Genera un polinomi zero
     */
    public Polynomial() {
        coeficientes = new float[1];
        coeficientes[0]=0;
    }

    /**
     * @param coeficientes
     *
     * Recibimos un array con todos los coeficientes, lo que haremos primero de
     * todo es hacer una copa de ese array ya que asi si el original se modifica
     * nosotros seguiremos con el que nos hayan pasado desde el principio
     *
     * Despues desde la izq iremos mirando los ceros que hay ya que los 0 a la izquierda no
     * nos interesan y despues desde el primer numero por la izq que no sea 0 lo copiaremos
     * a el array membre coeficientes
     */
    public Polynomial(float[] coeficientes) {
        //Lo que hago aqui es crear un nuevo array para no referenciar al
        // que nos pasan ya que el usuario podria modificar ese array
        //entonces usaremos una copia
        float [] newCoeficientes = new float[coeficientes.length];
        for (int i = 0; i < coeficientes.length; i++) {
            newCoeficientes[i]=coeficientes[i];
        }
        int x = 0;
        for (int i = 0; i < newCoeficientes.length; i++) {
            if (newCoeficientes[i]==0){
                x++;
            }else {
                break;
            }
        }
        if (x==newCoeficientes.length){

            this.coeficientes=new float[1];
            this.coeficientes[0]=0;
        }else {
            this.coeficientes=new float[newCoeficientes.length-x];
            for (int i = 0, j=x; i < this.coeficientes.length; i++, j++) {
                this.coeficientes[i] = newCoeficientes[j];
            }
        }

    }

    /**
     * @param stringPolinomio  Recibimos un polinomio en formato de String (2x^3 - 4 + 5x^2 + 32)
     *
     *
     *
     */
    // Constructor a partir d'un string
    public Polynomial(String stringPolinomio) {

        if (stringPolinomio.length()==1||(stringPolinomio.length()==2 && (stringPolinomio.charAt(0)=='-'||stringPolinomio.charAt(0)=='+'))){
            this.coeficientes=new float[1];
            if (stringPolinomio.charAt(0)=='x'){
                this.coeficientes= new float[2];
                this.coeficientes[0]=1;
                this.coeficientes[1]=0;

            }else {

                this.coeficientes[0]=Float.parseFloat(stringPolinomio);
            }
            return;
        }
        if (stringPolinomio.charAt(0)!='x'&&stringPolinomio.charAt(0)!='-'&&stringPolinomio.charAt(0)!='+'){
            stringPolinomio = "+"+stringPolinomio;
        }

        // Rutina que elimina espacios
        StringBuilder polinomio = new StringBuilder();
        for (int i = 0; i < stringPolinomio.length(); i++) {
            if (stringPolinomio.charAt(i)!=' '){
                polinomio.append(stringPolinomio.charAt(i));
            }
        }

        int mayorPotencia = buscarMayorPotencia(polinomio.toString());

        // Creamos el array del objeto con la longitud que le toca
        this.coeficientes=new float[mayorPotencia+1];

        // Llamamos a la funcion que añade cada
        // monomio en la posicion de coeficientes que le pertañe
        String monomio="";
        for (int i = 0; i <polinomio.length() ; i++) {
            if ((polinomio.charAt(i)=='-'||polinomio.charAt(i)=='+') &&i>0){
                creacionMonomio(monomio);
                monomio="";
            }
            monomio=monomio+polinomio.charAt(i);
            if (i+1>=polinomio.length()){
                creacionMonomio(monomio);
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
                    // Si es todo 0 lo que hacemos es restaurar
                    //  el array y hacer que sea de longitud 1 con un 0
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

            // Si hay algun 0 a la izq lo que
            // hacemos es quitar esos 0 de la izquierda
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
    }


    /**
     * @param poligono recibimos dos poligonos (1 el que nos pasan y 2 el que pasamos llamando a el metodo)
     * @return devolvemos un nuevo polinomo que es el resultado de la suma de los dos polinomios
     *
     *
     * Este metodo lo que hace es recibir 2 polinomios, 1 el que le pasamos y otro el que llamamos a ese polinomio.
     * Primero identificamos cual de los dos tiene un mayor exponente y lo asignamos a mayor, el que es menor lo añadimos a menor.
     * Despues invertimos los dos arrays de coeficientes que tenemos (mayor y menor) y lo que hacemos es ir recorriendo el array
     * resultado el cual sera de la misma longitud que el mayor de los dos iniciales.
     * i mientras que i sea menos que la longitud del menor, iremos sumando las dos posiciones i de cada array
     * si i es mayor que la longitud del menor, lo que haremos sera simplemente añadir la posicion de i de mayor en la posicion de i de resultado
     */
    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial poligono) {

        float[] mayor;
        float[] menor;
        if (this.coeficientes.length > poligono.coeficientes.length){
            mayor=this.coeficientes;
            menor=poligono.coeficientes;
        }else {
            mayor=poligono.coeficientes;
            menor=this.coeficientes;
        }

        float[] resultado = new float[mayor.length];
        mayor = invertirArray(mayor);
        menor = invertirArray(menor);
        for (int i = 0; i < resultado.length; i++) {
            if (i>=menor.length){
                resultado[i]=mayor[i];
            }else {
                resultado[i]=mayor[i]+menor[i];
            }
        }
        resultado= invertirArray(resultado);
        // RESTABLECEMOS LOS ARRAYS DE COEFICIENTES ORIGINALES
        mayor = invertirArray(mayor);
        menor=invertirArray(menor);
        return new Polynomial(resultado);
    }


    /**
     * @param polinomio recibimos dos polinomios (1 el que pasamos por parametro y 2 el que pasamos llamando al metodo (this))
     * @return devolvemos un nuevo polinomio que sera el resultado de la multiplicacion de los dos polinomios originales
     *
     * Esta funcion lo que hace es identificar las dos mayores potencias de cada polinomio (la longitud de cada uno - 1)
     * lo que haremos sera dependiendo de cual sea mayor llamar a la funcion multiplicar de una o otra manera, esta funcion
     * nos retornara el array de el resultado, con ese array llamaremos al constructor de polinomio lo retornaremos
     *
     */
    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial polinomio) {
        int mayorPotencia1 = this.coeficientes.length-1, mayorPotencia2 = polinomio.coeficientes.length-1, longitud = mayorPotencia1+mayorPotencia2;

        float[] array ;
        if (mayorPotencia1 > mayorPotencia2){
            array = multiplicar(polinomio.coeficientes,this.coeficientes,longitud);
        }else {
            array = multiplicar(this.coeficientes,polinomio.coeficientes,longitud);
        }
        return new Polynomial(array);
    }







    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {

        float[] dividendo = copiarArray(this.coeficientes);
        float[] divisor = copiarArray(p2.coeficientes);
        dividendo = invertirArray(dividendo);
        divisor = invertirArray(divisor);

        float[] coeficientesDivision ;
        coeficientesDivision = new float[dividendo.length-1];

        int posicion;
        int x;
        double divi;
        for (int i = dividendo.length-1; i >= divisor.length-1 ; i--) {
            divi=0;
            x = 1;
            for (int j = divisor.length-1; j >= 0 ; j--) {
                posicion=i-j;
                if (j == divisor.length-1){
                    if (posicion<0){
                        posicion=0;
                    }
                    coeficientesDivision[posicion] = dividendo[i]/divisor[j];
                    divi = dividendo[i]/divisor[j];
                    dividendo[i]=0;
                }else {
                    posicion= i-x;
                    if (posicion<0){
                        posicion=0;
                    }
                    dividendo[posicion] += (float) ((divisor[j] * divi)*(-1));
                    x++;
                }
            }
        }
        Polynomial [] polinomios = {new Polynomial(invertirArray(coeficientesDivision)),new Polynomial(invertirArray(dividendo))};
        return polinomios;
    }












    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {

        float[] cofi = copiarArray(this.coeficientes);

        float[] resultado = new float[1];
        if (cofi.length==1){
            //No hay solucion
            return null;
        }else if (cofi.length==2){

            //Primer grado
            resultado = new float[1];
            resultado[0] = (cofi[1]*(-1))/cofi[0];
            System.out.println(resultado[0]);

        }else if (cofi.length==3){

            //Segundo grado
            float a = cofi[0];
            float b = cofi[1];
            float c = cofi[2];
            float discriminante = b*b - 4*a*c;
            if (discriminante < 0){
                return null;
            }
            resultado = segundoGrado(cofi);
        }else if (cofi.length == 5 && (cofi[1]==0 && cofi[3]==0)){
            //Bicuadratica

            //Segundo grado
            float a = cofi[0];
            float b = cofi[2];
            float c = cofi[4];
            float discriminante = b*b - 4*a*c;
            if (discriminante < 0){
                return null;
            }
            float[] cofi1 = new float[3];
            cofi1[0]=cofi[0];
            cofi1[1]=cofi[2];
            cofi1[2]=cofi[4];
            resultado =  segundoGrado(cofi1);
            float[] resultado1 = new float[resultado.length*2];

            for (int i = 0, j=0; i < resultado.length; i++, j++) {
                resultado1[j] =(float) Math.sqrt(resultado[i]) ;
                j++;
                resultado1[j] = (float) Math.sqrt(resultado[i])*(-1) ;
            }
            resultado = resultado1;
        }else{

            //Todo es 0 excepto maximo exponente y termino independiente
            int x =0;
            for (int i = 0; i < cofi.length; i++) {
                if (cofi[i]!=0)x++;
            }
            if (x==2 || (x==1 && cofi[cofi.length-1]==0)){


                float a = cofi[0];
                float b = cofi[cofi.length-1];



                float discriminante = a/b;
                int indice = cofi.length-1;
                if (discriminante< 0 && ((cofi.length-1)%2 == 0)){
                    return null;
                }else if ((cofi.length-1)%2 == 0){
                    // DOS SOLUCIONES

                    resultado = new float[2];

                    resultado[0] =(float) Math.pow(discriminante, 1/indice);
                    resultado[1] = (float)Math.pow(discriminante, 1/indice)*(-1);

                }else{
                    // UNA SOLUCION
                    resultado = new float[1];
                    resultado[0] = (float)Math.pow(discriminante, 1/indice);
                }


            }




        }




        resultado = burbuja(resultado);
        return resultado;
    }


    private float[] segundoGrado(float[]cofi){
        float a = cofi[0];
        float b = cofi[1];
        float c = cofi[2];
        float[] dev = new float[0];

        float discriminante = b*b - 4*a*c;
        if (discriminante == 0){

            float x =(float) (-b + Math.sqrt(discriminante))/(2*a);
            System.out.println("Solo hay una solucion: " + x);
            dev = new float[1];
            dev[0]=x;
            return dev;


        }else {
            float x1 =(float) (-b + Math.sqrt(discriminante))/(2*a);
            float x2 =(float) (-b - Math.sqrt(discriminante))/(2*a);
            //imprime los valores
            System.out.println("La solucion de x1: " + x1);
            System.out.println("La solucion de x2: " + x2);

            dev = new float[2];
            dev[0] = x1;
            dev[1] = x2;
        }

        return dev;
    }



























    /**
     * @param objeto donde objeto es otro polinomio
     * @return devuelve true/false dependiendo si los dos polinomios son iguales o no
     */
    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object objeto) {
        Polynomial p1 = (Polynomial) objeto;
        if (p1.coeficientes.length != this.coeficientes.length)return false;
        for (int i = 0; i < this.coeficientes.length; i++) {
            if (this.coeficientes[i]!=p1.coeficientes[i]){
                return false;
            }
        }
        return true;
    }


    /**
     * @return Devolvemos un polinomio en forma de string
     *
     * En esta funcion lo que hacemos es leer posicion a
     * posicion del array Coeficientes de ese polinomio, y
     * la vamos añadiendo con sus respectivas x si le pertañen y
     * sus respectivas potencias
     *
     * Ejemplos:
     * [3,-2,0] --> 3x² -2x
     * [4,-1,0,11] --> 4x³ - x² + 11
     *
     */
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

            if (cof1==cof2){// SE USARA NUMEROS ENTEROS
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
            }else{// SE USARA NUMEROS DECIMALES
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
                        float num = coeficientesInvertidos[i];
                        if (simbolo.equals("-")){
                            num=num*(-1);
                        }
                        devolver = num+ "x^" + i + devolver;
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



    //FUNCIONES QUE HE NECESITADO



    /**
     * @param coef1 Coeficientes del primero polinomio
     * @param coef2 Coeficientes del segundo polinomio
     * @param longitud es la longuitud que tendra que usar para crear el
     *                 array del nuevo polinomio resultante de la
     *                 multiplicación
     * @return devuelve array el cual sera los coeficientes del resultado de la multiplicacion
     *
     * Este metodo recibe dos arrays de dos polinomios y lo que hace es ir recorriendo uno a
     * uno los coeficientes del primer polinomio, y los va multiplicando con todos los coeficientes
     * del segundo polinomio
     */
    private float[] multiplicar(float[] coef1, float[] coef2, int longitud){
        float[] resultado = new float[longitud+1];
        //invertimos los arrays
        coef1 = invertirArray(coef1);
        coef2 = invertirArray(coef2);

        for (int i = 0; i < coef1.length; i++) {
            for (int j = 0; j < coef2.length; j++) {
                resultado[i+j]+=coef1[i]*coef2[j];
            }
        }
        resultado = invertirArray(resultado);
        return resultado;
    }

    /**
     * @param monomio Recibimos una String la cual contiene un monomio
     *
     * Lo que hacemos es recibir un monomio, identificar cual es su potencia (sin incognita potencia = 0,
     * con incognita potencia = 1, con incognita y potencia potencia=potencia)
     *
     * Despues de identificar la potencia, identificamos el coeficiente de ese
     * monomio y lo añadimos al array de coeficientes en la posicion(potencia)
     */
    private void creacionMonomio(String monomio){
        String aPasar="";
        boolean simbolo=true; // TRUE POSITIVO ---- FALSE NEGATIVO
        int potencia=0;
        float coeficiente=0;

        if (monomio.charAt(0)=='-'){
            // Definimos que el monomio es negativo
            simbolo=false;
        }
        if (monomio.contains("x")){
            if (monomio.contains("^")){
                if (monomio.charAt(0)=='x'){
                    monomio= 1 + monomio;
                }else if (monomio.charAt(0)=='-'&&monomio.charAt(1)=='x'){
                    String monomio2 ="";
                    for (int i = 0; i < monomio.length(); i++ ) {
                        if (i==1){
                            monomio2 = monomio2 + 1;
                        }
                            monomio2 = monomio2 + monomio.charAt(i);
                    }
                }
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
            if (i+1>=monomio.length()) {
                // no hacemos nada
            }else{
                while (true){
                    if (monomio.charAt(i)=='x') break;
                    aPasar=aPasar+monomio.charAt(i);
                    if (i+1>=monomio.length()){
                        break;
                    }
                    i++;
                }
            }

            if  (aPasar.equals("")){
                aPasar=""+1;
            }else if(aPasar.equals("-")){
                aPasar=""+(-1);
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


    /**
     * @param polinomio  Esta creacionMonomio recibe una String la cual contiene un polinomio
     * @return devuelve un Int el cual es la mayor potencia de ese polinomio que le hemos pasado
     *
     * hasta que acabe el polinomio
     * Lo que hace esya creacionMonomio es recorrer el polinomio, y va mirando monomio a monomio cual
     * es su potencia y esa potencia actual la compara con la mayor potencia, en el case de que
     * la actual sea mayor a la mayor, se reestablece la mayor por la actual y vuelve a empezar
     */
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

    // FUNCION QUE TE DEVUELVE UNA COPIA DE UN ARRAY QUE PASAMOS
    private float[] copiarArray(float[] array){
        float[]copia = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            copia[i] = array[i];
        }
        return copia;
    }

    /**
     * @param coeficientes Recibe un array de floats
     * @return devuelve el mismo array que ha recibido pero invertido (IMPORTANTE, DEVUELVE UN NUEVO ARRAY, EL ORIGINAL NO SE MODIFICA)
     */
    private float[] invertirArray(float coeficientes[]){

        //te devuelve una copia
        float[] nuevoCoeficiente = copiarArray(coeficientes);

        for (int i = 0, j=nuevoCoeficiente.length-1; i < j; i++,j--) {

            float swap;
            swap=nuevoCoeficiente[i];
            nuevoCoeficiente[i]=nuevoCoeficiente[j];
            nuevoCoeficiente[j]=swap;

        }
        return nuevoCoeficiente;
    }

    /**
     * Esta creacionMonomio lo que hace es al llamarla, invertimos
     * el array de coeficientes del objeto desde el cual llamamos
     * a esta creacionMonomio/metodo.
     *
     * No recibe ningun parametro ni devuelve ningun otro parametro
     */
    private void invertirArray(){
        for (int i = 0, j=this.coeficientes.length-1; i < j; i++,j--) {
            float swap;
            swap=this.coeficientes[i];
            this.coeficientes[i]=this.coeficientes[j];
            this.coeficientes[j]=swap;
        }
    }



    private float[] burbuja(float[] ar){
        float swap;
        for (int j = ar.length; j > 0; j--) {
            for (int i = 1; i < j; i++) {
                if (ar[i - 1] > ar[i]) {
                    swap = ar[i];
                    ar[i] = ar[i - 1];
                    ar[i - 1] = swap;
                }
            }
        }
        return ar;
    }














    public static void main(String[] args) {
        Polynomial p1 ;
        Polynomial p2 ;
        Polynomial p3 ;
        Polynomial p4 ;

        p1 = new Polynomial("x - 9");
        p1.roots();


    }
}