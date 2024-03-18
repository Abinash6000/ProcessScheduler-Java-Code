public class Practical3 {
    public static void main(String[] args) {
        System.out.println("Practical 3");
        System.out.println("\n**************FCFS**************\n");
        FCFSProcessScheduler fcfs = new FCFSProcessScheduler();

        System.out.println("Example 1");
        
        Process[] processes1 = { 
            new Process(0, 2, "P1"),
            new Process(1, 3,"P2"),
            new Process(2, 5,"P3"),
            new Process(3, 4,"P4"),
            new Process(4, 6,"P5") };

        fcfs.solveExample(processes1);

        System.out.println("Example 2");
        Process[] processes2 = { 
            new Process(0, 24, "P1"),
            new Process(0, 3,"P2"),
            new Process(0, 3,"P3") };

        fcfs.solveExample(processes2);

        System.out.println("Example 3");
        Process[] processes3 = { 
            new Process(0, 8, "P1"),
            new Process(1, 4,"P2"),
            new Process(2, 9,"P3"),
            new Process(3, 5,"P4") };

        fcfs.solveExample(processes3);

        System.out.println("\n**************SJF Non-Preemptive**************\n");
        SJFNonPreemptiveProcessScheduler sjf = new SJFNonPreemptiveProcessScheduler();
        System.out.println("Example 1");
        Process[] example1 = { 
            new Process(3, 1, "P1"),
            new Process(1, 4, "P2"),
            new Process(4, 2, "P3"),
            new Process(0, 6, "P4"),
            new Process(2, 3, "P5") };

        // SJF in non-preemptive mode
        // first sort according to arrival time
        sjf.divide(example1, 0, example1.length-1);
        // then sort according to burst time and print ganttChart
        int ganttChart[] = sjf.sortWithGanttChartPrinting(example1, 0, example1.length-1);

        // Print Table P|AT|CT|TAT|WT
        sjf.printProcessInfoTable(example1, ganttChart);

        
        System.out.println("\n**************SJF Preemptive**************\n");
        System.out.println("Example 2");
        Process[] example2 = { 
            new Process(3, 1, "P1"),
            new Process(1, 4, "P2"),
            new Process(4, 2, "P3"),
            new Process(0, 6, "P4"),
            new Process(2, 3, "P5") };
        SJFPreemptiveProcessScheduler.solveExample(example2);
        
        System.out.println("\nExample 3");
        Process[] example3 = { 
            new Process(0, 7, "P1"),
            new Process(1, 5, "P2"),
            new Process(2, 3, "P3"), 
            new Process(3, 1, "P4"),
            new Process(4, 2, "P5"),
            new Process(5, 1, "P6"), };
        SJFPreemptiveProcessScheduler.solveExample(example3);

        System.out.println("\nExample 4");
        Process[] example4 = { 
            new Process(0, 9, "P1"),
            new Process(1, 4, "P2"),
            new Process(2, 9, "P3") };
        SJFPreemptiveProcessScheduler.solveExample(example4);


        System.out.println("\nExample 5");
        Process[] example5 = { 
            new Process(0, 20, "P1"),
            new Process(15, 25, "P2"),
            new Process(30, 10, "P3"),
            new Process(45, 15, "P4") };
        SJFPreemptiveProcessScheduler.solveExample(example5);

        System.out.println("\n**************Priority Non-Preemptive**************\n");
        
        Process[] Pexample1 = { 
            new Process(0, 4, "P1", 2),
            new Process(1, 3, "P2", 3),
            new Process(2, 1, "P3", 4),
            new Process(3, 5, "P4", 5),
            new Process(4, 2, "P5", 5) };

        // first sort according to arrival time
        PrioritySchedulerNonPreemptive.divide(Pexample1, 0, Pexample1.length-1);
        // then sort according to burst time and print ganttChart
        int PganttChart[] = PrioritySchedulerNonPreemptive.sortWithGanttChartPrinting(Pexample1, 0, Pexample1.length-1);

        // Print Table P|AT|CT|TAT|WT
        PrioritySchedulerNonPreemptive.printProcessInfoTable(Pexample1, PganttChart);

        System.out.println("\n**************Priority Preemptive**************\n");

        
        System.out.println("Example 2");
        Process[] Pexample2 = { 
            new Process(0, 4, "P1", 2),
            new Process(1, 3, "P2", 3),
            new Process(2, 1, "P3", 4),
            new Process(3, 5, "P4", 5),
            new Process(4, 2, "P5", 5) };
        PrioritySchedulerPreemptive.solveExample(Pexample2);
    }
}
