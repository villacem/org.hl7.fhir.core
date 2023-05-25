package org.hl7.fhir.utilities.jws;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.gen.*;

public class JWSSigner {

  public static void testSignedJWS() {
    try {
      // Generate two signing keys in JWK format

// 2048-bit RSA signing key for RS256 alg
      RSAKey rsaJWK = new RSAKeyGenerator(2048)
        .algorithm(JWSAlgorithm.RS256)
        .keyUse(KeyUse.SIGNATURE)
        .keyID("1")
        .generate();

// EC signing key with P-256 curve for ES256 alg
      ECKey ecJWK = new ECKeyGenerator(Curve.P_256)
        .algorithm(JWSAlgorithm.ES256)
        .keyUse(KeyUse.SIGNATURE)
        .keyID("2")
        .generate();

// The payload to sign
      Payload payload = new Payload("Hello, world!");

// Create the JWS secured object for JSON serialisation
      JWSObjectJSON jwsObjectJSON = new JWSObjectJSON(payload);

// Apply the first signature using the RSA key
      jwsObjectJSON.sign(
        new JWSHeader.Builder((JWSAlgorithm) rsaJWK.getAlgorithm())
          .keyID(rsaJWK.getKeyID())
          .build(),
        new RSASSASigner(rsaJWK)
      );

// Apply the second signature using the EC key
      jwsObjectJSON.sign(
        new JWSHeader.Builder((JWSAlgorithm) ecJWK.getAlgorithm())
          .keyID(ecJWK.getKeyID())
          .build(),
        new ECDSASigner(ecJWK)
      );

// Serialise to JSON
      String json = jwsObjectJSON.serializeGeneral();

// Get the public keys to allow recipients to verify the signatures
      RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();
      ECKey ecPublicJWK = ecJWK.toPublicJWK();

      System.out.println(json);
    }
    catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
