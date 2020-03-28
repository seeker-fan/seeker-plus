package com.wf.seeker.mytest_cases.other.rsapool;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA工具类
 * 
 * @title
 * @description
 * @since JDK1.8
 */
public class RSAUtils {

	/** 默认密钥key大小 **/
	private static final int DEFAULT_KEY_SIZE = 2048;

	private static final String ALGORITHM = "RSA";

	private static final String DEFAULT_PROVIDER = "BC";

	private static final String DEFAULT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

	static {
		// java Security中加入BC安全模式
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	/**
	 * 生成非对称密码对
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 *
	 */
	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
		return generateKeyPair(DEFAULT_KEY_SIZE);
	}

	/**
	 * 生成指定大小的非对称密钥对
	 * 
	 * @param keySize
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM, DEFAULT_PROVIDER);
		keyPairGen.initialize(keySize, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * 根据模和公钥指数获取公钥实例
	 * 
	 * @param modulus 模
	 * @param publicExponent 公钥指数
	 * @return
	 * @throws Exception
	 *
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = KeyFactory.getInstance(ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
	}

	/**
	 * 根据模和私钥指数获取私钥实例
	 * 
	 * @param modulus 模
	 * @param privateExponent 私钥指数
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchProviderException
	 *
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
		KeyFactory keyFac = KeyFactory.getInstance(ALGORITHM, DEFAULT_PROVIDER);
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
	}

	/**
	 * 将Base64私钥字符串转为RSAPrivateKey对象
	 * 
	 * @param privateKeyBase64
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 */
	public static RSAPrivateKey getRSAprivateKey(String privateKeyBase64)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec2 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyBase64));
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, DEFAULT_PROVIDER);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec2);
		return privateKey;
	}

	/**
	 * 将Base64公钥字符串转为RSAPublicKey对象
	 * 
	 * @param publicKeyBase64
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 *
	 */
	public static RSAPublicKey getRSAPublicKey(String publicKeyBase64)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, DEFAULT_PROVIDER);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyBase64));
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		return publicKey;
	}

}
