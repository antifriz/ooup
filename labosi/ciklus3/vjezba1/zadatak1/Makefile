all: tester parrot.so tiger.so

tester: main.c myfactory.c
	gcc main.c myfactory.c -o $@ -ldl

parrot.so: parrot.c
	gcc -shared -fPIC $< -o $@
	
tiger.so: tiger.c
	gcc -shared -fPIC $< -o $@	

clean:
	-rm -rf *.o *.so tester

test: all
	./tester
