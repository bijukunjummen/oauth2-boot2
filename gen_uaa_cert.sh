#!/bin/bash
openssl genrsa -out privkey.pem 2048
openssl rsa -pubout -in privkey.pem -out pubkey.pem

SIGNING_KEY=$(cat privkey.pem)
VERIFICATION_KEY=$(cat pubkey.pem)

JWT_SIGNING_KEYS=$(cat <<EOF
jwt:
   token:
      signing-key: |
$(echo "$SIGNING_KEY" | awk '{printf "       %s\n", $0}') 
      verification-key: |
$(echo "$VERIFICATION_KEY" | awk '{printf "       %s\n", $0}')
EOF
)

echo "$JWT_SIGNING_KEYS" > uaa_config.yml

rm privkey.pem
rm pubkey.pem