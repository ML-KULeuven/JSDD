# JSDD
Java wrapper for the sdd library version 2.0.

This wrapper provides access to the sdd library through Java or Scala.

# Dependencies and System Requirements
sdd 2.0 library: Download from http://reasoning.cs.ucla.edu/sdd/

The sdd library works on linux and OS X. The java wrapper is only tested on linux, but should also work on OS X.


# Quickstart
1. From the sdd package, copy the sdd library (libsdd.so) to lib/
2. Make sure that the c libraries can be found by adding lib/ to the ld library path
    ```
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:lib/
    ```

## Run examples    
3. Run an example of your choice (1-7) by executing:
    ```
    make example1
    ...
    make example7
    ```

## Compile from source
3. From the sdd package, copy the header of the sdd library (sddapi.h) to include/

4. Clean first so that the the binaries and jars will be recompiled
    ```
    make clean
    ```
5. Compile
    ```
    make
    ```

## Run unit tests
3. Run the tests
    ```
    make test
    ```

# Use JSDD in your own code
1. Make sure that the c libraries (libsdd.so, libsdd-2_wrap.so) can be found by adding lib/ to the ld library path
2. Add the JSDD.jar to the java classpath
3. Start coding!

# API
You can find the documentation of JSDD in the directory ./doc/index.html.
The main classes are in the package sdd:
- SddManager
- Sdd
- Vtree
- WmcManager

# Garbarge Collection
Although Java has built-in garbage collection, garbage collection of SDDs still needs to be done manually. It was decided not to use the built-in garbage collection because it is lazy and does not collect objects before they take up too much memory. But Java does not take the memory of the underlying C program into account, therefore it will probably wait to long to do garbage collaction and is thus not optimal for SDDs.

The techniques for garbage collection remain the same as in the C library. You have to refer/derefer SDDs and you can use automatic or manual garbage
collection.

For debugging purpose, when a Java object of the class Sdd is finalized, it checks whether that object has been dereferenced as many times as it has been referenced. If this is not the case, a warning is printed to inform the user that there might be a memory leak.

# Examples
Some examples can be found in the ./examples/ directory. These examples are the same as the ones packaged with the C library. There is one extra example about using the WmcManager. The examples use the ./input/ and ./output/ folders for their input and output.


# Contact
If you have further questions, do not hesitate to contact Jessa Bekker (https://people.cs.kuleuven.be/~jessa.bekker/)

# Cite
Please cite the original paper that this wrapper was developed for, when you use it:

```
@inproceedings{bekker2015tractable,
  title={Tractable learning for complex probability queries},
  author={Bekker, Jessa and Davis, Jesse and Choi, Arthur and Darwiche, Adnan and Van den Broeck, Guy},
  booktitle={Advances in Neural Information Processing Systems},
  pages={2242--2250},
  year={2015}
} 
```
