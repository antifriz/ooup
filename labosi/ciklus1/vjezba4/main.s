	.file	"main.cpp"
	.intel_syntax noprefix
	.section	.text._ZN9CoolClass3setEi,"axG",@progbits,_ZN9CoolClass3setEi,comdat
	.align 2
	.weak	_ZN9CoolClass3setEi
	.type	_ZN9CoolClass3setEi, @function
_ZN9CoolClass3setEi:
.LFB0:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR [rbp-8], rdi
	mov	DWORD PTR [rbp-12], esi
	mov	rax, QWORD PTR [rbp-8]
	mov	edx, DWORD PTR [rbp-12]
	mov	DWORD PTR [rax+8], edx
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	_ZN9CoolClass3setEi, .-_ZN9CoolClass3setEi
	.section	.text._ZN9CoolClass3getEv,"axG",@progbits,_ZN9CoolClass3getEv,comdat
	.align 2
	.weak	_ZN9CoolClass3getEv
	.type	_ZN9CoolClass3getEv, @function
_ZN9CoolClass3getEv:
.LFB1:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR [rbp-8], rdi
	mov	rax, QWORD PTR [rbp-8]
	mov	eax, DWORD PTR [rax+8]
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	_ZN9CoolClass3getEv, .-_ZN9CoolClass3getEv
	.section	.text._ZN13PlainOldClass3setEi,"axG",@progbits,_ZN13PlainOldClass3setEi,comdat
	.align 2
	.weak	_ZN13PlainOldClass3setEi
	.type	_ZN13PlainOldClass3setEi, @function
_ZN13PlainOldClass3setEi:
.LFB2:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR [rbp-8], rdi
	mov	DWORD PTR [rbp-12], esi
	mov	rax, QWORD PTR [rbp-8]
	mov	edx, DWORD PTR [rbp-12]
	mov	DWORD PTR [rax], edx
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE2:
	.size	_ZN13PlainOldClass3setEi, .-_ZN13PlainOldClass3setEi
	.section	.text._ZN4BaseC2Ev,"axG",@progbits,_ZN4BaseC5Ev,comdat
	.align 2
	.weak	_ZN4BaseC2Ev
	.type	_ZN4BaseC2Ev, @function
_ZN4BaseC2Ev:
.LFB7:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	mov	QWORD PTR [rbp-8], rdi
	mov	rax, QWORD PTR [rbp-8]
	mov	QWORD PTR [rax], OFFSET FLAT:_ZTV4Base+16
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE7:
	.size	_ZN4BaseC2Ev, .-_ZN4BaseC2Ev
	.weak	_ZN4BaseC1Ev
	.set	_ZN4BaseC1Ev,_ZN4BaseC2Ev
	.section	.text._ZN9CoolClassC2Ev,"axG",@progbits,_ZN9CoolClassC5Ev,comdat
	.align 2
	.weak	_ZN9CoolClassC2Ev
	.type	_ZN9CoolClassC2Ev, @function
_ZN9CoolClassC2Ev:
.LFB9:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR [rbp-8], rdi
	mov	rax, QWORD PTR [rbp-8]
	mov	rdi, rax
	call	_ZN4BaseC2Ev
	mov	rax, QWORD PTR [rbp-8]
	mov	QWORD PTR [rax], OFFSET FLAT:_ZTV9CoolClass+16
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE9:
	.size	_ZN9CoolClassC2Ev, .-_ZN9CoolClassC2Ev
	.weak	_ZN9CoolClassC1Ev
	.set	_ZN9CoolClassC1Ev,_ZN9CoolClassC2Ev
	.text
	.globl	main
	.type	main, @function
main:
.LFB4:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	push	rbx
	sub	rsp, 24
	.cfi_offset 3, -24
	mov	edi, 4
	call	_Znwm
	mov	QWORD PTR [rbp-32], rax
	mov	edi, 16
	call	_Znwm
	mov	rbx, rax
	mov	rdi, rbx
	call	_ZN9CoolClassC1Ev
	mov	QWORD PTR [rbp-24], rbx
	mov	rax, QWORD PTR [rbp-32]
	mov	esi, 42
	mov	rdi, rax
	call	_ZN13PlainOldClass3setEi
	mov	rax, QWORD PTR [rbp-24]
	mov	rax, QWORD PTR [rax]
	mov	rax, QWORD PTR [rax]
	mov	rdx, QWORD PTR [rbp-24]
	mov	esi, 42
	mov	rdi, rdx
	call	rax
	mov	eax, 0
	add	rsp, 24
	pop	rbx
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE4:
	.size	main, .-main
	.weak	_ZTV9CoolClass
	.section	.rodata._ZTV9CoolClass,"aG",@progbits,_ZTV9CoolClass,comdat
	.align 32
	.type	_ZTV9CoolClass, @object
	.size	_ZTV9CoolClass, 32
_ZTV9CoolClass:
	.quad	0
	.quad	_ZTI9CoolClass
	.quad	_ZN9CoolClass3setEi
	.quad	_ZN9CoolClass3getEv
	.weak	_ZTV4Base
	.section	.rodata._ZTV4Base,"aG",@progbits,_ZTV4Base,comdat
	.align 32
	.type	_ZTV4Base, @object
	.size	_ZTV4Base, 32
_ZTV4Base:
	.quad	0
	.quad	_ZTI4Base
	.quad	__cxa_pure_virtual
	.quad	__cxa_pure_virtual
	.weak	_ZTS9CoolClass
	.section	.rodata._ZTS9CoolClass,"aG",@progbits,_ZTS9CoolClass,comdat
	.type	_ZTS9CoolClass, @object
	.size	_ZTS9CoolClass, 11
_ZTS9CoolClass:
	.string	"9CoolClass"
	.weak	_ZTI9CoolClass
	.section	.rodata._ZTI9CoolClass,"aG",@progbits,_ZTI9CoolClass,comdat
	.align 16
	.type	_ZTI9CoolClass, @object
	.size	_ZTI9CoolClass, 24
_ZTI9CoolClass:
	.quad	_ZTVN10__cxxabiv120__si_class_type_infoE+16
	.quad	_ZTS9CoolClass
	.quad	_ZTI4Base
	.weak	_ZTS4Base
	.section	.rodata._ZTS4Base,"aG",@progbits,_ZTS4Base,comdat
	.type	_ZTS4Base, @object
	.size	_ZTS4Base, 6
_ZTS4Base:
	.string	"4Base"
	.weak	_ZTI4Base
	.section	.rodata._ZTI4Base,"aG",@progbits,_ZTI4Base,comdat
	.align 16
	.type	_ZTI4Base, @object
	.size	_ZTI4Base, 16
_ZTI4Base:
	.quad	_ZTVN10__cxxabiv117__class_type_infoE+16
	.quad	_ZTS4Base
	.ident	"GCC: (Ubuntu 4.8.2-19ubuntu1) 4.8.2"
	.section	.note.GNU-stack,"",@progbits
