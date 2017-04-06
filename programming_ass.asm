;Kevin Tan
;Programming assignment NASM

section .data
	number db 5
	answer db 1 ;1 means prime, 0 means not prime

section .bss
;unitinitalized data being used
	
section .text
        global _start

_start:
	mov esi, number ;get the offset of number into esi
	

keith:	
	mov edi, answer
	mov eax,0 ;clear eax register
	mov al,[esi] ;get the number from memory into al
	mov dl,al ;put it inside dl as well 		
	mov bl, 2 ;bl holds each divisor starting from 2
	
loopy:	
	cmp bl,al ;condition divisor < number
	jge .end
	div bl ;eax/ebx with quot in eax and rem in ah
	and ax,1111111100000000b ;isolate the rem in ah with a AND mask to determine whether the 						
				 ;remainder is 0, AX = 16 bits, AX = AL(8bits)|AH(8bits)
	cmp ah,0 ;the remainder of number / divisor is 0
	je .condition ;if not prime
	
	jmp .inc

.condition:
	mov edi,0
	mov [answer],edi ;not prime

.inc:
	inc bl
	mov eax,0 ;clear eax register
	mov al,[esi] ;get the number from memory into al
	jmp loopy

.end:	
        mov eax,1            ; The system call for exit (sys_exit)
        mov ebx,0            ; Exit with return code of 0 (no error)
	int 80h
