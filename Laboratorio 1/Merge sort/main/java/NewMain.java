
//By : Anthony Reynold Alcarraz Mucha

import java.util.Arrays;

public class NewMain {
    
    public static void main(String[] args) {

    
    int[] arr = new int[1000000];
    
    for(int z = 0;z< arr.length;z++){
        arr[z]= (int) (Math.random()*10000);
    }
    
    int[] arr2=Arrays.copyOfRange(arr,0,arr.length);
    
    int n = arr.length;

    long start = System.nanoTime();
    //*********** MERGERSORT Simple ********
    
    System.out.println("Sin Paralelizar ");
    mergersort ordernar= new mergersort();
    /*System.out.println("Array original: ");
    for (int value : arr) {
        System.out.print(value + " ");
    }*/
    
    System.out.println();
    //System.out.print("Lista Ordenada :");
    ordernar.sort(arr, 0,n-1);
    System.out.println();
    //ordernar.imprimir(arr);
    
    long end  = System.nanoTime();
    long time = end - start;
    System.out.println();
    System.out.println("Tiempo sin paralelizar: " + time/1000000000.0);


    
    long start2 = System.nanoTime();
    
    //*********** MERGERSORT Paralelizado ************
    
    mergersortparalelo ordernarparalelo= new mergersortparalelo();
    System.out.println();
    System.out.println("Paralelizado");
    /*System.out.println("Array original: ");
    for (int value : arr2) {
        System.out.print(value + " ");
    }*/
    
    System.out.println();


    int num_threads= 16 ;
    //System.out.print("Lista Ordenada :");
    ordernarparalelo.parallelmergersort(arr2,0,n-1,num_threads);
    System.out.println();
    //ordernarparalelo.imprimir(arr2);
    long end2  = System.nanoTime();
    long time2 = end2 - start2;
    System.out.println();
    System.out.println("Tiempo paralelizado: " + time2/1000000000.0);
    
    }


}
