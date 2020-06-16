//By : Anthony Reynold Alcarraz Mucha

class mergersort {
    public void sort( int arr[],int left, int right){
        if (left<right){
            int middle = (left +right)/2;
            sort(arr,left,middle);
            sort(arr, middle+1,right);
            merge(arr,left,middle,right);
        }
    }

    public void merge(int arr[],int left,int middle,int right){ 
        
        // Longidudes de los vectores 
        int n1 = middle - left + 1;
        int n2 = right - middle;

        //Arrays temporales
        int leftarray[] = new int [n1];
        int rightarray[] = new int [n2];
        
        for (int i=0;i<n1;i++){
            leftarray[i]= arr[left + i];
        }

        for (int j=0;j<n2;j++){
            rightarray[j]= arr[middle + j + 1];
        }

        int i=0,j=0;
        int k=left;
        
        //Ordenamiento
        while (i<n1 && j<n2){
            if(leftarray[i]<rightarray[j]){
                arr[k]=leftarray[i];
                i++;
            }else{
                arr[k]=rightarray[j];
                j++;
            }
            k++;
        }
        while(i< n1){
            arr[k]=leftarray[i];
            i++;
            k++;
        }
        while(j<n2){
            arr[k]=rightarray[j];
            j++;
            k++;
        }
    }
    public void imprimir(int arr[]){
        int n = arr.length;
        for(int i=0;i<n;i++){
            System.out.print(arr[i] + " ");
        }
    }
}
