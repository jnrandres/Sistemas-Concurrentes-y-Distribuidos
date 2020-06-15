/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public class NewMain {

    public static void main(String[] args) {
    //int arr[100]={5,26,12,6,1,4,7,28,40,41,42,53,1000,65,52,70,81,52,56,312,20,145,152};
    int[] arr = new int[5000];
    
    for(int z = 0;z< arr.length;z++){
        arr[z]= (int) (Math.random()*10000);
    }
    
    int n = arr.length;

    
    //*********** MERGERSORT Simple ********
    
    mergersort ordernar= new mergersort();
    System.out.println("Array original: ");
    for (int value : arr) {
        System.out.print(value + " ");
    }
    
    System.out.println();
    System.out.print("Lista Ordenada :");
    ordernar.sort(arr, 0,n-1);
    System.out.println();
    ordernar.imprimir(arr);
    }
    
    
    //*********** MERGERSORT Paralelizado ************
    
    /*mergersortparalelo ordernarparalelo= new mergersortparalelo();
    System.out.println();
    System.out.println("Array original: ");
    for (int value : arr) {
        System.out.print(value + " ");
    }
    
    System.out.println();

    System.out.print("Lista Ordenada :");
    ordernarparalelo.parallelmergersort(arr, 0,n-1,4);
    System.out.println();
    ordernarparalelo.imprimir(arr);
    }*/

}
