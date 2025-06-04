              ;       Izza Nelly binti Mohd Nasir (1211111583)
              ;       Ain Nur Yasmin binti Muhd Zaini (1211109582)
              ;       Mohammad Hazman bin Khairil Anwar (1211110444)
              ;       Abdul Adzeem Abdul Rasyid (1211109773)
              ;       Muhammad Nabil Naufal bin Md Zaid (1221101160)

              mov     r0,#0x11000000
              mov     r1,#0x00110000
              mov     r2,#0x00001100
              mov     r3,#0x00000011
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              mov     r8,#0x2000
              str     r0, [r8]

              ;       move r0,#0x22223333
              mov     r0,#0x22000000
              mov     r1,#0x00220000
              mov     r2,#0x00003300
              mov     r3,#0x00000033
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#4]

              ;       move r0,#0x31111111
              mov     r0,#0x31000000
              mov     r1,#0x00110000
              mov     r2,#0x00001100
              mov     r3,#0x00000011
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#8]

              ;       move r0,#0x42223333
              mov     r0,#0x42000000
              mov     r1,#0x00220000
              mov     r2,#0x00003300
              mov     r3,#0x00000033
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#12]

              ;       move r0,#0x51111111
              mov     r0,#0x51000000
              mov     r1,#0x00110000
              mov     r2,#0x00001100
              mov     r3,#0x00000011
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#8]
              str     r0, [r8,#16]

              ;       move r0,#0x62223333
              mov     r0,#0x62000000
              mov     r1,#0x00220000
              mov     r2,#0x00003300
              mov     r3,#0x00000033
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#12]
              str     r0, [r8,#20]

              ;       move r0,#0x71111111
              mov     r0,#0x71000000
              mov     r1,#0x00110000
              mov     r2,#0x00001100
              mov     r3,#0x00000011
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#24]

              ;       move r0,#0x82223333
              mov     r0,#0x82000000
              mov     r1,#0x00220000
              mov     r2,#0x00003300
              mov     r3,#0x00000033
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#28]

              ;       move r0,#0x91111111
              mov     r0,#0x91000000
              mov     r1,#0x00110000
              mov     r2,#0x00001100
              mov     r3,#0x00000011
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#32]

              ;       move r0,#0xA2223333
              mov     r0,#0xA2000000
              mov     r1,#0x00220000
              mov     r2,#0x00003300
              mov     r3,#0x00000033
              add     r0, r0, r1
              add     r0, r0, r2
              add     r0, r0, r3
              str     r0, [r8,#36]


ENTRY         

start_sort    
              mov     r9, #10 ; Number of elements
              ldr     r8, =0x2000 ; Base address of data array

bubble_sort   
              mov     r4, #0 ; loop_outer index i
              mov     r5, r9 ; r5 will keep track of n-i-1 for loop_inner

loop_outer    
              cmp     r4, r9 ; COMPARE loop_outer index with number of elements
              beq     transfer_data ; IF all elements have been processed, call transfer_data

              mov     r6, #0 ; loop_inner index j
              mov     r7, r8 ; Pointer to first element

loop_inner    
              ldr     r0,[r7] ; LOAD current element
              ldr     r1, [r7, #4] ; LOAD next element
              cmp     r0, r1 ; COMPARE current element with next element
              ble     no_swap ; IF in order, CALL no_swap

              ;       Swap elements
              str     r1, [r7]
              str     r0, [r7, #4]

no_swap       
              add     r7, r7, #4 ; Move to next element
              add     r6,r6, #1 ; INCREMENT loop_inner index
              cmp     r6,r5 ; COMPARE loop_inner index with n-i-1
              blt     loop_inner ; IF j < n-i-1, CONTINUE loop_inner

              add     r4, r4, #1 ; INCREMENT loop_outer index
              sub     r5, r5, #1 ; DECREMENT comparison count for next pass
              b       loop_outer ; REPEAT loop_outer

transfer_data 
              ldr     r10, =0x2100 ; Base address for storing sorted data
              mov     r6,#0 ; RESET loop_inner index for transfer loop
              mov     r7,r8 ; Source base address (original sorted array)

copy_loop     
              cmp     r6,r9 ; COMPARE loop_inner index with number of elements
              beq     done ; IF all elements are copied, FINISH
              ldr     r0, [r7], #4 ; LOAD from source and post-increment address
              str     r0,[r10], #4 ; STORE to destination and post-increment address
              add     r6, r6, #1 ; INCREMENT loop_inner index
              b       copy_loop ; REPEAT copy_loop

done          