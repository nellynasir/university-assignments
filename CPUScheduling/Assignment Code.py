import matplotlib.pyplot as plt
import tkinter as tk
from tkinter import ttk
from tkinter import messagebox


def mainMenu():
    root = tk.Tk()
    root.title("Main Menu")

    font = ("Arial", 12)

    # Setting up labels and table contents
    heading = ['Student ID', 'Name', 'Email']
    student_details = [[
        '1201102251', 'SABRINA AMALYN BINTI AMINUR RIZAL',
        '1201102251@student.mmu.edu.my'
    ],
                       [
                           '1211111583', 'IZZA NELLY BINTI MOHD NASIR',
                           '1211111583@student.mmu.edu.my'
                       ],
                       [
                           '1181102053', 'NIA DAYANA BINTI SOFIYANR',
                           '1181102053@student.mmu.edu.my'
                       ],
                       [
                           '1211208476',
                           'NUR ARISSA HANANI BINTI MOHAMED ADZLAN',
                           '1211208476@student.mmu.edu.my'
                       ]]

    # Create frame for content
    content_frame = tk.Frame(root)
    content_frame.pack(padx=20, pady=20)

    # Add header text
    header_label = tk.Label(content_frame,
                            text="CSN6214 Operating System",
                            font=("Arial", 14))
    header_label.grid(row=0, column=0, columnspan=2, pady=(0, 10))

    semester_label = tk.Label(content_frame,
                              text="Trimester 1, 22/23, 2210",
                              font=("Arial", 12))
    semester_label.grid(row=1, column=0, columnspan=2)

    assignment_label = tk.Label(
        content_frame,
        text="Assignment: Simulation of CPU Scheduling Algorithms",
        font=("Arial", 14))
    assignment_label.grid(row=2, column=0, columnspan=2, pady=(20, 10))

    lecturer_label = tk.Label(content_frame,
                              text="Lecturer: Mr. Firdaus",
                              font=("Arial", 12))
    lecturer_label.grid(row=3, column=0, columnspan=2)

    tutorial_label = tk.Label(content_frame,
                              text="Tutorial Section: TT6L",
                              font=("Arial", 12))
    tutorial_label.grid(row=4, column=0, columnspan=2)

    group_member_label = tk.Label(content_frame,
                                  text="Group Member Details:",
                                  font=("Arial", 14))
    group_member_label.grid(row=5, column=0, columnspan=2, pady=(15, 5))

    # Create the table with student details
    tree = ttk.Treeview(content_frame,
                        columns=heading,
                        show='headings',
                        height=4)
    for col in heading:
        tree.heading(col, text=col)
        tree.column(col, width=280, anchor="center")

    for student in student_details:
        tree.insert("", tk.END, values=student)

    tree.grid(row=6, column=0, columnspan=2, pady=(10, 15))

    # Create buttons
    start_button = tk.Button(content_frame,
                             text="START",
                             width=23,
                             command=lambda: inputProcessDetails())
    start_button.grid(row=7, column=0, pady=(0, 15))

    exit_button = tk.Button(content_frame,
                            text="EXIT",
                            width=23,
                            command=root.quit,
                            bg="red")
    exit_button.grid(row=7, column=1, pady=(0, 15))

    root.mainloop()


# Global process list
process = []


def inputProcessDetails():

    def display_input_data():
        # Get the current values from the input fields
        a = arrival_time_entry.get()
        b = burst_time_entry.get()
        p = priority_entry.get()

        # Initialize a new process list with the input data
        new_process = initProcess(a, b, p)

        # Clear the existing rows in the table
        for row in table.get_children():
            table.delete(row)

        # Insert the new process data into the table
        for row in new_process:
            table.insert('', 'end', values=row)

        # Update the global process list
        global process
        process = new_process

    def confirm_input_data():
        global process
        # print("Process List:", process)  # Debugging line
        if len(process) < 3 or len(process) > 10:
            messagebox.showerror(
                "Error",
                "The number of processes can range from 3 to 10.\nPlease ensure your input is within range before confirming."
            )
        elif 3 <= len(process) <= 10:
            selectPSA(process)
            root.quit()  # End the window after confirming

    def initProcess(arrivalTime, burstTime, priority):
        process = []
        arrivalTime = arrivalTime.split()
        burstTime = burstTime.split()
        priority = priority.split()

        for i in range(len(burstTime)):
            process.append([
                'P' + str(i),
                int(arrivalTime[i]),
                int(burstTime[i]),
                int(priority[i])
            ])

        return process

    # Create the main window
    global root
    root = tk.Tk()
    root.title("Input Process Details")

    # Column setup for input data
    input_frame = tk.Frame(root)
    input_frame.grid(row=0, column=0, padx=10, pady=10)

    tk.Label(
        input_frame,
        text="Enter process details in this format: eg. 0 1 5 6 7 8").grid(
            row=0, column=0, columnspan=2, pady=(0, 10))

    tk.Label(input_frame, text="Type in Arrival Time:").grid(row=1,
                                                             column=0,
                                                             sticky='e')
    arrival_time_entry = tk.Entry(input_frame)
    arrival_time_entry.grid(row=1, column=1)

    tk.Label(input_frame, text="Type in Burst Time:").grid(row=2,
                                                           column=0,
                                                           sticky='e')
    burst_time_entry = tk.Entry(input_frame)
    burst_time_entry.grid(row=2, column=1)

    tk.Label(input_frame, text="Type in Priority:").grid(row=3,
                                                         column=0,
                                                         sticky='e')
    priority_entry = tk.Entry(input_frame)
    priority_entry.grid(row=3, column=1)

    # Buttons for actions
    display_button = tk.Button(input_frame,
                               text="Display",
                               width=19,
                               command=display_input_data)
    display_button.grid(row=4, column=0, pady=(10, 15))

    confirm_button = tk.Button(input_frame,
                               text="Confirm",
                               width=19,
                               command=confirm_input_data)
    confirm_button.grid(row=4, column=1, pady=(10, 15))

    # Table setup for displaying processes
    table_frame = tk.Frame(root)
    table_frame.grid(row=0, column=1, padx=10, pady=10)

    columns = ['Process', 'Arrival Time', 'Burst Time', 'Priority']
    table = ttk.Treeview(table_frame,
                         columns=columns,
                         show='headings',
                         height=6)

    # Define the column headings
    for col in columns:
        table.heading(col, text=col)
        table.column(col, width=150, anchor="center")

    table.grid(row=0, column=0, pady=(0, 15))

    # Start the Tkinter event loop
    root.mainloop()


def selectPSA(process):
    # Create the main window
    root = tk.Tk()
    root.title("Select a Process Scheduling Algorithm")

    # Create and place the label
    label = tk.Label(root, text="Select a Process Scheduling Algorithm:")
    label.grid(row=0, column=0, padx=10, pady=(10, 20))

    # Buttons for the different scheduling algorithms
    button1 = tk.Button(root,
                        text="Round Robin with Quantum 3",
                        width=50,
                        command=lambda: roundRobin(process))
    button1.grid(row=1, column=0, pady=(5, 10))

    button2 = tk.Button(root,
                        text="Shortest Remaining Time",
                        width=50,
                        command=lambda: shortestRemainingTime(process))
    button2.grid(row=2, column=0, pady=(5, 10))

    button3 = tk.Button(root, 
                        text="Shortest Job Next", 
                        width=50, 
                        command=lambda: shortestJobNext(process))
    button3.grid(row=3, column=0, pady=(5, 10))

    button4 = tk.Button(root, 
                    text="Non-Preemptive Priority", 
                    width=50, 
                    command=lambda: nonPreemptivePriority(process))
    button4.grid(row=4, column=0, pady=(5, 10))


    # NOTE: Add the other buttons here!

    # Return to Main Menu button
    return_button = tk.Button(root,
                              text="Return to Main Menu",
                              width=25,
                              bg="red",
                              command=root.quit)
    return_button.grid(row=5, column=0, pady=(10, 15))

    # Start the Tkinter event loop
    root.mainloop()


def roundRobin(process):
    # sorts the processes according to ascending arrival time
    process.sort(key=lambda x: x[1])
    # print("Processes sorted according to Arrival Time: ", process, '\n')

    # used to calculate the sum of all the burst times
    sumBurst = 0
    for i in range(len(process)):
        sumBurst = sumBurst + process[i][2]
    # print("The sum of all the burst times is: ",sumBurst,"\n")

    quantum = 3  # The quantum set by the user
    currentQuantum = 0  # to store how many times the process has executed for (to make sure that the process does not go over
    # allocated quantum time)

    readyQ = []  # ready queue
    ganttTime = [process[0][1]]  # For gantt chart: the timeline
    ganttProcess = [
    ]  # For gantt chart: which processes are currently executing at each time

    timer = process[0][1]
    while timer <= sumBurst:  # while less than sum of the burst times
        for i in range(len(process)):  # loops through all processes
            if process[i][1] == timer:
                # check to see if any processes arrive at that time, if a new process arrives, append it to the readyQ
                readyQ.append(process[i][:])  # <--- pass by value
        timer += 1

        if readyQ:  # if the ready queue is not empty, only then we can start performing process scheduling

            #  (1) if quantum time is done, but the process still has burst time left, add to the end of the queue
            if (currentQuantum == quantum and readyQ[0][2] != 0):
                # remove first element of readyq and put it at the back of the readyq:
                readyQ.append(readyQ[0])
                readyQ.pop(0)
                currentQuantum = 0  # set quantum back to 0 for the new process in readyQ

            # (2) decreasing quantum time, if theres still burst time, and quantum has not been used up,
            # and the process arrival is the same as the timer
            if (currentQuantum < quantum and readyQ[0][2] != 0
                    and readyQ[0][1] <= timer):
                readyQ[0][2] -= 1  # decrement the burst time
                currentQuantum += 1  # increment the quantum
                # To track the process that is executing at this current time:
                ganttProcess.append(readyQ[0][0])
                ganttTime.append(timer)

            # (3) When the process has completed execution (when remaining burst time = 0):
            if (readyQ[0][2] == 0):
                # to append the finishing time to process array:
                for i in range(len(process)):
                    if readyQ[0][0] == process[i][0]:
                        process[i].append(timer)
                readyQ.pop(0)  # get rid of the first item in readyQ
                currentQuantum = 0  # set quantum back to 0 for the new process in readyQ

    # print("\na) ROUND ROBIN:")
    # print('Gantt Processes:', ganttProcess)
    # print('Gantt Time:', ganttTime)
    # print('Process:', process)

    createGanttChart(ganttProcess, ganttTime)
    calculate(
        process
    )  # calculates the turn around time, waiting time, and average TAT and WT


def shortestRemainingTime(process):
    # Sort processes by arrival time
    process.sort(key=lambda x: x[1])

    # Initialize variables
    time = 0
    completed = 0

    n = len(process)                         # Total number of processes
    remainingTime = [p[2] for p in process]  # Remaining burst times for each process
    ganttProcess = []
    ganttTime = []
    shortest = -1
    minRemainingTime = float("inf")          # Used to determine which process has the smaller remaining burst time
    is_idle = True                           # Indicates if the CPU is idle

    # SRT Simulation
    # While function loop until all processes are completed
    while completed != n:

        # Used to find the process with the shortest remaining time that has arrived in the system
        for i in range(n):
            if process[i][1] <= time and remainingTime[i] > 0: # Checks if process has arrived but not completed yet
                # The condition where it checks if:
                # 1. The remaining burst time of the current process IS SMALLER THAN the current mininum remaining burst time
                if remainingTime[i] < minRemainingTime or (
                    # 2. If the remaining burst time of the current process is equal to the current minimum
                        remainingTime[i] == minRemainingTime
                    # 3. Compares the processes' arrival times
                        and process[i][1] < process[shortest][1]):

                    # Selects the process with the shortest remaining time
                    shortest = i
                    minRemainingTime = remainingTime[i]
                    is_idle = False # CPU not idle

        if is_idle:    # If no process is ready to execute
            time += 1  # Increment time to wait for the next process to arrive
            continue   # Skip to the next iteration of the loop

        # Execute the process
        ganttProcess.append(process[shortest][0])       # Adds the process ID to Gantt Chart
        ganttTime.append(time)                          # Adds the current time to Gantt Chart intervals 
        remainingTime[shortest] -= 1                    # Decrease the remaining burst time
        minRemainingTime = remainingTime[shortest]      # Update the minimum remaining time

        if remainingTime[shortest] == 0:                # If the process has finished execution
            completed += 1                              # Increment the completed process count
            finishTime = time + 1                       # Calculate finish time
            process[shortest].append(finishTime)        # Append finish time to the process details
            minRemainingTime = float("inf")             # Resets the minimum remaining time to ensure no finished process is mistakenly
                                                            # selected as the shortest in the next iteration

        time += 1           # Increment the current time 

    ganttTime.append(time)  # Append the final time for the Gantt chart

    createGanttChart(ganttProcess, ganttTime) # Create the gantt chart
    calculate(process)  # Calculate and display TAT and WT

# Algoritjm for SJN
def shortestJobNext(process):
    process.sort(key=lambda x: (x[1], x[2]))  # Sort by arrival time, then burst time
    
    #initialize variables
    time = 0
    completed = 0

    n = len(process)
    ganttProcess = []
    ganttTime = []
    visited = [False] * n #A list to track if a process has been executed

    # Start processing until all processes are completed
    while completed < n:
        #Create a waiting queue of processes that have arrived and are not yet completed
        waiting_queue = [p for p in process if p[1] <= time and not visited[process.index(p)]]
        
        #If there are processes in the waiting queue:
        if waiting_queue:  # Sort the waiting queue by Burst Time (Shortest Job Next)
            waiting_queue.sort(key=lambda x: x[2])  # Sort by shortest burst time
            current_process = waiting_queue.pop(0)
            index = process.index(current_process) # Find the index of the selected process

            # Execute the process
            ganttProcess.append(current_process[0]) # Add the process ID to the Gantt chart
            ganttTime.append(time) # Add the current time to the Gantt chart
            time += current_process[2] # Increment the time by the process's Burst Time
            process[index].append(time) # Append the finish time to the process details
            visited[index] = True # Mark the process as completed
            completed += 1 # Increment the count of completed processes
        else:
            time += 1 #If no process is ready to execute, increment the time

    ganttTime.append(time)
    createGanttChart(ganttProcess, ganttTime)
    calculate(process)

# Algorithm for Non-Preemptive Priority Scheduling
def nonPreemptivePriority(process):
    # Sort processes by arrival time and then by priority
    process.sort(key=lambda x: (x[1], x[3]))

    time = 0
    completed = 0
    n = len(process)
    ganttProcess = []
    ganttTime = []
    visited = [False] * n  # Track if a process is completed

    while completed < n:
        # Find the next process to execute
        available_processes = [p for p in process if p[1] <= time and not visited[process.index(p)]]
        
        if available_processes:
            # Select the process with the highest priority (lowest priority number)
            available_processes.sort(key=lambda x: x[3])
            current_process = available_processes[0]
            index = process.index(current_process)

            # Execute the selected process
            ganttProcess.append(current_process[0])  # Process ID
            ganttTime.append(time)  # Start time
            time += current_process[2]  # Increment time by Burst Time
            process[index].append(time)  # Append finish time to the process
            visited[index] = True  # Mark as completed
            completed += 1  # Increment the completed count
        else:
            time += 1  # If no process is ready, increment time

    ganttTime.append(time)  # Append the final time
    createGanttChart(ganttProcess, ganttTime)  # Generate the Gantt chart
    calculate(process)  # Calculate turnaround and waiting times


def createGanttChart(ganttProcess, ganttTime):
    intervals = []
    start_time = ganttTime[0]
    current_process = ganttProcess[0]

    for i in range(1, len(ganttProcess)):
        if ganttProcess[i] != current_process:
            intervals.append((current_process, start_time, ganttTime[i]))
            start_time = ganttTime[i]
            current_process = ganttProcess[i]
    intervals.append((current_process, start_time, ganttTime[-1]))  # Add the last interval

    # Create the Gantt chart plot
    fig, ax = plt.subplots(figsize=(8, 1.5))  # Set a narrower height
    ax.set_xticks(ganttTime)  # Set x-axis ticks to display all time points
    ax.set_xlim(ganttTime[0], ganttTime[-1])  # Adjust x-axis limits
    ax.set_yticks([])  # Remove y-axis labels
    ax.grid(False)  # Disable gridlines
    ax.set_title("Gantt Chart", fontsize=12)
    ax.set_xlabel("Time", fontsize=10)

    # Plot each process as a bar
    for process, start, end in intervals:
        ax.broken_barh([(start, end - start)], (0, 10), facecolors='green', edgecolor="black")
        ax.text((start + end) / 2, 5, process, ha='center', va='center', color='white', fontsize=8)

    # Show the Gantt chart
    plt.tight_layout()
    plt.show()


def calculate(process):
    TAT = 0  # Turn Around Time
    WT = 0  # Waiting Time
    # (1) calculates the TAT and WT of each process
    for i in range(len(process)):
        TAT = process[i][4] - process[i][1]  # TAT = Finish Time - Arrival Time
        process[i].append(TAT)
        WT = process[i][5] - process[i][
            2]  # Waiting Time = Turn Around Time - Burst Time
        process[i].append(WT)

    # (2) displays the processes with the appended TAT and WT
    # print("\nTable with Calculation: ", process)

    numProcess = len(process)  # counts the number of processes
    sumTAT = 0  # stores sum of Turn Around Time
    sumWT = 0  # store sum of Waiting Time
    # (3) calculates the total TAT & WT for the entire processes
    for i in range(len(process)):
        sumTAT = sumTAT + process[i][5]
        sumWT = sumWT + process[i][6]

    # (4) displays the average TAT and WT
    # print("Average Turnaround Time = ", sumTAT, "/", numProcess, "=", round(sumTAT / numProcess, 1))
    # print("Average Waiting Time = ", sumWT, "/", numProcess, "=", round(sumWT / numProcess, 1))

    avgTAT = round(sumTAT / numProcess, 1)
    avgWT = round(sumWT / numProcess, 1)

    tableWithCalculation(process, sumTAT, sumWT, avgTAT, avgWT)


def tableWithCalculation(process, sumTAT, sumWT, avgTAT, avgWT):
    # Create the main window
    root = tk.Tk()
    root.title("Table with Calculations")

    # Define the heading
    heading = [
        'Process', 'Arrival Time', 'Burst Time', 'Priority', 'Finish Time',
        'Turnaround Time', 'Waiting Time'
    ]

    # Create a treeview to display the table data
    tree = ttk.Treeview(root, columns=heading, show="headings")

    # Define the column headings
    for col in heading:
        tree.heading(col, text=col)
        tree.column(col, width=100, anchor="center")

    # Insert rows into the treeview
    for proc in process:
        tree.insert("", "end", values=proc)

    # Create a scroll bar for the treeview
    scroll = tk.Scrollbar(root, orient="vertical", command=tree.yview)
    tree.configure(yscrollcommand=scroll.set)
    scroll.grid(row=0, column=0, sticky='ns')
    tree.grid(row=0, column=1, pady=(10, 20))

    # Display calculation results
    result_frame = tk.Frame(root)
    result_frame.grid(row=1, column=0, columnspan=2, pady=(10, 15))

    tk.Label(result_frame,
             text=f"Total Turn Around Time = {sumTAT}").grid(row=0,
                                                             column=0,
                                                             padx=10,
                                                             sticky="w")
    tk.Label(result_frame,
             text=f"Average Turn Around Time = {avgTAT}").grid(row=1,
                                                               column=0,
                                                               padx=10,
                                                               sticky="w")
    tk.Label(result_frame,
             text=f"Total Waiting Time = {sumWT}").grid(row=2,
                                                        column=0,
                                                        padx=10,
                                                        sticky="w")
    tk.Label(result_frame,
             text=f"Average Waiting Time = {avgWT}").grid(row=3,
                                                          column=0,
                                                          padx=10,
                                                          sticky="w")

    # Go Back button
    go_back_button = tk.Button(root,
                               text="Go Back",
                               width=30,
                               command=lambda: goBack(process, root))
    go_back_button.grid(row=2, column=0, columnspan=2, pady=(20, 10))

    # Start the Tkinter event loop
    root.mainloop()


def goBack(process, root):
    # Reset process data by removing calculated columns
    for i in range(len(process)):
        del process[i][4]  # Finish Time
        del process[i][5]  # Turnaround Time
        del process[i][-1]  # Waiting Time

    # Close the current window
    root.quit()
    # Call selectPSA or any other function for the next steps
    selectPSA(process)


mainMenu()
