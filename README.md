# **CPU Scheduler**
---
### Supported Schedulers:

```
* First Come First Served (FCFS)
* Shortest Job First – Non Preemptive (SJF – NP)
* Shortest Job First –Preemptive (SJF – P)
* Priority – Non Preemptive
* Priority – Preemptive
* Round Robin (RR)
```
---
### Features:

1. Floating Point Support
2. Support Idle :
    If there’s no arrived processes, then the CPU is idle until a process arrives
3. Input Validation :
    There’s inputs are required for some schedulers you cannot add a process without filling it with valid data
4. Generating Dummy Data : 
    Supported a button to Generate dummy data for the sake of Testing, Every time you press this button it’ll generate another random case
5. Table Editable Cells
    If you added a wrong input you don’t need to delete it and add it again, You can just
    double click on the cell you want to edit and enter the new value and press ENTER on
    the keyboard or press ESC to discard changes.
6. Processes Addition Keyboard shortcut**
    You can add a process using Add button on GUI or by pressing ENTER key on
    keyboard.
    **Note:** All inputs should be filled with valid data or You’ll see an error (As mentioned
    in Input Validation feature).
7. Processes Deletion & Bulk Delete Keyboard shortcut**
    Single Process Deletion: You can just select the process from the table (Single Left
    Click) and Press DELETE button on keyboard or The delete button on GUI.
    Bulk Delete: You can select the processes you want to delete from the table using
    CTRL and select all the processes you want to delete and press DELETE button on
    Keyboard or the delete on GUI.
    Feel free to use SHIFT and arrow keys to delete multiple processes.
8. Showing all processes Starting, Waiting & Departure time**
    All Processes Starting will be shown in the processes table after you (Calculate &
    Draw the processes)
**NOTE:** If you didn’t press (Calculate & Draw) all processes Starting, Waiting &
Departure times will be initialized with - 1
9. Showing average waiting time & average turnaround time**
    When you press Calculate & Draw you’ll get a new window having the Gantt chart,
    Average waiting time & Average turnaround time.
10. Only required data the user will be asked for
11. User-friendly GUI

---

### How to Run?

```
Choose the scheduling Algorithm from the top left list, Add Processes by filling the
Text fields (Process ID, Arrival time, Burst time, etc...)
Press Calculate & Draw and another window will pop up containing the Gantt chart,
Average waiting time & Average turnaround time.
```
