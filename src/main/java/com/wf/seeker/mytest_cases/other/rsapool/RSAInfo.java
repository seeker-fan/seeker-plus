package com.wf.seeker.mytest_cases.other.rsapool;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

/**
 * 非对称密钥基础信息
 * 
 * @title
 * @description
 * @since JDK1.8
 */
public class RSAInfo {

	private String id; // 业务订单号

	private RSAPrivateKey privatekey;

	private RSAPublicKey publicKey;

	/** 用于设置32位随机数，可用于DES等对称加密 */
	private String randomid;

	public RSAInfo() {
		randomid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public String getRandomid() {
		return randomid;
	}

	public void setRandomid(String randomid) {
		this.randomid = randomid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RSAPrivateKey getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(RSAPrivateKey privatekey) {
		this.privatekey = privatekey;
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public String getPublicKeyPKCS1Hex() throws IOException {
		SubjectPublicKeyInfo spkInfo = SubjectPublicKeyInfo.getInstance(publicKey.getEncoded());
		ASN1Primitive primitive = spkInfo.parsePublicKey();
		byte[] publicKeyPKCS1 = primitive.getEncoded();
		return Hex.encodeHexString(publicKeyPKCS1);

	}

	public String getPublicKeyBase64() {
		return Base64.encodeBase64String(publicKey.getEncoded());
	}

	public String getPrivateKeyBase64() {
		return Base64.encodeBase64String(privatekey.getEncoded());
	}

	public String getPrivateExponent() {
		return privatekey.getPrivateExponent().toString(16);
	}

	public String getModulus() {
		return privatekey.getModulus().toString(16);
	}

	public String getPublicExponent() {
		return publicKey.getPublicExponent().toString(16);
	}

}
