# RSA
This is a Java implementation of the RSA Asymmetric Encryption algorithm.

## Requirements 

```bash
java --version
openjdk 11.0.6 2020-01-14
```

## Compile and run 

```bash
javac RSAGenKey
java RSAGenKey Data/Key.dat

javac RSAEncrypt
java RSAEncrypt [file_to_encrypt] [destination_file] [key_data_file]

javac RSADecrypt
java RSADecrypt [file_to_decrypt] [destination_file] [key_data_file]
```

## Note

- The bit length of the prime numbers used are set to be 514 (the length of 2^513) thus the application can only encrypt txt files that are less than or equal to 128 bytes.
