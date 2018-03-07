# JSDD
Java wrapper for the sdd library version 2.0.

This wrapper provides access to the sdd library through Java or Scala.

# Requirements
sdd 2.0 library: Download from http://reasoning.cs.ucla.edu/sdd/
The sdd library works on linux and osx. The java wrapper is only tested on linux, but should also work on osx.


# Quickstart
1. From the sdd package, copy the sdd library (libsdd.so) to lib/
2. From the sdd package, copy the header of the sdd library (sddapi.h) to include/
3. Make sure that the c libraries can be found by adding lib/ to the ld library path
    ```
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:lib/

## Run examples    
Run an example of your choice (1-7) by executing:
```
make example1
...
```
make example7

## Compile from source
Clean first so that the the binaries and jars will be recompiled
```
make clean
```
make
    
## Run unit tests
```
make test
    
## Use JSDD in your own code
1. Make sure that the c libraries (libsdd.so, libsdd-2_wrap.so) can be found by adding lib/ to the ld library path
2. Add the JSDD.jar to the java classpath
3. Start coding!

# API
The API is available in doc/