	.file	"proba.cpp"
	.section	.text._ZN5probaC2Ev,"axG",@progbits,_ZN5probaC5Ev,comdat
	.align 2
	.weak	_ZN5probaC2Ev
	.type	_ZN5probaC2Ev, @function
_ZN5probaC2Ev:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -8(%rbp)
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	_ZN5probaC2Ev, .-_ZN5probaC2Ev
	.weak	_ZN5probaC1Ev
	.set	_ZN5probaC1Ev,_ZN5probaC2Ev
	.section	.text._ZN5proba3jejEv,"axG",@progbits,_ZN5proba3jejEv,comdat
	.align 2
	.weak	_ZN5proba3jejEv
	.type	_ZN5proba3jejEv, @function
_ZN5proba3jejEv:
.LFB3:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -24(%rbp)
	movl	$0, -4(%rbp)
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE3:
	.size	_ZN5proba3jejEv, .-_ZN5proba3jejEv
	.text
	.globl	main
	.type	main, @function
main:
.LFB4:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%rbx
	subq	$24, %rsp
	.cfi_offset 3, -24
	movl	$1, %edi
	call	_Znwm
	movq	%rax, %rbx
	movq	%rbx, %rdi
	call	_ZN5probaC1Ev
	movq	%rbx, -24(%rbp)
	movq	-24(%rbp), %rax
	movq	%rax, %rdi
	call	_ZN5proba3jejEv
	movl	$0, %eax
	addq	$24, %rsp
	popq	%rbx
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE4:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 4.8.2-19ubuntu1) 4.8.2"
	.section	.note.GNU-stack,"",@progbits
