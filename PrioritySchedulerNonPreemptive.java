import java.util.Comparator;
import java.util.PriorityQueue;

public class PrioritySchedulerNonPreemptive {

    static int[] sortWithGanttChartPrinting(Process[] processes, int s, int e) {
        System.out.println();
        System.out.print(0);
        int[] ganttChart = new int[processes.length+1];
        ganttChart[0] = 0;  // first process starts at time 0
        int totalBurstTime = 0;
        for(int i = 0; i<processes.length; i++) {
            totalBurstTime += processes[i].burstTime;
        }
        
        // Priority Queue to sort according to burst time
        PriorityQueue<Process> pq = new PriorityQueue<Process>(new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return p2.priority - p1.priority;
            }
        });

        int pointerForProcesses = 0, ganttChartPointer = 1;
        Process activeProcess = new Process(0, 0, "P0", 0);

        for(int i = 0; i<totalBurstTime; i++) {
            // to add new processes to the queue
            if(pointerForProcesses != processes.length && processes[pointerForProcesses].arrivalTime == i) {
                pq.add(processes[pointerForProcesses]);
                pointerForProcesses++;
            }

            // for the first process
            if(i == 0) {
                activeProcess = pq.poll();
                System.out.print(" " + activeProcess.processName);
                ganttChart[i+1] = activeProcess.burstTime;
                continue;
            }

            // for the rest of the processes
            activeProcess.remainingBurstTime--;
            if(activeProcess.remainingBurstTime == 0) {
                activeProcess = pq.poll();
                ganttChart[ganttChartPointer++] = i;
                System.out.print(" " + i + " " + activeProcess.processName);
            }
        }

        System.out.println(" " + totalBurstTime + "\n\n");
        ganttChart[ganttChartPointer] = totalBurstTime;

        return ganttChart;
    }

    static void printProcessInfoTable(Process[] processes, int[] ganttChart) {


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

    static void divide(Process[] arr, int s, int e) {
        if(s == e) return;
        int m = s + (e-s)/2;
        divide(arr, s, m);
        divide(arr, m+1, e);
        conquerAccArivalTime(arr, s, e, m);
    }

    static void conquerAccArivalTime(Process[] arr, int s, int e, int m) {
        int n1 = m-s+1;
        int n2 = e-m;
        Process l[] = new Process[n1];
        Process r[] = new Process[n2];
    
        for(int i = 0; i<n1; i++) {
            l[i] = new Process(arr[s+i].arrivalTime, arr[s+i].burstTime, arr[s+i].processName, arr[s+i].priority);
        }
        for(int j = 0; j<n2; j++) {
            r[j] = new Process(arr[m+1+j].arrivalTime, arr[m+1+j].burstTime, arr[m+1+j].processName, arr[m+1+j].priority);
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

