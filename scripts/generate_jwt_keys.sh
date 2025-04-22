#!/bin/bash

# Generate a 2048-bit RSA private key in PEM format
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048

# Extract the public key in PEM format from the private key
openssl rsa -pubout -in private_key.pem -out public_key.pem

echo "Keys generated:"
echo "Private key: $(realpath private_key.pem)"
echo "Public key:  $(realpath public_key.pem)"
