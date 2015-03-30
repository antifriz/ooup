	.file	"main.cpp"
	.intel_syntax noprefix
	.section	.text._ZN4BaseC2Ev,"axG",@progbits,_ZN4BaseC5Ev,comdat
	.align 2
	.weak	_ZN4BaseC2Ev
	.type	_ZN4BaseC2Ev, @function
_ZN4BaseC2Ev:
.LFB1:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR [rbp-8], rdi
	mov	rax, QWORD PTR [rbp-8]
	mov	QWORD PTR [rax], OFFSET FLAT:_ZTV4Base+16
	mov	rax, QWORD PTR [rbp-8]
	mov	rdi, rax
	call	_ZN4Base6metodaEv
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE1:
	.size	_ZN4BaseC2Ev, .-_ZN4BaseC2Ev
	.weak	_ZN4BaseC1Ev
	.set	_ZN4BaseC1Ev,_ZN4BaseC2Ev
	.section	.rodata
.LC0:
	.string	"ja sam bazna implementacija!"
	.section	.text._ZN4Base15virtualnaMetodaEv,"axG",@progbits,_ZN4Base15virtualnaMetodaEv,comdat
	.align 2
	.weak	_ZN4Base15virtualnaMetodaEv
	.type	_ZN4Base15virtualnaMetodaEv, @function
_ZN4Base15virtualnaMetodaEv:
.LFB3:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR [rbp-8], rdi
	mov	edi, OFFSET FLAT:.LC0
	call	puts
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE3:
	.size	_ZN4Base15virtualnaMetodaEv, .-_ZN4Base15virtualnaMetodaEv
	.section	.rodata
.LC1:
	.string	"Metoda kaze: "
	.section	.text._ZN4Base6metodaEv,"axG",@progbits,_ZN4Base6metodaEv,comdat
	.align 2
	.weak	_ZN4Base6metodaEv
	.type	_ZN4Base6metodaEv, @function
_ZN4Base6metodaEv:
.LFB4:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR [rbp-8], rdi
	mov	edi, OFFSET FLAT:.LC1
	mov	eax, 0
	call	printf
	mov	rax, QWORD PTR [rbp-8]
	mov	rax, QWORD PTR [rax]
	mov	rax, QWORD PTR [rax]
	mov	rdx, QWORD PTR [rbp-8]
	mov	rdi, rdx
	call	rax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE4:
	.size	_ZN4Base6metodaEv, .-_ZN4Base6metodaEv
	.section	.text._ZN7DerivedC2Ev,"axG",@progbits,_ZN7DerivedC5Ev,comdat
	.align 2
	.weak	_ZN7DerivedC2Ev
	.type	_ZN7DerivedC2Ev, @function
_ZN7DerivedC2Ev:
.LFB6:
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
	mov	QWORD PTR [rax], OFFSET FLAT:_ZTV7Derived+16
	mov	rax, QWORD PTR [rbp-8]
	mov	rdi, rax
	call	_ZN4Base6metodaEv
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE6:
	.size	_ZN7DerivedC2Ev, .-_ZN7DerivedC2Ev
	.weak	_ZN7DerivedC1Ev
	.set	_ZN7DerivedC1Ev,_ZN7DerivedC2Ev
	.section	.rodata
	.align 8
.LC2:
	.string	"ja sam izvedena implementacija!"
	.section	.text._ZN7Derived15virtualnaMetodaEv,"axG",@progbits,_ZN7Derived15virtualnaMetodaEv,comdat
	.align 2
	.weak	_ZN7Derived15virtualnaMetodaEv
	.type	_ZN7Derived15virtualnaMetodaEv, @function
_ZN7Derived15virtualnaMetodaEv:
.LFB8:
	.cfi_startproc
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	sub	rsp, 16
	mov	QWORD PTR [rbp-8], rdi
	mov	edi, OFFSET FLAT:.LC2
	call	puts
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE8:
	.size	_ZN7Derived15virtualnaMetodaEv, .-_ZN7Derived15virtualnaMetodaEv
	.text
	.globl	main
	.type	main, @function
main:
.LFB9:
	.cfi_startproc
	.cfi_personality 0x3,__gxx_personality_v0
	.cfi_lsda 0x3,.LLSDA9
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	mov	rbp, rsp
	.cfi_def_cfa_register 6
	push	r12
	push	rbx
	sub	rsp, 16
	.cfi_offset 12, -24
	.cfi_offset 3, -32
	mov	edi, 8
.LEHB0:
	call	_Znwm
.LEHE0:
	mov	rbx, rax
	mov	rdi, rbx
.LEHB1:
	call	_ZN7DerivedC1Ev
.LEHE1:
	mov	QWORD PTR [rbp-24], rbx
	mov	rax, QWORD PTR [rbp-24]
	mov	rdi, rax
.LEHB2:
	call	_ZN4Base6metodaEv
	mov	eax, 0
	jmp	.L10
.L9:
	mov	r12, rax
	mov	rdi, rbx
	call	_ZdlPv
	mov	rax, r12
	mov	rdi, rax
	call	_Unwind_Resume
.LEHE2:
.L10:
	add	rsp, 16
	pop	rbx
	pop	r12
	pop	rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE9:
	.globl	__gxx_personality_v0
	.section	.gcc_except_table,"a",@progbits
.LLSDA9:
	.byte	0xff
	.byte	0xff
	.byte	0x1
	.uleb128 .LLSDACSE9-.LLSDACSB9
.LLSDACSB9:
	.uleb128 .LEHB0-.LFB9
	.uleb128 .LEHE0-.LEHB0
	.uleb128 0
	.uleb128 0
	.uleb128 .LEHB1-.LFB9
	.uleb128 .LEHE1-.LEHB1
	.uleb128 .L9-.LFB9
	.uleb128 0
	.uleb128 .LEHB2-.LFB9
	.uleb128 .LEHE2-.LEHB2
	.uleb128 0
	.uleb128 0
.LLSDACSE9:
	.text
	.size	main, .-main
	.weak	_ZTV7Derived
	.section	.rodata._ZTV7Derived,"aG",@progbits,_ZTV7Derived,comdat
	.align 16
	.type	_ZTV7Derived, @object
	.size	_ZTV7Derived, 24
_ZTV7Derived:
	.quad	0
	.quad	_ZTI7Derived
	.quad	_ZN7Derived15virtualnaMetodaEv
	.weak	_ZTV4Base
	.section	.rodata._ZTV4Base,"aG",@progbits,_ZTV4Base,comdat
	.align 16
	.type	_ZTV4Base, @object
	.size	_ZTV4Base, 24
_ZTV4Base:
	.quad	0
	.quad	_ZTI4Base
	.quad	_ZN4Base15virtualnaMetodaEv
	.weak	_ZTS7Derived
	.section	.rodata._ZTS7Derived,"aG",@progbits,_ZTS7Derived,comdat
	.type	_ZTS7Derived, @object
	.size	_ZTS7Derived, 9
_ZTS7Derived:
	.string	"7Derived"
	.weak	_ZTI7Derived
	.section	.rodata._ZTI7Derived,"aG",@progbits,_ZTI7Derived,comdat
	.align 16
	.type	_ZTI7Derived, @object
	.size	_ZTI7Derived, 24
_ZTI7Derived:
	.quad	_ZTVN10__cxxabiv120__si_class_type_infoE+16
	.quad	_ZTS7Derived
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
