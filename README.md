# Cryptography-Course
Implementation of algorithms that I learned through cryptography course in the college

## Books
* Cryptography and Network Security Principles and Practice, 5th Edition (William Stallings)
* Intro to Java Programming, Comprehensive Version ,10th Edition (Y. Daniel Liang)

## Libraries
* JAMA : A Java Matrix Package (https://math.nist.gov/javanumerics/jama/)

## Implemented Techniques
### Caesar Cipher <br />
Encryption - Decryption - Brute force attack
### Affine Caesar cipher <br />
Encryption - Decryption - Brute force attack
### One-Time Pad Cipher <br />
Encryption - Decryption 
### Vigenere Cipher <br />
Encryption - Decryption 
### Playfair Cipher <br />
Encryption - Decryption 
### Hill Cipher <br />
Encryption - Decryption <br />
Note: we need to add JAR file of JAMA library to use it
### Data Encryption Standard (DES) <br />
Encryption - Decryption - Key Generation  <br />
simple GUI using JavaFX <br />
![Screenshot](DES)
Note: plainttext,ciphertext, and the key should be hexadecimal and 64-bit
### Advanced Encryption Standard (AES)
Encryption - Key Expansion - Decryption(not completed) <br />
simple GUI using JavaFX and we can read from files and write the result on another file <br />
![Screenshot](AES)
Note: plainttext,ciphertext, and the key should be hexadecimal and 128-bit. key expansion is 10 round. In decryption, inverse mix column is the only uncompleted function

## Inspiration
These tutorials and articles helped me:<br />
* https://www.youtube.com/playlist?list=PLd2pEan0ZG_Y1lTa4mXV1y0h-iJjINrqX
* http://page.math.tu-berlin.de/~kant/teaching/hess/krypto-ws2006/des.htm?fbclid=IwAR0qs4kuZ7_x8juLlvnIaG_0iWV2veToDAnFPqsu2NOctDaVzwL_KgM1ZJA
