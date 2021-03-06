/*
 * Copyright (c) 2016-2019, FusionAuth, All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package io.fusionauth.jwt.hmac;

import io.fusionauth.jwt.JWTSigningException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.Algorithm;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * This class can sign and verify a JWT that was signed using HMAC.
 *
 * @author Daniel DeGroff
 */
public class HMACSigner implements Signer {

  private final Algorithm algorithm;

  private byte[] secret;

  private HMACSigner(Algorithm algorithm, String secret) {
    Objects.requireNonNull(algorithm);
    Objects.requireNonNull(secret);

    this.algorithm = algorithm;
    this.secret = secret.getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Build a new HMAC signer using a SHA-256 hash.
   *
   * @param secret The secret used to generate the HMAC hash.
   * @return a new HMAC signer.
   */
  public static HMACSigner newSHA256Signer(String secret) {
    return new HMACSigner(Algorithm.HS256, secret);
  }

  /**
   * Build a new HMAC signer using a SHA-384 hash.
   *
   * @param secret The secret used to generate the HMAC hash.
   * @return a new HMAC signer.
   */
  public static HMACSigner newSHA384Signer(String secret) {
    return new HMACSigner(Algorithm.HS384, secret);
  }

  /**
   * Build a new HMAC signer using a SHA-512 hash.
   *
   * @param secret The secret used to generate the HMAC hash.
   * @return a new HMAC signer.
   */
  public static HMACSigner newSHA512Signer(String secret) {
    return new HMACSigner(Algorithm.HS512, secret);
  }

  @Override
  public Algorithm getAlgorithm() {
    return algorithm;
  }

  @Override
  public byte[] sign(String message) {
    Objects.requireNonNull(message);

    try {
      Mac mac = Mac.getInstance(algorithm.getName());
      mac.init(new SecretKeySpec(secret, algorithm.getName()));
      return mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      throw new JWTSigningException("An unexpected exception occurred when attempting to sign the JWT", e);
    }
  }
}
