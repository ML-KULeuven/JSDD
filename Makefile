

#############################################
## Change the following paths if necessary ##
#############################################
# To locate the correct paths using you can use the following commands:
#
# $ locate <filename>
# where <filename> is the complete name of the file
# 
# $ find / -name <filename>
# where <filename> is the name of the file which can include wildcards, e.g junit*.jar can be used to look for different versions of junit.


LINUX=linux
OSX=darwin
OS=$(LINUX)

SDD_DIR = lib/ # This is the directory containing libsdd.so (or libsdd.dylib for osx)

JUNIT_PATH = /usr/share/java/junit4.jar
HAMCREST_PATH = /usr/share/java/hamcrest-core.jar
JNI_DIR = $(JAVA_HOME)/include# This is the directory containing jni.h
JNI_MD_DIR = $(JNI_DIR)/$(OS) # This is the directory containing jni_md.h

ifeq ($(OS),$(LINUX))
LIB = lib/libsdd_wrap.so
else
ifeq ($(OS),$(OSX))
LIB = lib/libsdd_wrap.dylib
else
LIB = fail
endif
endif

JAVA = java -Djava.library.path="lib" -cp .:lib/*
#############################################


default: lib/JSDD.jar

test:	bin/test/java/*class
	java -Djava.library.path="lib" -cp $(JUNIT_PATH):$(HAMCREST_PATH):lib/JSDD.jar:bin/test/java/ org.junit.runner.JUnitCore AllTests
	
example1: examples/*.class
	$(JAVA) examples.Test1

example2: examples/*.class
	$(JAVA) examples.Test2

example3: examples/*.class
	$(JAVA) examples.Test3

example4: examples/*.class
	$(JAVA) examples.Test4

example5: examples/*.class
	$(JAVA) examples.Circuit

example6: examples/*.class
	$(JAVA) examples.CircuitGC

example7: examples/*.class
	$(JAVA) examples.WMC

clean:
	rm -f -r bin
	rm -f examples/*.class
	rm -f -r output


bin:
	mkdir -p bin/main/java
	mkdir -p bin/main/c
	mkdir -p bin/test/java

bin/main/c/sdd-2_wrap.o: bin
	gcc -c -fpic -o bin/main/c/sdd-2_wrap.o -std=c99 src/main/c/sdd-2_wrap.c -Iinclude -I$(JNI_DIR) -I$(JNI_MD_DIR) 

lib/libsdd_wrap.so: bin/main/c/sdd-2_wrap.o
	ld -shared  -o lib/libsdd_wrap.so bin/main/c/sdd-2_wrap.o -L$(SDD_DIR) -lsdd -lm -XLinker 

lib/libsdd_wrap.dylib: bin/main/c/sdd-2_wrap.o
	g++ -dynamiclib bin/main/c/sdd-2_wrap.o -Llib/  -lsdd -lm -o lib/libsdd_wrap.dylib

lib/JSDD.jar: $(LIB)
	javac src/main/java/sdd/*.java src/main/java/jni/*.java src/main/java/helpers/*.java -d bin/main/java/
	jar cf lib/JSDD.jar -C bin/main/java sdd -C bin/main/java jni -C bin/main/java helpers

bin/test/java/*class: lib/JSDD.jar
	javac -cp $(JUNIT_PATH):$(HAMCREST_PATH):lib/JSDD.jar src/test/java/*.java -d bin/test/java/
	

examples/*.class: lib/JSDD.jar
	javac -cp .:lib/JSDD.jar examples/*.java


