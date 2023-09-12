package io.github.klikdata.dto.bpjs;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesKeySpec {
    private SecretKeySpec key;
    private IvParameterSpec iv;

    public AesKeySpec() {}

    public AesKeySpec(SecretKeySpec key, IvParameterSpec iv) {
        this.key = key;
        this.iv = iv;
    }

    public SecretKeySpec getKey() {
        return key;
    }

    public void setKey(SecretKeySpec key) {
        this.key = key;
    }

    public IvParameterSpec getIv() {
        return iv;
    }

    public void setIv(IvParameterSpec iv) {
        this.iv = iv;
    }
}
