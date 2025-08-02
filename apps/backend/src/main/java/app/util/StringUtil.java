package app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class StringUtil
{
  private StringUtil()
  {
    // なし
  }

  public static String sha256(String str)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(str.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      
      return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }
    catch (NoSuchAlgorithmException e)
    {
      // SHA-256はJVMに標準で含まれるため、通常この例外は発生しない
      throw new IllegalStateException("SHA-256 algorithm not found.", e);
    }
  }
}
