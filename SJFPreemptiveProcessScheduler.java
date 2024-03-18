import java.util.*;

public class SJFPreemptiveProcessScheduler {

    static void solveExample(Process[] processes) {
        int n = processes.length-1;

        // SJF in non-preemptive mode
        // first sort according to arrival time
        divide(processes, 0, n);
        // then sort according to burst time and print ganttChart
        ArrayList<Integer> ganttChart = sortWithGanttChartPrinting(processes, 0, n);
        int[] ganttChartArray = new int[ganttChart.size()];
        for(int i = 0; i<ganttChart.size(); i++) {
            ganttChartArray[i] = ganttChart.get(i);
        }

        // Print Table P|AT|CT|TAT|WT
        printProcessInfoTable(processes, ganttChartArray);
    }

    static ArrayList<Integer> sortWithGanttChartPrinting(Process[] processes, int s, int e) {
        System.out.println();
        ArrayList<Integer> ganttChart = new ArrayList<>();
        ganttChart.add(0);  // first process starts at time 0
        int totalBurstTime = 0;
        for(int i = 0; i<processes.length; i++) {
            totalBurstTime += processes[i].burstTime;
        }
        
        // Priority Queue to sort according to burst time
        PriorityQueue<Process> pq = new PriorityQueue<Process>(new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return p1.remainingBurstTime - p2.remainingBurstTime;
            }
        });

        int pointerForProcesses = 0;
        String prevProc = "";
        // Process activeProcess = new Process(0, 0, "P0");

        for(int i = 0; i<totalBurstTime; i++) {
            // to add new processes to the queue
            if(pointerForProcesses != processes.length && processes[pointerForProcesses].arrivalTime == i) {
                pq.add(processes[pointerForProcesses]);
                pointerForProcesses++;
            }

            if(i == 0) {
                prevProc = pq.peek().processName;
                System.out.print(i + " " + pq.peek().processName);
                pq.peek().remainingBurstTime--;
                continue;
            }
            // decrease the remaining burstTime of the active process

            if(prevProc != pq.peek().processName) {
                ganttChart.add(i);
                System.out.print(" " + i + " " + pq.peek().processName);
                prevProc = pq.peek().processName;
            }

            pq.peek().remainingBurstTime--;

            if(pq.peek().remainingBurstTime == 0) {
                pq.poll().completionTime = i+1;
            }

        }

        System.out.println(" " + totalBurstTime + "\n\n");
        ganttChart.add(totalBurstTime);

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
            // int CT = ganttChart[i+1];
            int CT = processes[i].completionTime;
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