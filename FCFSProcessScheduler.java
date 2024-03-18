public class FCFSProcessScheduler {

      void solveExample(Process[] processes) {
        // Sort according to arrival time
        divide(processes, 0, processes.length-1);

        // Gantt Chart
        int ganttChart[] = printGanttChart(processes);
        
        // Print Table P|AT|BT|CT|TAT|WT
        printProcessInfoTable(processes, ganttChart);
    }

      void printProcessInfoTable(Process[] processes, int[] ganttChart) {

        System.out.printf("----------------------------------------%n");
        System.out.printf("| %-10s | %-2s |%-2s | %-2s | %-3s | %-2s |%n", "PROCESS", "AT", "BT", "CT", "TAT", "WT");
        System.out.printf("----------------------------------------%n");

        int sumWT = 0, sumTAT = 0;

        for(int i = 0; i<processes.length; i++) {

            String P = processes[i].processName;
            int AT = processes[i].arrivalTime;
            int BT = processes[i].burstTime;
            int CT = ganttChart[i+1];
            int TAT = CT - AT;
            int WT = TAT - processes[i].burstTime;

            sumWT += WT;
            sumTAT += TAT;

            System.out.printf("| %-10s | %02d | %02d | %02d | %03d | %02d |%n", P, AT, BT, CT, TAT, WT);            
        }

        float averageWT = (float)sumWT/processes.length;
        float averageTAT = (float)sumTAT/processes.length;

        System.out.printf("----------------------------------------%n");
        System.out.printf("AverageWT = %2s %n", averageWT);
        System.out.printf("AverageTAT = %2s %n", averageTAT);
    }

     int[] printGanttChart(Process processes[]) {
        int ganttChart[] = new int[processes.length+1];

        System.out.print("\nGantt Chart \n");
        int currTime = 0;
        System.out.print(currTime);
        ganttChart[0] = currTime;

        for(int i = 0; i<processes.length; i++) {
            System.out.print(" "+processes[i].processName);

            currTime += processes[i].burstTime;
            ganttChart[1+i] = currTime;

            System.out.print(" "+currTime);
        }
        System.out.println("\n");

        return ganttChart;
    }

      void divide(Process[] arr, int s, int e) {
        if(s == e) return;
        int m = s + (e-s)/2;
        divide(arr, s, m);
        divide(arr, m+1, e);
        conquer(arr, s, e, m);
    }
    
      void conquer(Process[] arr, int s, int e, int m) {
        int n1 = m-s+1;
        int n2 = e-m;
        Process l[] = new Process[n1];
        Process r[] = new Process[n2];
    
        for(int i = 0; i<n1; i++) {
            l[i] = new Process(arr[s+i].arrivalTime, arr[s+i].burstTime, arr[s+i].processName);
        }
        for(int j = 0; j<n2; j++) {
            r[j] = new Process(arr[m+1+j].arrivalTime, arr[m+1+j].burstTime, arr[m+1+j].processName);
        }
    
        int i = 0, j = 0;
        int k = s;
        while(i<n1 && j<n2) {
            if(l[i].arrivalTime <= r[j].arrivalTime) {
                arr[k].copy(l[i]);
                k++;i++;
            } else {
                arr[k].copy(r[j]);
                k++;j++;
            }
        }
    
        while(i<n1) {
            arr[k].copy(l[i]);
            k++;i++;
        }
    
        while(j<n2) {
            arr[k].copy(r[j]);
            k++;j++;
        }
    }
}
