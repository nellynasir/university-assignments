# CPUScheduling

This project implements various CPU scheduling algorithms commonly taught in Operating Systems courses.  
It's a console-based simulation written in C++.

## ⏱️ Algorithms Implemented
- **First-Come, First-Served (FCFS)**
- **Shortest Job First (SJF)**
- **Round Robin (RR)**

Each algorithm calculates:
- Waiting Time
- Turnaround Time
- Gantt Chart-style output (text)

## 💻 How to Run
1. Compile with any C++ compiler:
   ```bash
   g++ CPUScheduling.cpp -o CPUScheduling
   ./CPUScheduling
